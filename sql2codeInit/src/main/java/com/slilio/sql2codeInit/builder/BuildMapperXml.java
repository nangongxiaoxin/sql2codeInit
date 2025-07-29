package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.FieldInfo;
import com.slilio.sql2codeInit.bean.TableInfo;
import com.slilio.sql2codeInit.utils.StringUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-19 @Description: @Version: 1.0
 */
public class BuildMapperXml {

  private static final Logger logger = LoggerFactory.getLogger(BuildMapperXml.class);

  private static final String BASE_COLUMN_LIST = "base_column_list";

  private static final String BASE_QUERY_CONDITION = "base_query_condition";

  private static final String QUERY_CONDITION = "query_condition";

  private static final String BASE_QUERY_CONDITION_EXTEND = "base_query_condition_extend";

  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_MAPPERS_XMLS);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    String className = tableInfo.getBeanName() + Constants.SUFFIX_MAPPERS;
    File poFile = new File(folder, className + ".xml");

    OutputStream out = null;
    OutputStreamWriter outw = null;
    BufferedWriter bw = null;

    try {
      out = new FileOutputStream(poFile);
      outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
      bw = new BufferedWriter(outw);

      // 文件内容正文

      // xml头部
      bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      bw.newLine();
      bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
      bw.newLine();
      bw.write("\t\t \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
      bw.newLine();
      bw.write("<mapper namespace=\"" + Constants.PACKAGE_MAPPERS + "." + className + "\">");
      bw.newLine();

      // 1. xml 实体映射
      buildBaseResultMapXml(bw, tableInfo);
      bw.newLine();
      bw.newLine();

      // 2. xml 通用查询列
      buildBaseColumnXml(bw, tableInfo, BASE_COLUMN_LIST);
      bw.newLine();
      bw.newLine();

      // 3. xml 基础查询条件
      buildBaseQueryXml(bw, tableInfo, BASE_QUERY_CONDITION);
      bw.newLine();
      bw.newLine();

      // 4. xml 扩展查询条件
      buildConditionQueryXml(bw, tableInfo, BASE_QUERY_CONDITION_EXTEND);
      bw.newLine();
      bw.newLine();

      // 5. xml 通用查询调价
      buildBaseQueryCondition(
          bw, tableInfo, QUERY_CONDITION, BASE_QUERY_CONDITION, BASE_QUERY_CONDITION_EXTEND);
      bw.newLine();
      bw.newLine();

      // 6. xml 查询列表
      buildQueryList(bw, tableInfo, BASE_COLUMN_LIST, QUERY_CONDITION);
      bw.newLine();
      bw.newLine();

      // 7. xml 查询数量
      buildQueryCount(bw, tableInfo, QUERY_CONDITION);
      bw.newLine();
      bw.newLine();

      // 8. xml 单条插入
      buildOneLineInsert(bw, tableInfo, QUERY_CONDITION);
      bw.newLine();
      bw.newLine();

      // 9. xml 插入或者更新(有匹配的值)
      buildInsertOrUpdate(bw, tableInfo);
      bw.newLine();
      bw.newLine();

      // 10. xml 批量插入
      StringBuffer insertFieldBuffer = new StringBuffer();
      StringBuffer insertPropertyBuffer = new StringBuffer();
      buildBatchInsert(bw, tableInfo, insertFieldBuffer, insertPropertyBuffer);
      bw.newLine();
      bw.newLine();

      // 10. xml 批量插入或更新(有匹配的值)
      buildBatchInsertOrUpdate(bw, tableInfo, insertFieldBuffer, insertPropertyBuffer);
      bw.newLine();
      bw.newLine();

      // 11. xml 根据主键更新
      buildUpdateOfKey(bw, tableInfo);
      bw.newLine();
      bw.newLine();

      // 文件写入结束
      bw.newLine();
      bw.write("</mapper>");
      bw.flush();
    } catch (Exception e) {
      logger.error("创建Mapper Xml失败", e);
    } finally {
      if (bw != null) {
        try {
          bw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (outw != null) {
        try {
          outw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 根据主键更新
   *
   * @param bw
   * @param tableInfo
   */
  private static void buildUpdateOfKey(BufferedWriter bw, TableInfo tableInfo) throws Exception {
    Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
    for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
      List<FieldInfo> keyFieldInfoList = entry.getValue();

      Integer index = 0;
      StringBuilder methodName = new StringBuilder();
      StringBuilder paramNames = new StringBuilder();
      for (FieldInfo fieldInfo : keyFieldInfoList) {
        index++;
        methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
        paramNames.append(fieldInfo.getFieldName() + "=#{" + fieldInfo.getPropertyName() + "}");
        if (index < keyFieldInfoList.size()) {
          methodName.append("And");
          paramNames.append(" and ");
        }
      }
      // 查询
      bw.newLine();
      bw.write("\t<!-- 根据 " + methodName + " 查询 -->");
      bw.newLine();
      bw.write("\t<select id=\"selectBy" + methodName + "\" resultMap=\"base_result_map\">");
      bw.newLine();
      bw.write(
          "\t\tselect <include refid=\""
              + BASE_COLUMN_LIST
              + "\"/> from "
              + tableInfo.getTableName()
              + " where "
              + paramNames);
      bw.newLine();
      bw.write("\t</select>");
      bw.newLine();

      // 更新
      bw.newLine();
      bw.write("\t<!-- 根据 " + methodName + " 更新 -->");
      bw.newLine();
      bw.write(
          "\t<update id=\"updateBy"
              + methodName
              + "\" parameterType=\""
              + Constants.PACKAGE_PO
              + "."
              + tableInfo.getBeanName()
              + "\" >");
      bw.newLine();
      bw.write("\t\tupdate " + tableInfo.getTableName());
      bw.newLine();
      bw.write("\t\t<set>");
      bw.newLine();
      for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
        bw.write("\t\t\t<if test=\"bean." + fieldInfo.getFieldName() + " != null\">");
        bw.newLine();
        bw.write(
            "\t\t\t\t"
                + fieldInfo.getFieldName()
                + " = #{bean."
                + fieldInfo.getPropertyName()
                + "},");
        bw.newLine();
        bw.write("\t\t\t</if>");
        bw.newLine();
      }
      bw.write("\t\t</set>");
      bw.newLine();
      bw.write("\t\twhere " + paramNames);
      bw.newLine();
      bw.write("\t</update>");
      bw.newLine();

      // 删除
      bw.newLine();
      bw.write("\t<!-- 根据 " + methodName + " 删除 -->");
      bw.newLine();
      bw.write("\t<delete id=\"deleteBy" + methodName + "\">");
      bw.newLine();
      bw.write("\t\tdelete from " + tableInfo.getTableName() + " where " + paramNames);
      bw.newLine();
      bw.write("\t</delete>");
      bw.newLine();
    }
  }

  /**
   * 批量插入或更新(有匹配的值)
   *
   * @param bw
   * @param tableInfo
   */
  private static void buildBatchInsertOrUpdate(
      BufferedWriter bw,
      TableInfo tableInfo,
      StringBuffer insertFieldBuffer,
      StringBuffer insertPropertyBuffer)
      throws Exception {
    bw.newLine();
    bw.write("\t<!-- 批量插入或更新 -->");
    bw.newLine();
    bw.write(
        "\t<insert id=\"insertOrUpdateBatch\" parameterType=\""
            + Constants.PACKAGE_PO
            + "."
            + tableInfo.getBeanName()
            + "\">");
    bw.newLine();
    String insertFieldBufferStr =
        insertFieldBuffer.substring(0, insertFieldBuffer.lastIndexOf(","));
    bw.write(
        "\t\tinsert into " + tableInfo.getTableName() + "(" + insertFieldBufferStr + ")values");
    bw.newLine();
    bw.write(
        "\t\t<foreach collection=\"list\" item=\"item\" separator=\",\"  open=\"(\" close=\")\">");
    bw.newLine();
    String insertPropertyBufferStr =
        insertPropertyBuffer.substring(0, insertPropertyBuffer.lastIndexOf(","));
    bw.write("\t\t\t" + insertPropertyBufferStr);
    bw.newLine();
    bw.write("\t\t</foreach>");
    bw.newLine();
    bw.write("\t\ton DUPLICATE KEY UPDATE ");

    StringBuffer insertBatchUpdateBuffer = new StringBuffer();
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      insertBatchUpdateBuffer.append(
          fieldInfo.getFieldName() + " = values(" + fieldInfo.getFieldName() + "),");
    }
    String insertBatchUpdateBufferStr =
        insertBatchUpdateBuffer.substring(0, insertBatchUpdateBuffer.lastIndexOf(","));
    bw.write(insertBatchUpdateBufferStr);
    bw.newLine();

    bw.write("\t</insert>");
  }

  /**
   * 批量插入
   *
   * @param bw
   * @param tableInfo
   */
  private static void buildBatchInsert(
      BufferedWriter bw,
      TableInfo tableInfo,
      StringBuffer insertFieldBuffer,
      StringBuffer insertPropertyBuffer)
      throws Exception {
    bw.newLine();
    bw.write("\t<!-- 批量插入 -->");
    bw.newLine();
    bw.write(
        "\t<insert id=\"insertBatch\" parameterType=\""
            + Constants.PACKAGE_PO
            + "."
            + tableInfo.getBeanName()
            + "\">");
    bw.newLine();

    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      if (fieldInfo.getAutoIncrement()) {
        continue;
      }
      insertFieldBuffer.append(fieldInfo.getFieldName()).append(",");
      insertPropertyBuffer
          .append("#{item.")
          .append(fieldInfo.getPropertyName())
          .append("}")
          .append(",");
    }
    String insertFieldBufferStr =
        insertFieldBuffer.substring(0, insertFieldBuffer.lastIndexOf(","));
    bw.write(
        "\t\tinsert into " + tableInfo.getTableName() + "(" + insertFieldBufferStr + ")values");
    bw.newLine();
    bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
    bw.newLine();
    String insertPropertyBufferStr =
        insertPropertyBuffer.substring(0, insertPropertyBuffer.lastIndexOf(","));
    bw.write("\t\t\t(" + insertPropertyBufferStr + ")");
    bw.newLine();
    bw.write("\t\t</foreach>");
    bw.newLine();
    bw.write("\t</insert>");
  }

  /**
   * 插入或者更新(有匹配的值)
   *
   * @param bw
   * @param tableInfo
   */
  private static void buildInsertOrUpdate(BufferedWriter bw, TableInfo tableInfo) throws Exception {
    bw.newLine();
    bw.write("\t<!-- 插入或者更新(有匹配的值) -->");
    bw.newLine();
    bw.write(
        "\t<insert id=\"insertOrUpdate\" parameterType=\""
            + Constants.PACKAGE_PO
            + "."
            + tableInfo.getBeanName()
            + "\">");
    bw.newLine();

    bw.write("\t\tinsert into " + tableInfo.getTableName());

    bw.newLine();
    bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      bw.newLine();
      bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
      bw.newLine();
      bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
      bw.newLine();
      bw.write("\t\t\t</if>");
    }
    bw.newLine();
    bw.write("\t\t</trim>");

    bw.newLine();
    bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      bw.newLine();
      bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
      bw.newLine();
      bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
      bw.newLine();
      bw.write("\t\t\t</if>");
    }
    bw.newLine();
    bw.write("\t\t</trim>");

    bw.newLine();
    bw.write("\t\ton DUPLICATE KEY UPDATE");
    bw.newLine();

    Map<String, String> keyTempMap = new HashMap<>();
    for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
      List<FieldInfo> fieldInfoList = entry.getValue();
      for (FieldInfo item : fieldInfoList) {
        keyTempMap.put(item.getFieldName(), item.getFieldName());
      }
    }

    bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      if (keyTempMap.get(fieldInfo.getFieldName()) != null) {
        continue;
      }
      bw.newLine();
      bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
      bw.newLine();
      bw.write(
          "\t\t\t\t" + fieldInfo.getFieldName() + " = values(" + fieldInfo.getFieldName() + "),");
      bw.newLine();
      bw.write("\t\t\t</if>");
    }
    bw.newLine();
    bw.write("\t\t</trim>");

    bw.newLine();
    bw.write("\t</insert>");
  }

  /**
   * 单条插入
   *
   * @param bw
   * @param tableInfo
   */
  private static void buildOneLineInsert(
      BufferedWriter bw, TableInfo tableInfo, String query_condition) throws Exception {
    bw.newLine();
    bw.write("\t<!-- 插入（匹配有值的字段） -->");
    bw.newLine();
    bw.write(
        "\t<insert id=\"insert\" parameterType=\""
            + Constants.PACKAGE_PO
            + "."
            + tableInfo.getBeanName()
            + "\">");
    bw.newLine();

    FieldInfo autoIncrementField = null;
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      if (fieldInfo.getAutoIncrement() != null && fieldInfo.getAutoIncrement()) {
        autoIncrementField = fieldInfo;
        break;
      }
    }

    if (autoIncrementField != null) {
      bw.write(
          "\t\t<selectKey keyProperty=\"bean."
              + autoIncrementField.getFieldName()
              + "\" resultType=\""
              + autoIncrementField.getJavaType()
              + "\" order=\"AFTER\">");
      bw.newLine();
      bw.write("\t\t\tselect last_insert_id()");
      bw.newLine();
      bw.write("\t\t</selectKey>");
    }

    bw.newLine();
    bw.write("\t\tinsert into " + tableInfo.getTableName());

    bw.newLine();
    bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      bw.newLine();
      bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
      bw.newLine();
      bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
      bw.newLine();
      bw.write("\t\t\t</if>");
    }
    bw.newLine();
    bw.write("\t\t</trim>");

    bw.newLine();
    bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      bw.newLine();
      bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
      bw.newLine();
      bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
      bw.newLine();
      bw.write("\t\t\t</if>");
    }
    bw.newLine();
    bw.write("\t\t</trim>");

    bw.newLine();
    bw.write("\t</insert>");
  }

  /**
   * 查询数量
   *
   * @param tableInfo
   * @param bw
   * @param query_condition
   * @throws IOException
   */
  private static void buildQueryCount(
      BufferedWriter bw, TableInfo tableInfo, String query_condition) throws IOException {
    bw.newLine();
    bw.write("\t<!-- 查询数量 -->");
    bw.newLine();
    bw.write("\t<select id=\"selectCount\" resultType=\"java.lang.Long\">");
    bw.newLine();
    bw.write(
        "\t\tselect count(1) from "
            + tableInfo.getTableName()
            + " <include refid=\""
            + query_condition
            + "\"/>");
    bw.newLine();
    bw.write("\t</select>");
  }

  /**
   * 查询列表
   *
   * @param tableInfo
   * @param bw
   * @param base_column_list
   * @param query_condition
   * @throws Exception
   */
  private static void buildQueryList(
      BufferedWriter bw, TableInfo tableInfo, String base_column_list, String query_condition)
      throws Exception {
    bw.newLine();
    bw.write("\t<!-- 查询列表 -->");
    bw.newLine();
    bw.write("\t<select id=\"selectList\" resultMap=\"base_result_map\">");
    bw.newLine();
    bw.write(
        "\t\tselect <include refid=\""
            + base_column_list
            + "\"/> from "
            + tableInfo.getTableName()
            + " <include refid=\""
            + query_condition
            + "\"/>");
    bw.newLine();
    bw.write("\t\t<if test=\"query.orderBy!=null\">order by ${query.orderBy}</if>");
    bw.newLine();
    bw.write(
        "\t\t<if test=\"query.simplePage!=null\">limit #{query.simplePage.start},#{query.simplePage.end}</if>");
    bw.newLine();
    bw.write("\t</select>");
  }

  /**
   * 通用查询条件
   *
   * @param bw
   * @param baseQueryConditionExtend
   * @param baseQueryCondition
   * @param tableInfo
   */
  private static void buildBaseQueryCondition(
      BufferedWriter bw,
      TableInfo tableInfo,
      String queryCondition,
      String baseQueryCondition,
      String baseQueryConditionExtend)
      throws Exception {
    bw.write("\t<!-- 扩展查询条件 -->");
    bw.newLine();
    bw.write("\t<sql id=\"" + queryCondition + "\">");
    bw.newLine();
    bw.write("\t\t<where>");
    bw.newLine();
    bw.write("\t\t\t<include refid=\"" + baseQueryCondition + "\"/>");
    bw.newLine();
    bw.write("\t\t\t<include refid=\"" + baseQueryConditionExtend + "\"/>");
    bw.newLine();
    bw.write("\t\t</where>");
    bw.newLine();
    bw.write("\t</sql>");
  }

  /**
   * xml 基础查询条件
   *
   * @param bw
   * @param baseQueryCondition
   * @param tableInfo
   */
  private static void buildBaseQueryXml(
      BufferedWriter bw, TableInfo tableInfo, String baseQueryCondition) throws Exception {
    bw.write("\t<!-- 通用查询列 -->");
    bw.newLine();
    bw.write("\t<sql id=\"" + baseQueryCondition + "\">");
    bw.newLine();

    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      String stringQuery = "";
      if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
        stringQuery = " and query." + fieldInfo.getPropertyName() + "!=''";
      }

      bw.write(
          "\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null" + stringQuery + "\">");
      bw.newLine();
      bw.write("\t\t\tand id = #{query." + fieldInfo.getPropertyName() + "}");
      bw.newLine();
      bw.write("\t\t</if>");
      bw.newLine();
    }

    bw.write("\t</sql>");
  }

  /**
   * xml 扩展查询条件
   *
   * @param bw
   * @param base_query_condition_extend
   * @param tableInfo
   * @throws Exception
   */
  private static void buildConditionQueryXml(
      BufferedWriter bw, TableInfo tableInfo, String base_query_condition_extend) throws Exception {
    bw.write("\t<!-- 扩展查询条件 -->");
    bw.newLine();
    bw.write("\t<sql id=\"" + base_query_condition_extend + "\">");
    bw.newLine();

    for (FieldInfo fieldInfo : tableInfo.getFieldExtendList()) {
      String andWhere = "";
      if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
        andWhere =
            "and "
                + fieldInfo.getFieldName()
                + " like concat ('%', #{query."
                + fieldInfo.getPropertyName()
                + "}, '%')";
      } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())
          || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {

        if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_START)) {
          andWhere =
              "<![CDATA[ and "
                  + fieldInfo.getFieldName()
                  + " >= str_to_date(#{query."
                  + fieldInfo.getPropertyName()
                  + "}, '%Y-%m-%d') ]]>";
        } else if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_END)) {
          andWhere =
              "<![CDATA[ and "
                  + fieldInfo.getFieldName()
                  + " < date_sub(str_to_date(#{query."
                  + fieldInfo.getPropertyName()
                  + "}, '%Y-%m-%d'),interval -1 day) ]]>";
        }
      }

      bw.write(
          "\t\t<if test=\"query."
              + fieldInfo.getPropertyName()
              + " != null and query."
              + fieldInfo.getPropertyName()
              + " !=''\">");
      bw.newLine();
      bw.write("\t\t\t" + andWhere);
      bw.newLine();
      bw.write("\t\t</if>");
      bw.newLine();
    }

    bw.write("\t</sql>");
  }

  /**
   * xml 通用查询列
   *
   * @param bw
   * @param tableInfo
   */
  private static void buildBaseColumnXml(
      BufferedWriter bw, TableInfo tableInfo, String base_column_list) throws Exception {
    bw.write("\t<!-- 通用查询列 -->");
    bw.newLine();
    bw.write("\t<sql id=\"" + base_column_list + "\">");
    bw.newLine();
    StringBuilder columnBuilder = new StringBuilder();
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      columnBuilder.append(fieldInfo.getFieldName()).append(",");
    }
    String columnBuilderStr = columnBuilder.substring(0, columnBuilder.lastIndexOf(","));
    bw.write("\t\t" + columnBuilderStr);
    bw.newLine();
    bw.write("\t</sql>");
  }

  /**
   * xml 实体映射
   *
   * @param bw
   * @param tableInfo
   * @throws Exception
   */
  private static void buildBaseResultMapXml(BufferedWriter bw, TableInfo tableInfo)
      throws Exception {
    bw.write("\t<!-- 实体映射 -->");
    bw.newLine();
    String poClass = Constants.PACKAGE_PO + "." + tableInfo.getBeanName();
    bw.write("\t<resultMap id=\"base_result_map\" type=\"" + poClass + "\">");
    bw.newLine();

    FieldInfo idField = null;
    Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
    for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
      if ("PRIMARY".equals(entry.getKey())) {
        List<FieldInfo> fieldInfoList = entry.getValue();
        if (fieldInfoList.size() == 1) {
          idField = fieldInfoList.get(0);
          break;
        }
      }
    }
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      // 注释
      bw.write("\t\t<!--" + fieldInfo.getComment() + "-->");
      bw.newLine();
      String key = "";
      if (idField != null && fieldInfo.getPropertyName().equals(idField.getPropertyName())) {
        key = "id";
      } else {
        key = "result";
      }
      bw.write(
          "\t\t<"
              + key
              + " column=\""
              + fieldInfo.getFieldName()
              + "\" property=\""
              + fieldInfo.getPropertyName()
              + "\"/>");
      bw.newLine();
    }
    bw.write("\t</resultMap>");
  }
}

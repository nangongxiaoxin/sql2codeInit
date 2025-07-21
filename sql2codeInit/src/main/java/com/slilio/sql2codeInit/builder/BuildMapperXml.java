package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.FieldInfo;
import com.slilio.sql2codeInit.bean.TableInfo;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
      buildBaseColumnXml(bw, BASE_COLUMN_LIST, tableInfo);
      bw.newLine();
      bw.newLine();

      // 3. xml 基础查询条件
      buildBaseQueryXml(bw, BASE_QUERY_CONDITION, tableInfo);
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
   * xml 基础查询条件
   *
   * @param bw
   * @param baseQueryCondition
   * @param tableInfo
   */
  private static void buildBaseQueryXml(
      BufferedWriter bw, String baseQueryCondition, TableInfo tableInfo) throws Exception {
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
   * xml 通用查询列
   *
   * @param bw
   * @param tableInfo
   */
  private static void buildBaseColumnXml(
      BufferedWriter bw, String base_column_list, TableInfo tableInfo) throws Exception {
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
    bw.write("\t<resultMap id=\"BaseResultMap\" type=\"" + poClass + "\">");
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

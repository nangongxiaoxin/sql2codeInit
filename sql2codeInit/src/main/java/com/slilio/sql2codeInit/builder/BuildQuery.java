package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.FieldInfo;
import com.slilio.sql2codeInit.bean.TableInfo;
import com.slilio.sql2codeInit.utils.StringUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-13 @Description: 持久化对象 @Version: 1.0
 */
public class BuildQuery {
  private static final Logger logger = LoggerFactory.getLogger(BuildQuery.class);

  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_QUERY);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    String classname = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;
    File poFile = new File(folder, classname + ".java");

    OutputStream out = null;
    OutputStreamWriter outw = null;
    BufferedWriter bw = null;

    try {
      out = new FileOutputStream(poFile);
      outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
      bw = new BufferedWriter(outw);

      // 文件内容正文

      // package
      bw.write("package " + Constants.PACKAGE_QUERY + ";");
      bw.newLine();
      bw.newLine();

      // 导包
      if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
        bw.write("import java.util.Date;");
        bw.newLine();
      }

      if (tableInfo.getHaveBigDecimal()) {
        bw.write("import java.math.BigDecimal;");
        bw.newLine();
      }

      // 类注释
      bw.newLine();
      BuildComment.createClassComment(bw, tableInfo.getComment() + "查询");
      // 类
      bw.write("public class " + classname + " {");
      bw.newLine();

      List<FieldInfo> extendList = new ArrayList();
      for (FieldInfo field : tableInfo.getFieldList()) {
        // 变量注释
        BuildComment.createFieldComment(bw, field.getComment());

        // 变量（参数）
        bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
        bw.newLine();
        bw.newLine();

        // String类型的参数
        if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, field.getSqlType())) {
          String propertyNameFuzzy = field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY;
          bw.write("\tprivate " + field.getJavaType() + " " + propertyNameFuzzy + ";");
          bw.newLine();
          bw.newLine();

          FieldInfo fuzzyField = new FieldInfo();
          fuzzyField.setJavaType(field.getJavaType());
          fuzzyField.setPropertyName(propertyNameFuzzy);
          extendList.add(fuzzyField);
        }

        // 日期类型
        if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, field.getSqlType())
            || ArrayUtils.contains(Constants.SQL_DATE_TYPES, field.getSqlType())) {
          // 开始
          String propertyNameStart =
              field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START;
          bw.write("\tprivate String " + propertyNameStart + ";");
          bw.newLine();
          bw.newLine();

          FieldInfo timeStartField = new FieldInfo();
          timeStartField.setJavaType("String");
          timeStartField.setPropertyName(propertyNameStart);

          // 结束
          String propertyNameEnd = field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END;
          bw.write("\tprivate String " + propertyNameEnd + ";");
          bw.newLine();
          bw.newLine();

          FieldInfo timeEndField = new FieldInfo();
          timeEndField.setJavaType("String");
          timeEndField.setPropertyName(propertyNameEnd);

          // 上述字段添加到列表
          extendList.add(timeStartField);
          extendList.add(timeEndField);
        }
      }

      // 变量set、get方法
      List<FieldInfo> fieldInfoList = tableInfo.getFieldList();
      fieldInfoList.addAll(extendList);
      for (FieldInfo field : fieldInfoList) {
        String tempField = StringUtils.upperCaseFirstLetter(field.getPropertyName());
        // set
        bw.write(
            "\tpublic void set"
                + tempField
                + " ("
                + field.getJavaType()
                + " "
                + field.getPropertyName()
                + ") {");
        bw.newLine();
        bw.write("\t\tthis." + field.getPropertyName() + " = " + field.getPropertyName() + ";");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();

        // get
        bw.write("\tpublic " + field.getJavaType() + " get" + tempField + " () {");
        bw.newLine();
        bw.write("\t\treturn this." + field.getPropertyName() + ";");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();
      }

      // 文件结束
      bw.newLine();
      bw.write("}");
      bw.flush();
    } catch (Exception e) {
      logger.error("创建PO失败", e);
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
}

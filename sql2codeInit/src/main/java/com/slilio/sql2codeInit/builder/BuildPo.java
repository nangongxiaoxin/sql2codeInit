package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.FieldInfo;
import com.slilio.sql2codeInit.bean.TableInfo;
import com.slilio.sql2codeInit.utils.DateUtils;
import com.slilio.sql2codeInit.utils.StringUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-13 @Description: 持久化对象 @Version: 1.0
 */
public class BuildPo {
  private static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_PO);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    File poFile = new File(folder, tableInfo.getBeanName() + ".java");

    OutputStream out = null;
    OutputStreamWriter outw = null;
    BufferedWriter bw = null;

    try {
      out = new FileOutputStream(poFile);
      outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
      bw = new BufferedWriter(outw);

      // 文件内容正文

      bw.write("package " + Constants.PACKAGE_PO + ";");
      bw.newLine();
      bw.newLine();

      // package
      bw.write("import java.io.Serializable;");
      bw.newLine();

      // 导包
      if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
        bw.write("import java.util.Date;");
        bw.newLine();
        bw.write(Constants.BEAN_DATE_FORMAT_CLASS + ";");
        bw.newLine();
        bw.write(Constants.BEAN_DATE_UNFORMAT_CLASS + ";");
        bw.newLine();
      }
      if (tableInfo.getHaveBigDecimal()) {
        bw.write("import java.math.BigDecimal;");
        bw.newLine();
      }

      // JsonIgnore
      Boolean haveIgnoreBean = false;
      for (FieldInfo field : tableInfo.getFieldList()) {
        if (ArrayUtils.contains(
            Constants.IGNORE_BEAN_TO_JSON_FILED.split(","), field.getPropertyName())) {
          haveIgnoreBean = true;
          break;
        }
      }
      if (haveIgnoreBean) {
        bw.write(Constants.IGNORE_BEAN_TO_JSON_CLASS + ";");
        bw.newLine();
      }

      bw.newLine();

      // 类注释
      BuildComment.createClassComment(bw, tableInfo.getComment());
      // 类
      bw.write("public class " + tableInfo.getBeanName() + " implements Serializable {");
      bw.newLine();

      for (FieldInfo field : tableInfo.getFieldList()) {
        // 变量注释
        BuildComment.createFieldComment(bw, field.getComment());

        // time
        if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, field.getSqlType())) {
          // 序列化
          bw.write(
              "\t"
                  + String.format(
                      Constants.BEAN_DATE_FORMAT_SERIALIZE, DateUtils.YYYY_MM_DD_HH_MM_SS));
          bw.newLine();

          // 反序列化
          bw.write(
              "\t"
                  + String.format(
                      Constants.BEAN_DATE_UNFORMAT_SERIALIZE, DateUtils.YYYY_MM_DD_HH_MM_SS));
          bw.newLine();
        }

        // date
        if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, field.getSqlType())) {
          // 序列化
          bw.write(
              "\t" + String.format(Constants.BEAN_DATE_FORMAT_SERIALIZE, DateUtils.YYYY_MM_DD));
          bw.newLine();

          // 反序列化
          bw.write(
              "\t" + String.format(Constants.BEAN_DATE_UNFORMAT_SERIALIZE, DateUtils.YYYY_MM_DD));
          bw.newLine();
        }

        // JsonIgnore
        if (ArrayUtils.contains(
            Constants.IGNORE_BEAN_TO_JSON_FILED.split(","), field.getPropertyName())) {
          bw.write("\t" + Constants.IGNORE_BEAN_TO_JSON_EXPRESSION);
          bw.newLine();
        }

        // 变量
        bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
        bw.newLine();
        bw.newLine();
      }

      // 变量set、get方法
      for (FieldInfo field : tableInfo.getFieldList()) {
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

      // 重写toString方法
      StringBuffer toStringBf = new StringBuffer();
      Integer index = 0;
      for (FieldInfo field : tableInfo.getFieldList()) {
        index++;
        toStringBf.append(
            field.getComment()
                + ": \" + ( "
                + field.getPropertyName()
                + " == null ? \"空\" : "
                + field.getPropertyName()
                + " )");
        if (index < tableInfo.getFieldList().size()) {
          toStringBf.append(" + ").append(" \", ");
        }
      }
      String toStringBfStr = toStringBf.toString();
      toStringBfStr = " \" " + toStringBfStr;

      bw.write("\t@Override");
      bw.newLine();
      bw.write("\tpublic String toString() {");
      bw.newLine();
      bw.write("\t\treturn " + toStringBfStr + ";");
      bw.newLine();
      bw.write("\t}");

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

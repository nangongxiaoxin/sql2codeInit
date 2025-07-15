package com.slilio.sql2codeInit.bean;

import com.slilio.sql2codeInit.utils.PropertiesUtils;

/**
 * @Author: slilio @CreateTime: 2025-07-12 @Description: @Version: 1.0
 */
public class Constants {
  // 是否忽略表前缀
  public static Boolean IGNORE_TABLE_PREFIX;

  // 参数bean后缀 操作对象生成对应方法的后缀
  public static String SUFFIX_BEAN_PARAM;

  // 路径
  public static String PATH_BASE;

  // 包名
  public static String PACKAGE_BASE;

  public static String PATH_PO;

  public static String PACKAGE_PO;

  // 注释
  public static String AUTHOR_COMMENT;

  // 需要忽略的属性
  public static String IGNORE_BEAN_TO_JSON_FILED;
  public static String IGNORE_BEAN_TO_JSON_EXPRESSION;
  public static String IGNORE_BEAN_TO_JSON_CLASS;

  // 日期序列化
  public static String BEAN_DATE_FORMAT_SERIALIZE;
  public static String BEAN_DATE_FORMAT_CLASS;

  // 日期反序列化
  public static String BEAN_DATE_UNFORMAT_SERIALIZE;
  public static String BEAN_DATE_UNFORMAT_CLASS;

  private static final String PATH_JAVA = "java";
  private static final String PATH_RESOURCE = "resources";

  // 初始化
  static {
    IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));

    SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");

    // package
    PACKAGE_BASE = PropertiesUtils.getString("package.base");
    // PO
    PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");

    // path
    PATH_BASE = PropertiesUtils.getString("path.base") + PATH_JAVA;
    PATH_BASE = PATH_BASE.replace(".", "/");

    // po
    PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");

    // 注释
    AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");

    // 需要忽略的属性
    IGNORE_BEAN_TO_JSON_FILED = PropertiesUtils.getString("ignore.bean.toJson.field");
    IGNORE_BEAN_TO_JSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.toJson.expression");
    IGNORE_BEAN_TO_JSON_CLASS = PropertiesUtils.getString("ignore.bean.toJson.class");

    // 日期序列化
    BEAN_DATE_FORMAT_SERIALIZE = PropertiesUtils.getString("bean.date.format.serialize");
    BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");

    // 日期反序列化
    BEAN_DATE_UNFORMAT_SERIALIZE = PropertiesUtils.getString("bean.date.unformat.serialize");
    BEAN_DATE_UNFORMAT_CLASS = PropertiesUtils.getString("bean.date.unformat.class");
  }

  public static String[] SQL_DATE_TIME_TYPES = new String[] {"datetime", "timestamp"};
  public static String[] SQL_DATE_TYPES = new String[] {"date", "time"};
  public static String[] SQL_DECIMAL_TYPES = new String[] {"decimal", "double", "float"};
  public static String[] SQL_STRING_TYPES =
      new String[] {"char", "varchar", "text", "mediumtext", "longtext"};
  public static String[] SQL_INTEGER_TYPES =
      new String[] {"int", "tinyint", "smallint", "mediumint"};
  public static String[] SQL_LONG_TYPES = new String[] {"bigint"};

  public static void main(String[] args) {
    System.out.println(PATH_PO);
  }
}

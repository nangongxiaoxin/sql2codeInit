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

  private static final String PATH_JAVA = "java";
  private static final String PATH_RESOURCE = "resources";

  // 初始化
  static {
    IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));

    SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");

    // path
    PATH_BASE =
        PropertiesUtils.getString("path.base")
            + PATH_JAVA
            + "/"
            + PropertiesUtils.getString("package.base");
    PATH_BASE = PATH_BASE.replace(".", "/");

    // po
    PATH_PO = PATH_BASE + "/" + PropertiesUtils.getString("package.po").replace(".", "/");

    // package
    PACKAGE_BASE = PropertiesUtils.getString("package.base");
    PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
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

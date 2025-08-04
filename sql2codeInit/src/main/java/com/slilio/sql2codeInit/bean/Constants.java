package com.slilio.sql2codeInit.bean;

import com.slilio.sql2codeInit.utils.PropertiesUtils;

/**
 * @Author: slilio @CreateTime: 2025-07-12 @Description: @Version: 1.0
 */
public class Constants {
  // 是否忽略表前缀
  public static Boolean IGNORE_TABLE_PREFIX;

  // 模糊搜索配置
  public static String SUFFIX_BEAN_QUERY_FUZZY;

  // 日期起始
  public static String SUFFIX_BEAN_QUERY_TIME_START;
  public static String SUFFIX_BEAN_QUERY_TIME_END;

  // 参数bean后缀 操作对象生成对应方法的后缀
  public static String SUFFIX_BEAN_QUERY;

  // mapper文件后缀
  public static String SUFFIX_MAPPERS;

  // 包名
  public static String PACKAGE_BASE;
  public static String PACKAGE_PO;
  public static String PACKAGE_UTILS;
  public static String PACKAGE_ENUMS;
  public static String PACKAGE_QUERY;
  public static String PACKAGE_MAPPERS;
  public static String PACKAGE_VO;
  public static String PACKAGE_EXCEPTION;

  // service
  public static String PACKAGE_SERVICE;
  // service impl
  public static String PACKAGE_SERVICE_IMPL;

  // 路径
  public static String PATH_BASE;
  public static String PATH_PO;
  public static String PATH_UTILS;
  public static String PATH_ENUMS;
  public static String PATH_QUERY;
  public static String PATH_MAPPERS;
  public static String PATH_MAPPERS_XMLS;
  public static String PATH_SERVICE;
  public static String PATH_SERVICE_IMPL;
  public static String PATH_VO;
  public static String PATH_EXCEPTION;

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

    // 参数bean后缀 操作对象生成对应方法的后缀
    SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");

    // Mapper后缀配置
    SUFFIX_MAPPERS = PropertiesUtils.getString("suffix.mappers");

    // package
    PACKAGE_BASE = PropertiesUtils.getString("package.base");

    // path
    PATH_BASE = PropertiesUtils.getString("path.base") + PATH_JAVA;
    PATH_BASE = PATH_BASE.replace(".", "/");

    // po
    PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
    PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");

    // query
    PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getString("package.query");
    PATH_QUERY = PATH_BASE + "/" + PACKAGE_QUERY.replace(".", "/");

    // utils
    PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.utils");
    PATH_UTILS = PATH_BASE + "/" + PACKAGE_UTILS.replace(".", "/");

    // enums
    PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enums");
    PATH_ENUMS = PATH_BASE + "/" + PACKAGE_ENUMS.replace(".", "/");

    // mapper
    PACKAGE_MAPPERS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.mappers");
    PATH_MAPPERS = PATH_BASE + "/" + PACKAGE_MAPPERS.replace(".", "/");

    // service
    PACKAGE_SERVICE = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service");
    PATH_SERVICE = PATH_BASE + "/" + PACKAGE_SERVICE.replace(".", "/");

    // service impl
    PACKAGE_SERVICE_IMPL = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service.impl");
    PATH_SERVICE_IMPL = PATH_BASE + "/" + PACKAGE_SERVICE_IMPL.replace(".", "/");

    // vo
    PACKAGE_VO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.vo");
    PATH_VO = PATH_BASE + "/" + PACKAGE_VO.replace(".", "/");

    // exception
    PACKAGE_EXCEPTION = PACKAGE_BASE + "." + PropertiesUtils.getString("package.exception");
    PATH_EXCEPTION = PATH_BASE + "/" + PACKAGE_EXCEPTION.replace(".", "/");

    // mapper xml
    PATH_MAPPERS_XMLS =
        PropertiesUtils.getString("path.base")
            + PATH_RESOURCE
            + "/"
            + PACKAGE_MAPPERS.replace(".", "/");

    // 注释
    AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");

    // 模糊搜索配置
    SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");

    // 日期起始
    SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtils.getString("suffix.bean.query.time.start");
    SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtils.getString("suffix.bean.query.time.end");

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
    System.out.println(PATH_MAPPERS_XMLS);
  }
}

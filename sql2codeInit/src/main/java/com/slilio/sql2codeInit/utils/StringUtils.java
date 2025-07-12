package com.slilio.sql2codeInit.utils;

/**
 * @Author: slilio @CreateTime: 2025-07-12 @Description: 字符转工具类 @Version: 1.0
 */
public class StringUtils {
  /**
   * 首字母大写
   *
   * @param field
   * @return
   */
  public static String upperCaseFirstLetter(String field) {
    if (org.apache.commons.lang3.StringUtils.isEmpty(field)) {
      return field;
    }

    return field.substring(0, 1).toUpperCase() + field.substring(1);
  }

  /**
   * 首字母小写
   *
   * @param field
   * @return
   */
  public static String lowerCaseFirstLetter(String field) {
    if (org.apache.commons.lang3.StringUtils.isEmpty(field)) {
      return field;
    }

    return field.substring(0, 1).toLowerCase() + field.substring(1);
  }

  // 测试
  public static void main(String[] args) {
    System.out.println("abc->" + upperCaseFirstLetter("abc"));
    System.out.println("Abc->" + lowerCaseFirstLetter("Abc"));
  }
}

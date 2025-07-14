package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.FieldInfo;
import com.slilio.sql2codeInit.bean.TableInfo;
import com.slilio.sql2codeInit.utils.JsonUtils;
import com.slilio.sql2codeInit.utils.PropertiesUtils;
import com.slilio.sql2codeInit.utils.StringUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-11 @Description: 读取数据库表 @Version: 1.0
 */
public class BuildTable {
  private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
  private static Connection connection = null;

  // 获取表和注释
  private static final String SQL_SHOW_TABLE_STATUS = "SHOW TABLE STATUS";
  // 获取表字段
  private static final String SQL_SHOW_TABLE_FIELDS = "SHOW FULL FIELDS FROM %s";
  // 获取表索引
  private static final String SQL_SHOW_TABLE_INDEX = "SHOW INDEX FROM %s";

  // 初始化
  static {
    String driverName = PropertiesUtils.getString("db.driver-class-name");
    String url = PropertiesUtils.getString("db.url");
    String username = PropertiesUtils.getString("db.username");
    String password = PropertiesUtils.getString("db.password");

    try {
      Class.forName(driverName);
      connection = DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      logger.error("数据库连接失败：", e);
    }
  }

  public static List<TableInfo> getTables() {
    PreparedStatement preparedStatement = null;
    ResultSet tableResult = null;
    // 表信息
    List<TableInfo> tableInfoList = new ArrayList<>();

    try {
      preparedStatement = connection.prepareStatement(SQL_SHOW_TABLE_STATUS);
      tableResult = preparedStatement.executeQuery();

      // 遍历
      while (tableResult.next()) {
        // 表名
        String tableName = tableResult.getString("name");
        // 表注释
        String comment = tableResult.getString("comment");

        // 表名处理为对象名 table_product_info -> ProductInfo
        String beanName = tableName;
        if (Constants.IGNORE_TABLE_PREFIX) {
          beanName = tableName.substring(beanName.indexOf("_") + 1);
        }
        //        logger.info("表名转换前：beanName：{}", beanName);

        beanName = processFiled(beanName, true);

        //        logger.info("表名转换后：beanName：{}", beanName);

        // 封装
        TableInfo tableInfo = new TableInfo();

        tableInfo.setTableName(tableName);
        tableInfo.setBeanName(beanName);
        tableInfo.setComment(comment);
        tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_PARAM);

        logger.info(
            "表名：{}，表备注：{}，表Bean：{}，表BeanParam：{}",
            tableInfo.getTableName(),
            tableInfo.getComment(),
            tableInfo.getBeanName(),
            tableInfo.getBeanParamName());

        // 1.调取字段信息并写入
        readFieldInfo(tableInfo);
        // 2.调取索引信息并写入
        getKeyIndexInfo(tableInfo);

        // 写入
        tableInfoList.add(tableInfo);
      }
      logger.info("表：{}", JsonUtils.convertObject2Json(tableInfoList));
    } catch (Exception e) {
      logger.error("读取表失败：", e);
    } finally {
      if (tableResult != null) {
        try {
          tableResult.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    return tableInfoList;
  }

  // 表字段
  private static void readFieldInfo(TableInfo tableInfo) {
    PreparedStatement preparedStatement = null;
    ResultSet fieldResult = null;
    // 表信息
    List<FieldInfo> fieldInfoList = new ArrayList<>();
    try {
      preparedStatement =
          connection.prepareStatement(
              String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
      fieldResult = preparedStatement.executeQuery();

      Boolean haveDateTime = false;
      Boolean haveDate = false;
      Boolean haveBigDecimal = false;

      // 遍历
      while (fieldResult.next()) {
        // 字段
        String field = fieldResult.getString("field");
        // 类型
        String type = fieldResult.getString("type");
        // 自增长
        String extra = fieldResult.getString("extra");
        // 注释
        String comment = fieldResult.getString("comment");

        if (type.indexOf("(") > 0) {
          type = type.substring(0, type.indexOf("("));
        }

        // 数据库字段转java变量
        String propertyName = processFiled(field, false);

        FieldInfo fieldInfo = new FieldInfo();

        fieldInfo.setFieldName(field);
        fieldInfo.setComment(comment);
        fieldInfo.setSqlType(type);
        // 自增判断
        fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));
        fieldInfo.setPropertyName(propertyName);
        // 字段类型转换
        fieldInfo.setJavaType(processJavaType(type));

        // 写入
        fieldInfoList.add(fieldInfo);

        // 表信息补充
        if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
          haveDateTime = true;
        }

        if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
          haveDate = true;
        }

        if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPES, type)) {
          haveBigDecimal = true;
        }
      }
      // 写入
      tableInfo.setHaveDateTime(haveDateTime);
      tableInfo.setHaveDate(haveDate);
      tableInfo.setHaveBigDecimal(haveBigDecimal);
      tableInfo.setFieldList(fieldInfoList);
    } catch (Exception e) {
      logger.error("读取表失败：", e);
    } finally {
      if (fieldResult != null) {
        try {
          fieldResult.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // 表索引
  private static void getKeyIndexInfo(TableInfo tableInfo) {
    PreparedStatement preparedStatement = null;
    ResultSet fieldResult = null;
    // 表信息
    List<FieldInfo> fieldInfoList = new ArrayList<>();
    try {
      // 构建Map字典方便后续查询和操作
      Map<String, FieldInfo> tempMap = new HashMap<>();
      for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
        tempMap.put(fieldInfo.getFieldName(), fieldInfo);
      }

      preparedStatement =
          connection.prepareStatement(
              String.format(SQL_SHOW_TABLE_INDEX, tableInfo.getTableName()));
      fieldResult = preparedStatement.executeQuery();

      // 遍历
      while (fieldResult.next()) {
        String keyName = fieldResult.getString("key_name");
        Integer nonUnique = fieldResult.getInt("non_unique");
        String columnName = fieldResult.getString("column_name");

        if (nonUnique == 1) {
          continue;
        }

        List<FieldInfo> keyFieldList = tableInfo.getKeyIndexMap().get(keyName);
        if (keyFieldList == null) {
          keyFieldList = new ArrayList<>();
          tableInfo.getKeyIndexMap().put(keyName, keyFieldList);
        }

        // 查询临时map字典并写入到index list中
        keyFieldList.add(tempMap.get(columnName));
      }
    } catch (Exception e) {
      logger.error("读取索引失败：", e);
    } finally {
      if (fieldResult != null) {
        try {
          fieldResult.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  //  驼峰转化
  private static String processFiled(String field, Boolean upperCaseFirstLetter) {
    StringBuffer sb = new StringBuffer();
    String[] fields = field.split("_");

    // 首字母
    sb.append(upperCaseFirstLetter ? StringUtils.upperCaseFirstLetter(fields[0]) : fields[0]);
    // 其他词
    for (int i = 1; i < fields.length; i++) {
      sb.append(StringUtils.upperCaseFirstLetter(fields[i]));
    }
    return sb.toString();
  }

  // java类型
  private static String processJavaType(String type) {
    if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPES, type)) {
      return "Integer";
    } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPES, type)) {
      return "Long";
    } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, type)) {
      return "String";
    } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)
        || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
      return "Date";
    } else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPES, type)) {
      return "BigDecimal";
    } else {
      throw new RuntimeException("数据库字段类型转换为JAVA类型时出错：" + type);
    }
  }
}

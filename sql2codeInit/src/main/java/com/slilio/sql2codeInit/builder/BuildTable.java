package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.utils.PropertiesUtils;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-11 @Description: 读取数据库表 @Version: 1.0
 */
public class BuildTable {
  private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
  private static Connection connection = null;

  // 获取表和注释
  private static String SQL_SHOW_TABLE_STATUS = "SHOW TABLE STATUS";

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

  public static void getTables() {
    PreparedStatement preparedStatement = null;
    ResultSet tableResult = null;
    try {
      preparedStatement = connection.prepareStatement(SQL_SHOW_TABLE_STATUS);
      tableResult = preparedStatement.executeQuery();
      // 遍历
      while (tableResult.next()) {
        String tableName = tableResult.getString("name");
        String comment = tableResult.getString("comment");
        logger.info("tableName:{}, comment:{}", tableName, comment);
      }
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
  }
}

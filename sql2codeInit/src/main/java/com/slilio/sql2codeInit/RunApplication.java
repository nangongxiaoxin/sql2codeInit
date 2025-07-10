package com.slilio.sql2codeInit;

import com.slilio.sql2codeInit.builder.BuildTable;

/**
 * @Author: slilio @CreateTime: 2025-07-11 @Description: 启动类 @Version: 1.0
 */
public class RunApplication {
  public static void main(String[] args) {
    BuildTable.getTables();
  }
}

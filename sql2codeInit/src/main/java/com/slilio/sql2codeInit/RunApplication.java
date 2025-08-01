package com.slilio.sql2codeInit;

import com.slilio.sql2codeInit.bean.TableInfo;
import com.slilio.sql2codeInit.builder.*;
import java.util.List;

/**
 * @Author: slilio @CreateTime: 2025-07-11 @Description: 启动类 @Version: 1.0
 */
public class RunApplication {
  public static void main(String[] args) {

    // 1.遍历生成文件
    List<TableInfo> tableInfoList = BuildTable.getTables();
    for (TableInfo tableInfo : tableInfoList) {
      // 实体类
      BuildPo.execute(tableInfo);

      // 查询类
      BuildQuery.execute(tableInfo);

      // Mapper
      BuildMapper.execute(tableInfo);

      // Mapper XML
      BuildMapperXml.execute(tableInfo);

      // Service
      BuildService.execute(tableInfo);
    }

    // 2.生成基础类
    BuildBase.execute();
  }
}

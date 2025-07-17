package com.slilio.sql2codeInit;

import com.slilio.sql2codeInit.bean.TableInfo;
import com.slilio.sql2codeInit.builder.BuildBase;
import com.slilio.sql2codeInit.builder.BuildPo;
import com.slilio.sql2codeInit.builder.BuildTable;
import java.util.List;

/**
 * @Author: slilio @CreateTime: 2025-07-11 @Description: 启动类 @Version: 1.0
 */
public class RunApplication {
  public static void main(String[] args) {

    // 1.遍历生成文件
    List<TableInfo> tableInfoList = BuildTable.getTables();
    for (TableInfo tableInfo : tableInfoList) {
      BuildPo.execute(tableInfo);
    }

    // 2.
    BuildBase.execute();
  }
}

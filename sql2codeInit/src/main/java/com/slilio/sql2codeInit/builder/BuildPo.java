package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.TableInfo;
import java.io.File;
import java.io.IOException;

/**
 * @Author: slilio @CreateTime: 2025-07-13 @Description: 持久化对象 @Version: 1.0
 */
public class BuildPo {
  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_PO);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    File file = new File(folder, tableInfo.getBeanName() + ".java");
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

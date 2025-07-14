package com.slilio.sql2codeInit.builder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: slilio @CreateTime: 2025-07-14 @Description: 生成注解 @Version: 1.0
 */
public class BuildComment {

  public static void createClassComment(BufferedWriter bw, String classComment) throws IOException {
    bw.write("/**");
    bw.newLine();
    bw.write(" * @Description: " + classComment);
    bw.newLine();
    bw.write(" * @CreateTime: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    bw.newLine();
    bw.write(" */");
    bw.newLine();
  }

  public static void createFieldComment() {}

  public static void createMethodComment() {}
}

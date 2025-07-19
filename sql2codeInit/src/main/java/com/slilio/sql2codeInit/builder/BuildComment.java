package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.utils.DateUtils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: slilio @CreateTime: 2025-07-14 @Description: 生成注解 @Version: 1.0
 */
public class BuildComment {

  /**
   * 类注解
   *
   * @param bw
   * @param classComment
   * @throws IOException
   */
  public static void createClassComment(BufferedWriter bw, String classComment) throws Exception {
    bw.write("/**");
    bw.newLine();
    bw.write(" * @Description: " + (classComment == null ? "--" : classComment));
    bw.newLine();
    bw.write(" *");
    bw.newLine();
    bw.write(" * @Author: " + Constants.AUTHOR_COMMENT);
    bw.newLine();
    bw.write(" * @CreateTime: " + DateUtils.format(new Date(), DateUtils.YYYY_MM_DD));
    bw.newLine();
    bw.write(" */");
    bw.newLine();
  }

  /**
   * 参数注释
   *
   * @param bw
   * @param fieldComment
   * @throws Exception
   */
  public static void createFieldComment(BufferedWriter bw, String fieldComment) throws Exception {
    bw.write("\t/**");
    bw.newLine();
    bw.write("\t * " + (fieldComment == null ? "" : fieldComment));
    bw.newLine();
    bw.write("\t */");
    bw.newLine();
  }

  /**
   * mapper接口方法注释
   *
   * @param bw
   * @param fieldComment
   * @throws Exception
   */
  public static void createMapperMethodComment(BufferedWriter bw, String fieldComment)
      throws Exception {
    bw.write("\t/**");
    bw.newLine();
    bw.write("\t * " + (fieldComment == null ? "" : fieldComment));
    bw.newLine();
    bw.write("\t */");
    bw.newLine();
  }

  /** 方法注释 */
  public static void createMethodComment() {}
}

package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.TableInfo;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-08-01 @Description: @Version: 1.0
 */
public class BuildService {
  private static final Logger logger = LoggerFactory.getLogger(BuildService.class);

  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_SERVICE);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    String className = tableInfo.getBeanName() + "Service";
    File poFile = new File(folder, className + ".java");

    OutputStream out = null;
    OutputStreamWriter outw = null;
    BufferedWriter bw = null;

    try {
      out = new FileOutputStream(poFile);
      outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
      bw = new BufferedWriter(outw);

      // 文件内容正文

      // package
      bw.write("package " + Constants.PACKAGE_SERVICE + ";");
      bw.newLine();
      bw.newLine();

      // 导包

      if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
        bw.write(Constants.BEAN_DATE_FORMAT_CLASS + ";");
        bw.newLine();
        bw.write(Constants.BEAN_DATE_UNFORMAT_CLASS + ";");
        bw.newLine();

        bw.write("import " + Constants.PACKAGE_UTILS + ".DateUtils;");
        bw.newLine();
        bw.write("import " + Constants.PACKAGE_ENUMS + ".DateTimePatternEnum;");
        bw.newLine();
      }

      // 正文
      bw.newLine();
      BuildComment.createClassComment(bw, tableInfo.getComment() + "Service");
      bw.write("public interface " + className + " {");
      bw.newLine();
      bw.write("}");

      bw.flush();
    } catch (Exception e) {
      logger.error("创建Service失败", e);
    } finally {
      if (bw != null) {
        try {
          bw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (outw != null) {
        try {
          outw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}

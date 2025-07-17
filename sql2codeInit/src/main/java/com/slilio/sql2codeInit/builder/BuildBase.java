package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-17 @Description: @Version: 1.0
 */
public class BuildBase {
  private static Logger logger = LoggerFactory.getLogger(BuildBase.class);

  public static void execute() {
    List<String> headerInfoList = new ArrayList<>();

    // 生成日期枚举类
    headerInfoList.add("package " + Constants.PACKAGE_ENUMS);
    build(headerInfoList, "DateTimePatternEnum", Constants.PATH_ENUMS);

    //  生成DateUtils类
    headerInfoList.clear();
    headerInfoList.add("package " + Constants.PACKAGE_UTILS);
    build(headerInfoList, "DateUtils", Constants.PATH_UTILS);
  }

  private static void build(List<String> headerInfoList, String fileName, String outPutPath) {
    File folder = new File(outPutPath);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    File javaFile = new File(outPutPath, fileName + ".java");

    OutputStream out = null;
    OutputStreamWriter outw = null;
    BufferedWriter bw = null;

    InputStream in = null;
    InputStreamReader inr = null;
    BufferedReader br = null;

    try {
      out = new FileOutputStream(javaFile);
      outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
      bw = new BufferedWriter(outw);

      String templatePath =
          BuildBase.class.getClassLoader().getResource("template/" + fileName + ".txt").getPath();
      in = new FileInputStream(templatePath);
      inr = new InputStreamReader(in, StandardCharsets.UTF_8);
      br = new BufferedReader(inr);

      // 写入包名
      for (String head : headerInfoList) {
        bw.write(head + ";");
        bw.newLine();
        if (head.contains("package")) {
          bw.newLine();
        }
      }
      bw.newLine();

      String lineInfo = null;
      while ((lineInfo = br.readLine()) != null) {
        bw.write(lineInfo);
        bw.newLine();
      }

      // 写入
      bw.flush();

    } catch (Exception e) {
      logger.error("生成基础类：{}，失败：", fileName, e);
    } finally {

      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (inr != null) {
        try {
          inr.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

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

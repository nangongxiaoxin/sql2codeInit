package com.slilio.sql2codeInit.builder;

import com.slilio.sql2codeInit.bean.Constants;
import com.slilio.sql2codeInit.bean.FieldInfo;
import com.slilio.sql2codeInit.bean.TableInfo;
import com.slilio.sql2codeInit.utils.StringUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-19 @Description: 构建Mapper @Version: 1.0
 */
public class BuildMapper {
  private static final Logger logger = LoggerFactory.getLogger(BuildMapper.class);

  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_MAPPERS);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    String className = tableInfo.getBeanName() + Constants.SUFFIX_MAPPERS;
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
      bw.write("package " + Constants.PACKAGE_MAPPERS + ";");
      bw.newLine();
      bw.newLine();

      // 导包
      bw.write("import org.apache.ibatis.annotations.Param;");
      bw.newLine();
      bw.newLine();

      // 类注释
      BuildComment.createClassComment(bw, tableInfo.getComment() + "Mapper");
      // 类名
      bw.write("public interface " + className + "<T, P>" + " extends BaseMapper {");
      bw.newLine();

      // mapper内方法生成
      Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
      for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
        List<FieldInfo> keyFieldInfoList = entry.getValue();

        Integer index = 0;
        StringBuilder methodName = new StringBuilder();
        StringBuilder methodParams = new StringBuilder();
        for (FieldInfo fieldInfo : keyFieldInfoList) {
          index++;
          methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
          if (index < keyFieldInfoList.size()) {
            methodName.append("And");
          }

          methodParams.append(
              "@Param(\""
                  + fieldInfo.getPropertyName()
                  + "\") "
                  + fieldInfo.getJavaType()
                  + " "
                  + fieldInfo.getPropertyName());
          if (index < keyFieldInfoList.size()) {
            methodParams.append(", ");
          }
        }
        // 查询
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 查询");
        bw.write("\tT selectBy" + methodName + " (" + methodParams + ");");
        bw.newLine();
        bw.newLine();

        // 更新
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 更新");
        bw.write("\tLong updateBy" + methodName + " (@Param(\"bean\") T t, " + methodParams + ");");
        bw.newLine();
        bw.newLine();

        // 删除
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 删除");
        bw.write("\tLong deleteBy" + methodName + " (" + methodParams + ");");
        bw.newLine();
        bw.newLine();
      }

      // 文件结束
      bw.newLine();
      bw.write("}");
      bw.flush();
    } catch (Exception e) {
      logger.error("创建Mapper失败", e);
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

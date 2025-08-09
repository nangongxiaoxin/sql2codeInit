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
 * @Author: slilio @CreateTime: 2025-08-03 @Description: @Version: 1.0
 */
public class BuildController {
  private static final Logger logger = LoggerFactory.getLogger(BuildController.class);

  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_CONTROLLER);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    //    String interfaceName = tableInfo.getBeanName() + "Service";
    String className = tableInfo.getBeanName() + "Controller";
    File poFile = new File(folder, className + ".java");

    String serviceName = tableInfo.getBeanName() + "Service";
    String serviceBeanName = StringUtils.lowerCaseFirstLetter(serviceName);

    OutputStream out = null;
    OutputStreamWriter outw = null;
    BufferedWriter bw = null;

    try {
      out = new FileOutputStream(poFile);
      outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
      bw = new BufferedWriter(outw);

      // 文件内容正文

      // package
      bw.write("package " + Constants.PACKAGE_CONTROLLER + ";");
      bw.newLine();
      bw.newLine();

      bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
      bw.newLine();
      bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
      bw.newLine();
      bw.write("import " + Constants.PACKAGE_VO + ".ResponseVO;");
      bw.newLine();
      bw.write("import " + Constants.PACKAGE_SERVICE + "." + serviceName + ";");
      bw.newLine();
      bw.write("import java.util.List;");
      bw.newLine();
      bw.write("import javax.annotation.Resource;");
      bw.newLine();
      bw.write("import org.springframework.web.bind.annotation.RequestBody;");
      bw.newLine();
      bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
      bw.newLine();
      bw.write("import org.springframework.web.bind.annotation.RestController;");
      bw.newLine();

      // 正文
      bw.newLine();
      BuildComment.createClassComment(bw, tableInfo.getComment() + "Controller");
      bw.write("@RestController");
      bw.newLine();
      bw.write(
          "@RequestMapping(\"/"
              + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName())
              + "\")");
      bw.newLine();
      bw.write("public class " + className + " extends ABaseController {");
      bw.newLine();
      bw.newLine();

      // 注入
      bw.write("\t@Resource private " + serviceName + " " + serviceBeanName + ";");
      bw.newLine();
      bw.newLine();

      bw.write("\t@RequestMapping(\"loadDataList\")");
      bw.newLine();
      bw.write("\tpublic ResponseVO loadDataList(" + tableInfo.getBeanParamName() + " query) {");
      bw.newLine();
      bw.write("\t\treturn getSuccessResponseVO(" + serviceBeanName + ".findListByPage(query));");
      bw.newLine();
      bw.write("\t}");
      bw.newLine();
      bw.newLine();

      BuildComment.createFieldComment(bw, "新增");
      bw.write("\tpublic ResponseVO add(" + tableInfo.getBeanName() + " bean) {");
      bw.newLine();
      bw.write("\t\tthis." + serviceBeanName + ".add(bean);");
      bw.newLine();
      bw.write("\t\treturn getSuccessResponseVO(null);");
      bw.newLine();
      bw.write("\t}");
      bw.newLine();
      bw.newLine();

      BuildComment.createFieldComment(bw, "批量新增");
      bw.write(
          "\tpublic ResponseVO addBatch(@RequestBody List<"
              + tableInfo.getBeanName()
              + "> listBean) {");
      bw.newLine();
      bw.write("\t\tthis." + serviceBeanName + ".addBatch(listBean);");
      bw.newLine();
      bw.write("\t\treturn getSuccessResponseVO(null);");
      bw.newLine();
      bw.write("\t}");
      bw.newLine();
      bw.newLine();

      BuildComment.createFieldComment(bw, "批量新增或修改");
      bw.write(
          "\tpublic ResponseVO addOrUpdateBatch(@RequestBody List<"
              + tableInfo.getBeanName()
              + "> listBean) {");
      bw.newLine();
      bw.write("\t\tthis." + serviceBeanName + ".addOrUpdateBatch(listBean);");
      bw.newLine();
      bw.write("\t\treturn getSuccessResponseVO(null);");
      bw.newLine();
      bw.write("\t}");
      bw.newLine();
      bw.newLine();

      for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
        List<FieldInfo> keyFieldInfoList = entry.getValue();

        Integer index = 0;
        StringBuilder methodName = new StringBuilder();
        StringBuilder methodParams = new StringBuilder();
        StringBuilder paramsBuilder = new StringBuilder();
        for (FieldInfo fieldInfo : keyFieldInfoList) {
          index++;
          methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
          if (index < keyFieldInfoList.size()) {
            methodName.append("And");
          }

          methodParams.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
          paramsBuilder.append(fieldInfo.getPropertyName());
          if (index < keyFieldInfoList.size()) {
            methodParams.append(", ");
            paramsBuilder.append(", ");
          }
        }
        // 查询
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 查询");
        bw.write(
            "\tpublic ResponseVO get"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + " ("
                + methodParams
                + ") {");
        bw.newLine();
        bw.write(
            "\t\treturn getSuccessResponseVO(this."
                + serviceBeanName
                + ".get"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + "("
                + paramsBuilder
                + "));");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();

        // 更新
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 更新");
        bw.write(
            "\tpublic ResponseVO update"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + " ("
                + tableInfo.getBeanName()
                + " bean, "
                + methodParams
                + ") {");
        bw.newLine();
        bw.write(
            "\t\tthis."
                + serviceBeanName
                + ".update"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + "(bean, "
                + paramsBuilder
                + ");");
        bw.newLine();
        bw.write("\t\treturn getSuccessResponseVO(null);");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();

        // 删除
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 删除");
        bw.write(
            "\tpublic ResponseVO delete"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + " ("
                + methodParams
                + ") {");
        bw.newLine();
        bw.write(
            "\t\tthis."
                + serviceBeanName
                + ".delete"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + "("
                + paramsBuilder
                + ");");
        bw.newLine();
        bw.write("\t\treturn getSuccessResponseVO(null);");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();
      }

      // 结束
      bw.newLine();
      bw.write("}");

      bw.flush();
    } catch (Exception e) {
      logger.error("创建ServiceImpl失败", e);
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

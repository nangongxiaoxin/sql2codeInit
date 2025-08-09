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
      bw.write("import " + Constants.PACKAGE_QUERY + ".SimplePage;");
      bw.newLine();
      bw.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");
      bw.newLine();
      bw.write("import " + Constants.PACKAGE_ENUMS + ".PageSize;");
      bw.newLine();
      bw.write("import " + Constants.PACKAGE_SERVICE + "." + serviceName + ";");
      bw.newLine();
      bw.write("import java.util.List;");
      bw.newLine();
      bw.write("import javax.annotation.Resource;");
      bw.newLine();
      bw.write("import org.springframework.web.bind.annotation.RestController;");
      bw.newLine();

      // 正文
      bw.newLine();
      BuildComment.createClassComment(bw, tableInfo.getComment() + "Controller");
      bw.write("@RestController");
      bw.newLine();
      bw.write("public class " + className + " {");
      bw.newLine();
      bw.newLine();

      // 注入
      bw.write("\t@Resource private " + serviceName + " " + serviceBeanName + ";");
      bw.newLine();
      bw.newLine();

      //      BuildComment.createFieldComment(bw, "根据条件查询列表");
      //      bw.write(
      //          "\tpublic List<"
      //              + tableInfo.getBeanName()
      //              + "> findListByParam("
      //              + tableInfo.getBeanParamName()
      //              + " query) {");
      //      bw.newLine();
      //      bw.write("\t\treturn this." + serviceBeanName + ".selectList(query);");
      //      bw.newLine();
      //      bw.write("\t}");
      //      bw.newLine();
      //      bw.newLine();
      //
      //      BuildComment.createFieldComment(bw, "根据条件查询数量");
      //      bw.write("\tpublic Integer findCountByParam(" + tableInfo.getBeanParamName() + "
      // query) {");
      //      bw.newLine();
      //      bw.write("\t\treturn this." + serviceBeanName + ".selectCount(query);");
      //      bw.newLine();
      //      bw.write("\t}");
      //      bw.newLine();
      //      bw.newLine();
      //
      //      BuildComment.createFieldComment(bw, "分页查询");
      //      bw.write(
      //          "\tpublic PaginationResultVO<"
      //              + tableInfo.getBeanName()
      //              + "> findListByPage("
      //              + tableInfo.getBeanParamName()
      //              + " query) {");
      //      bw.newLine();
      //      bw.write("\t\tInteger count = this.findCountByParam(query);");
      //      bw.newLine();
      //      bw.write(
      //          "\t\tInteger pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() :
      // query.getPageSize();");
      //      bw.newLine();
      //      bw.write("\t\tSimplePage page = new SimplePage(query.getPageNo(), count, pageSize);");
      //      bw.newLine();
      //      bw.write("\t\tquery.setSimplePage(page);");
      //      bw.newLine();
      //      bw.write("\t\tList<ProductInfo> list = this.findListByParam(query);");
      //      bw.newLine();
      //      bw.write(
      //          "\t\tPaginationResultVO<ProductInfo> result = new PaginationResultVO(count,
      // page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);");
      //      bw.newLine();
      //      bw.write("\t\treturn result;");
      //      bw.newLine();
      //      bw.write("\t}");
      //      bw.newLine();
      //      bw.newLine();

      BuildComment.createFieldComment(bw, "新增");
      bw.write("\tpublic Integer add(" + tableInfo.getBeanName() + " bean) {");
      bw.newLine();
      bw.write("\t\treturn this." + serviceBeanName + ".insert(bean);");
      bw.newLine();
      bw.write("\t}");
      bw.newLine();
      bw.newLine();

      BuildComment.createFieldComment(bw, "批量新增");
      bw.write("\tpublic Integer addBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
      bw.newLine();
      bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
      bw.newLine();
      bw.write("\t\t\treturn 0;");
      bw.newLine();
      bw.write("\t\t}");
      bw.newLine();
      bw.write("\t\treturn this." + serviceBeanName + ".insertBatch(listBean);");
      bw.newLine();
      bw.write("\t}");
      bw.newLine();
      bw.newLine();

      BuildComment.createFieldComment(bw, "批量新增或修改");
      bw.write(
          "\tpublic Integer addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
      bw.newLine();
      bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
      bw.newLine();
      bw.write("\t\t\treturn 0;");
      bw.newLine();
      bw.write("\t\t}");
      bw.newLine();
      bw.write("\t\treturn this." + serviceBeanName + ".insertOrUpdateBatch(listBean);");
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
            "\tpublic "
                + tableInfo.getBeanName()
                + " get"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + " ("
                + methodParams
                + ") {");
        bw.newLine();
        bw.write(
            "\t\treturn this."
                + serviceBeanName
                + ".selectBy"
                + methodName
                + "("
                + paramsBuilder
                + ");");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();

        // 更新
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 更新");
        bw.write(
            "\tpublic Integer update"
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
            "\t\treturn this."
                + serviceBeanName
                + ".updateBy"
                + methodName
                + "(bean, "
                + paramsBuilder
                + ");");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();

        // 删除
        BuildComment.createMapperMethodComment(bw, "根据 " + methodName + " 删除");
        bw.write(
            "\tpublic Integer delete"
                + tableInfo.getBeanName()
                + "By"
                + methodName
                + " ("
                + methodParams
                + ") {");
        bw.newLine();
        bw.write(
            "\t\treturn this."
                + serviceBeanName
                + ".deleteBy"
                + methodName
                + "("
                + paramsBuilder
                + ");");
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

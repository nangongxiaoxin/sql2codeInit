package com.slilio.sql2codeInit.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: slilio @CreateTime: 2025-07-12 @Description: 表信息 @Version: 1.0
 */
public class TableInfo {
  // todo：可使用lombok的@Data实现get和set(@Getter和@Setter也行)

  // 表名
  private String tableName;
  // bean名
  private String beanName;
  // 参数名
  private String beanParamName;
  // 表注释
  private String comment;
  // 字段信息
  private List<FieldInfo> fieldList;
  // 扩展字段信息
  private List<FieldInfo> fieldExtendList;
  // 唯一索引集合
  private Map<String, List<FieldInfo>> keyIndexMap = new LinkedHashMap<>();
  // 是否有date类型
  private boolean haveDate;
  // 是否有datetime类型
  private boolean haveDateTime;
  // 是否有bigDecimal类型
  private boolean haveBigDecimal;

  public List<FieldInfo> getFieldExtendList() {
    return fieldExtendList;
  }

  public void setFieldExtendList(List<FieldInfo> fieldExtendList) {
    this.fieldExtendList = fieldExtendList;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getBeanName() {
    return beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public String getBeanParamName() {
    return beanParamName;
  }

  public void setBeanParamName(String beanParamName) {
    this.beanParamName = beanParamName;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List<FieldInfo> getFieldList() {
    return fieldList;
  }

  public void setFieldList(List<FieldInfo> fieldList) {
    this.fieldList = fieldList;
  }

  public Map<String, List<FieldInfo>> getKeyIndexMap() {
    return keyIndexMap;
  }

  public void setKeyIndexMap(Map<String, List<FieldInfo>> keyIndexMap) {
    this.keyIndexMap = keyIndexMap;
  }

  public boolean getHaveDate() {
    return haveDate;
  }

  public void setHaveDate(boolean haveDate) {
    this.haveDate = haveDate;
  }

  public boolean getHaveDateTime() {
    return haveDateTime;
  }

  public void setHaveDateTime(boolean haveDateTime) {
    this.haveDateTime = haveDateTime;
  }

  public boolean getHaveBigDecimal() {
    return haveBigDecimal;
  }

  public void setHaveBigDecimal(boolean haveBigDecimal) {
    this.haveBigDecimal = haveBigDecimal;
  }
}

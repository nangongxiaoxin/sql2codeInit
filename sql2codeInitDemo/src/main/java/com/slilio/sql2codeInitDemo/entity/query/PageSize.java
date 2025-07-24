package com.slilio.sql2codeInitDemo.entity.query;

/**
 * @Author: slilio @CreateTime: 2025-07-24 @Description: @Version: 1.0
 */
public enum PageSize {
  SIZE15(15),
  SIZE20(20),
  SIZE30(30),
  SIZE40(40),
  SIZE50(50),
  ;

  int size;

  private PageSize(int size) {
    this.size = size;
  }

  public int getSize() {
    return this.size;
  }
}

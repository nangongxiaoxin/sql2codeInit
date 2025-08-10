package com.slilio.sql2codeInitDemo.controller;

import com.slilio.sql2codeInitDemo.enums.ResponseCodeEnum;

import com.slilio.sql2codeInitDemo.entity.vo.ResponseVO;

public class ABaseController {

  protected static final String STATUS_SUCCESS = "success";
  protected static final String STATUS_ERROR = "error";

  protected <T> ResponseVO getSuccessResponseVO(T t) {
    ResponseVO<T> responseVO = new ResponseVO<>();
    responseVO.setStatus(STATUS_SUCCESS);
    responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
    responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
    responseVO.setData(t);
    return responseVO;
  }
}

package com.slilio.sql2codeInitDemo.controller;

import com.slilio.sql2codeInitDemo.entity.vo.ResponseVO;

import com.slilio.sql2codeInitDemo.enums.ResponseCodeEnum;

import com.slilio.sql2codeInitDemo.exception.BusinessException ;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class AGlobalExceptionHandlerController extends ABaseController {
  private static final Logger logger =
      LoggerFactory.getLogger(AGlobalExceptionHandlerController.class.getName());

  @ExceptionHandler(value = Exception.class)
  Object handleException(Exception e, HttpServletRequest request) {
    logger.error("请求错误，请求地址{}，错误信息：", request.getRequestURI(), e);
    ResponseVO ajaxResponseVO = new ResponseVO();
    // 404
    if (e instanceof NoHandlerFoundException) {
      ajaxResponseVO.setCode(ResponseCodeEnum.CODE_404.getCode());
      ajaxResponseVO.setInfo(ResponseCodeEnum.CODE_404.getMsg());
      ajaxResponseVO.setStatus(STATUS_ERROR);
    } else if (e instanceof BusinessException) {
      // 业务错误
      BusinessException biz = (BusinessException) e;
      ajaxResponseVO.setCode(biz.getCode());
      ajaxResponseVO.setInfo(biz.getMessage());
      ajaxResponseVO.setStatus(STATUS_ERROR);
    } else if (e instanceof BindException) {
      // 参数类型错误
      ajaxResponseVO.setCode(ResponseCodeEnum.CODE_600.getCode());
      ajaxResponseVO.setInfo(ResponseCodeEnum.CODE_600.getMsg());
      ajaxResponseVO.setStatus(STATUS_ERROR);
    } else if (e instanceof DuplicateKeyException) {
      // 主键冲突
      ajaxResponseVO.setCode(ResponseCodeEnum.CODE_601.getCode());
      ajaxResponseVO.setInfo(ResponseCodeEnum.CODE_601.getMsg());
      ajaxResponseVO.setStatus(STATUS_ERROR);
    } else {
      ajaxResponseVO.setCode(ResponseCodeEnum.CODE_500.getCode());
      ajaxResponseVO.setInfo(ResponseCodeEnum.CODE_500.getMsg());
      ajaxResponseVO.setStatus(STATUS_ERROR);
    }
    return ajaxResponseVO;
  }
}

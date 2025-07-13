package com.slilio.sql2codeInit.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Author: slilio @CreateTime: 2025-07-13 @Description: Json转换 @Version: 1.0
 */
public class JsonUtils {

  public static String convertObject2Json(Object object) {
    if (object == null) {
      return null;
    }
    return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
  }
}

package com.slilio.sql2codeInit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: slilio @CreateTime: 2025-07-10 @Description: @Version: 1.0
 */
public class PropertiesUtils {
  private static final Logger log = LoggerFactory.getLogger(PropertiesUtils.class);
  public static Properties props = new Properties();

  public static Map<String, String> PROPER_MAP = new ConcurrentHashMap();

  static {
    InputStream is = null;
    try {
      //            读取
      is = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
      props.load(is);

      Iterator<Object> iterator = props.keySet().iterator();
      while (iterator.hasNext()) {
        String key = (String) iterator.next();
        PROPER_MAP.put(key, props.getProperty(key));
      }
    } catch (Exception e) {
      log.error("配置读取异常： ", e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static String getString(String key) {
    return PROPER_MAP.get(key);
  }

  // 测试
  public static void main(String[] args) {
    System.out.println(getString("db.driver-class-name"));
  }
}

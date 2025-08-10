package com.slilio.sql2codeInitDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: slilio @CreateTime: 2025-08-10 @Description: @Version: 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.slilio.sql2codeInitDemo.mappers")
public class RunDemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(RunDemoApplication.class, args);
  }
}

package com.viki3d.spring.cloud.service.registry.front.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides very simple service.
 *
 * <p><a href="http://localhost:8081/hello">test link</a></p>
 */
@RestController
public class HelloServiceController {

  @Value("${spring.application.name}")
  private String appName;
  
  @GetMapping("/hello")
  public String hello() {

    return "Hello from: " + appName;
  }

}

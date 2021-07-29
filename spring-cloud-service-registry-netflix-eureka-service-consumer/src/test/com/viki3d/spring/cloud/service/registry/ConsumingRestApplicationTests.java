package com.viki3d.spring.cloud.service.registry;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

/**
 * Tests context for consuming rest. 
 */
@SpringBootTest
public class ConsumingRestApplicationTests {

  @Autowired
  private RestTemplate restTemplate;

  @Test
  public void contextLoads() {
    assertNotNull(restTemplate);
  }

}

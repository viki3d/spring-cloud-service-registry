package com.viki3d.spring.cloud.service.registry.front.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Consume (balanced or not) the "hello-microservice" from the Eureka Registry Server.
 *
 * @author Victor Kirov
 */
@RestController
public class HelloServiceConsumerRestController {

  @Autowired
  private DiscoveryClient discoveryClient;

  @Autowired
  private LoadBalancerClient loadBalancer;

  /**
   * Consumes the first possible microservice instance. Do not use load-balancer.
   *
   * <p><a href="http://localhost:8089/consume">test link</a></p>
   */
  @GetMapping("/consume")
  public String consume() {
    String result;
    // From: spring-cloud-service-registry-netflix-eureka-service/'application.properties'
    String microserviceToConsume = "hello-microservice";
    String endpointToConsume = "hello";

    List<ServiceInstance> microserviceInstances = discoveryClient
        .getInstances(microserviceToConsume);
    // Pick up the first available instance of the microservice:
    ServiceInstance microserviceInstance = microserviceInstances.get(0);

    if (microserviceInstances.size() == 0) {
      result = "Microservice [" + microserviceToConsume + "] not found! ";
    } else {
      RestTemplate restTemplate = new RestTemplate();
      String microserviceHost = microserviceInstance.getHost();
      int microservicePort = microserviceInstance.getPort();
      final String url = 
          "http://" + microserviceHost + ":" + microservicePort + "/" + endpointToConsume;
      String serviceResult = restTemplate.getForObject(url, String.class);
      result = "Found " + microserviceInstances.size() + " instances of microservice [" 
          + microserviceToConsume + "]; Call result = " + serviceResult;
    }
    return result;
  }

  /**
   * Consumes load-balanced microservice instance.
   *
   * <p><a href="http://localhost:8089/consumebalanced">test link</a></p>
   */
  @GetMapping("/consumebalanced")
  public String consumeBalanced() {
    String result;
    // From: spring-cloud-service-registry-netflix-eureka-service/'application.properties'
    String microserviceToConsume = "hello-microservice";
    String endpointToConsume = "hello";

    List<ServiceInstance> microserviceInstances = discoveryClient
        .getInstances(microserviceToConsume);
    // Let load-balancer to pick the microservice instance:    
    ServiceInstance microserviceInstance = loadBalancer.choose(microserviceToConsume);

    if (microserviceInstances.size() == 0) {
      result = "Microservice [" + microserviceToConsume + "] not found!";
    } else {
      RestTemplate restTemplate = new RestTemplate();
      String microserviceHost = microserviceInstance.getHost();
      int microservicePort = microserviceInstance.getPort();
      final String url =
          "http://" + microserviceHost + ":" + microservicePort + "/" + endpointToConsume;
      String serviceResult = restTemplate.getForObject(url, String.class);
      result = "Found " + microserviceInstances.size() + " instances of microservice [" 
          + microserviceToConsume + "]; Now consuming = " + url + "; Call result = " 
          + serviceResult;
    }
    return result;
  }

}

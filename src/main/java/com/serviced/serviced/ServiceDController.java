package com.serviced.serviced;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceDController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDController.class);
    @Autowired
    BuildProperties buildProperties;
    @Autowired
    RestTemplate restTemplate;
    @Value("${VERSION:v1}")
    private String version;

    @GetMapping("/ping")
    public String ping() {
//        LOGGER.info("Ping: name={}, version={}", buildProperties.getName(), version);
        String response = restTemplate.getForObject("http://service-c:8080/ping", String.class);
        LOGGER.info("Calling: response={}", response);
        return "I'm service-d " + version + ". Calling... " + response;
    }

    @GetMapping("/ping-with-random-error")
    public ResponseEntity<String> pingWithRandomError() {
        LOGGER.info("Ping with random error: name={}, version={}", buildProperties.getName(), version);
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://service-c:8080/ping-with-random-error", String.class);
        LOGGER.info("Calling: responseCode={}, response={}", responseEntity.getStatusCode(), responseEntity.getBody());
        return new ResponseEntity<>("I'm service-d " + version + ". Calling... " + responseEntity.getBody(), responseEntity.getStatusCode());
    }

    @GetMapping("/ping-with-random-delay")
    public String pingWithRandomDelay() {
        LOGGER.info("Ping with random delay: name={}, version={}", buildProperties.getName(), version);
        String response = restTemplate.getForObject("http://service-c:8080/ping-with-random-delay", String.class);
        LOGGER.info("Calling: response={}", response);
        return "I'm service-d " + version + ". Calling... " + response;
    }

    @GetMapping("/ping-error-v1")
    public String errorforv1() {

        LOGGER.info("Ping with random delay: name={}, version={}", buildProperties.getName(), version);
        String response = restTemplate.getForObject("http://service-c:8080/ping-error-v1", String.class);
        LOGGER.info("Calling: response={}", response);
        return "I'm service-d " + version + ". Calling... " + response;
    }
    @GetMapping("/ping-with-fixed-delay")
    public String pingWithFixedDelay() {
        LOGGER.info("Ping with random delay: name={}, version={}", buildProperties.getName(), version);
        String response = restTemplate.getForObject("http://service-c:8080/ping-with-fixed-delay", String.class);
        LOGGER.info("Calling: response={}", response);
        return "I'm service-d " + version + ". Calling... " + response;
    }
}

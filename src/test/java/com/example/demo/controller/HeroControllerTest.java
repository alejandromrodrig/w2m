package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.demo.entity.Hero;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HeroControllerTest {

  @LocalServerPort
  private int port;

  private String baseUrl = "http://localhost";

  private static RestTemplate restTemplate = null;

  @BeforeAll
  public static void init() {
    restTemplate = new RestTemplate(getClientHttpRequestFactory());
  }

  private static HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
        = new HttpComponentsClientHttpRequestFactory();

    clientHttpRequestFactory.setHttpClient(httpClient());

    return clientHttpRequestFactory;
  }

  private static HttpClient httpClient() {
    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    credentialsProvider.setCredentials(AuthScope.ANY,
        new UsernamePasswordCredentials("admin", "admin"));

    HttpClient client = HttpClientBuilder
        .create()
        .setDefaultCredentialsProvider(credentialsProvider)
        .build();
    return client;
  }

  @BeforeEach
  public void setUp() {
    baseUrl = baseUrl.concat(":").concat(port + "").concat("/hero");
  }

  @Test
  public void returnsAHero() {
    Hero heroResponse = restTemplate.getForObject(baseUrl.concat("/{id}"), Hero.class, 1);
    assertAll(
        () -> assertNotNull(heroResponse),
        () -> assertEquals("Spiderman", heroResponse.getName())
    );
  }

}
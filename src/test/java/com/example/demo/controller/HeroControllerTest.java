package com.example.demo.controller;

import static com.example.demo.entity.Category.C;
import static com.example.demo.entity.Gender.M;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
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
    final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
        = new HttpComponentsClientHttpRequestFactory();

    clientHttpRequestFactory.setHttpClient(httpClient());

    return clientHttpRequestFactory;
  }

  private static HttpClient httpClient() {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    credentialsProvider.setCredentials(AuthScope.ANY,
        new UsernamePasswordCredentials("admin", "admin"));

    final HttpClient client = HttpClientBuilder
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
  public void getsAHeroById() {
    final Hero heroResponse = restTemplate.getForObject(baseUrl.concat("/{id}"), Hero.class, 1);
    assertAll(
        () -> assertNotNull(heroResponse),
        () -> assertEquals("Spiderman", heroResponse.getName())
    );
  }

  @Test
  public void findsAHeroByName() {
    final List<Hero> heroResponse = restTemplate.getForObject(baseUrl.concat("/name/{keywords}"), List.class, "Man");
    assertAll(
        () -> assertNotNull(heroResponse),
        () -> assertEquals(4, heroResponse.size())
    );
  }

  @Test
  public void createsAHero() {
    final Hero hero = new Hero("Capitan Garfio", M, "662500221", "Capitan", C);
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final HttpEntity<Hero> httpEntity = new HttpEntity<>(hero, headers);

    final Hero heroResponse = restTemplate.postForObject(baseUrl, httpEntity, Hero.class);
    assertAll(
        () -> assertNotNull(heroResponse),
        () -> assertNotNull(heroResponse.getId()),
        () -> assertEquals("Capitan Garfio", heroResponse.getName()));
  }

  @Test
  public void updatesAHero() {
    final Hero hero = restTemplate.getForObject(baseUrl.concat("/{id}"), Hero.class, 1);
    hero.setName("Nuevo Heroe");
    final HttpEntity<Hero> entity = new HttpEntity<Hero>(hero);
    final ResponseEntity heroResponse = restTemplate.exchange(baseUrl, HttpMethod.PUT, entity, Hero.class);
    final Hero heroUpdated = (Hero) heroResponse.getBody();
    assertAll(
        () -> assertNotNull(heroUpdated),
        () -> assertNotNull(heroUpdated.getId()),
        () -> assertEquals("Nuevo Heroe", heroUpdated.getName()));
  }

  @Test
  public void deletesAHeroById() {
    final Map<String, String> params = new HashMap<String, String>();
    params.put("id", "1");
    restTemplate.delete(baseUrl.concat("/{id}"), params);
    assertThrows(HttpClientErrorException.class, () -> restTemplate.getForObject(baseUrl.concat("/{id}"), Hero.class, 1));

  }
}
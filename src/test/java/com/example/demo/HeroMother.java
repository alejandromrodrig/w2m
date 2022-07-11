package com.example.demo;

import static java.time.LocalDate.now;

import static com.example.demo.entity.Gender.M;
import static com.example.demo.entity.Range.A;
import static com.example.demo.entity.Range.S;

import java.util.List;

import com.example.demo.entity.Hero;

public class HeroMother {

  public static String UUID = "UUID_RANDOM_1";

  public static Hero oneHero() {
    return new Hero("Superman", M, now(), "661666666", "Superman returns", S);
  }

  public static List<Hero> someHeroes() {
    final Hero superman = new Hero("Superman", M, now(), "661666666", "Superman returns", S);
    final Hero batman = new Hero("Batman", M, now(), "662666666", "Night", A);
    return List.of(superman, batman);
  }
}

package com.example.demo;

import static com.example.demo.entity.Category.A;
import static com.example.demo.entity.Category.S;
import static com.example.demo.entity.Gender.M;

import java.util.List;

import com.example.demo.entity.Hero;

public class HeroMother {

  public static Integer ONE_ID = 1;

  public static Hero oneHero() {
    return new Hero("Superman", M, "661666666", "Superman returns", S);
  }

  public static List<Hero> someHeroes() {
    final Hero superman = new Hero("Superman", M, "661666666", "Superman returns", S);
    final Hero batman = new Hero("Batman", M, "662666666", "Night", A);
    return List.of(superman, batman);
  }
}

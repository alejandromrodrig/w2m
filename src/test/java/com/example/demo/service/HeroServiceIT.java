package com.example.demo.service;

import static com.example.demo.entity.Gender.M;
import static com.example.demo.entity.Range.A;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import javax.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class HeroServiceIT {

  @Autowired
  private HeroService heroService;

  @Autowired
  private HeroRepository heroRepository;

  @Test
  @Transactional
  public void createUserTest() throws ValidationException {
    assertEquals(8, heroRepository.count());
    final Hero newHero = new Hero("Hulk", M, "665111111", "", A);
    final Hero hero = heroService.createHero(newHero);
    final Optional<Hero> heroFromDB = heroRepository.findById(hero.getId());
    assertTrue(heroFromDB.isPresent());
    assertEquals(hero.getName(), heroFromDB.get().getName());
  }

  @Test
  @Transactional
  public void findByIdTest() {
    final int id = 101;
    final Hero hero = heroRepository.findById(id).get();
    assertEquals("Superman", hero.getName());
  }


}

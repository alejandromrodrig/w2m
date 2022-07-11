package com.example.demo.service;

import static com.example.demo.entity.Gender.M;
import static com.example.demo.entity.Range.A;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
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
    Hero newHero = new Hero(1, "Hulk", M, LocalDate.of(1980, 11, 8), "665111111", "", A);
    Hero hero = heroService.createHero(newHero);
    Optional<Hero> heroFromDB = heroRepository.findById(newHero.getId());
    assertTrue(heroFromDB.isPresent());
    assertEquals(hero.getName(), heroFromDB.get().getName());
  }

  @Test
  @Transactional
  public void findByIdTest() {
    final int id = 101;
    Hero hero = heroRepository.findById(id).get();
    assertEquals("Superman", hero.getName());
  }


}

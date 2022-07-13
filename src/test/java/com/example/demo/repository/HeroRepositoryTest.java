package com.example.demo.repository;

import static com.example.demo.entity.Category.A;
import static com.example.demo.entity.Gender.M;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.demo.entity.Hero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class HeroRepositoryTest {

  @Autowired
  private HeroRepository heroRepository;

  @Test
  @Transactional
  public void createsHero() {
    final Hero newHero = new Hero("Hulk", M, "665111111", "", A);
    final Hero hero = heroRepository.save(newHero);
    final Optional<Hero> heroFromDB = heroRepository.findById(hero.getId());
    assertTrue(heroFromDB.isPresent());
    assertEquals(hero.getName(), heroFromDB.get().getName());
  }

  @Test
  @Transactional
  public void findsHeroById() {
    final int id = 1;
    final Hero hero = heroRepository.findById(id).get();
    assertEquals("Spiderman", hero.getName());
  }

  @Test
  @Transactional
  public void updatesHero() {
    final int id = 1;
    final Hero hero = heroRepository.findById(id).get();
    hero.setName("Peter Parker");
    heroRepository.save(hero);
    final Hero updatedHero = heroRepository.findById(id).get();
    assertEquals(hero.getName(), updatedHero.getName());
  }

  @Test
  @Transactional
  public void deletesHeroById() {
    final int id = 1;
    heroRepository.deleteById(id);
    assertThrows(JpaObjectRetrievalFailureException.class, () -> heroRepository.getById(id));
  }

  @Test
  @Transactional
  public void findsAllHeroes() {
    assertEquals(8, heroRepository.findAll().size());
  }
}

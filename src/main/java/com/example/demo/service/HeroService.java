package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import javax.management.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroService {

  @Autowired
  HeroRepository heroRepository;

  public List<Hero> getAllHeroes() {
    return heroRepository.findAll();
  }

  public Hero getHeroById(final Integer id) {
    try {
      return heroRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException("Hero not found"));
    } catch (InstanceNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Hero> findHeroByKeywords(final String keywords) {
    return heroRepository.findByNameContainingIgnoreCase(keywords);
  }

  public List<Hero> searchHeroByKeywords(final String keywords) {
    return heroRepository.searchHeroByKeywords(keywords);
  }

  public Hero createHero(final Hero hero) {
    final List<Hero> heroes = heroRepository.findByNameContainingIgnoreCase(hero.getName());
    if (!heroes.isEmpty()) {
      try {
        throw (new InstanceNotFoundException("Hero exists"));
      } catch (InstanceNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
    return heroRepository.save(hero);
  }

  public Hero updateHero(final Hero hero) {
    getHeroById(hero.getId());
    return heroRepository.save(hero);
  }

  public void deleteHero(final Integer id) {
    getHeroById(id);
    heroRepository.deleteById(id);
  }
}


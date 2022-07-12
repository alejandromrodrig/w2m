package com.example.demo.service.impl;

import java.util.List;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import com.example.demo.service.HeroService;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "hero")
@Service
public class HeroServiceImpl implements HeroService {

  @Autowired
  HeroRepository heroRepository;

  @Cacheable(value = "allheroescache")
  @Override
  public List<Hero> getAllHeroes() {
    return heroRepository.findAll();
  }

  @Cacheable(value = "herocache", key = "#id")
  @Override
  public Hero getHeroById(final Integer id) throws InstanceNotFoundException {
    return heroRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException("Hero not found"));
  }

  @Override
  public List<Hero> findHeroByKeywords(final String keywords) {
    return heroRepository.findByNameContainingIgnoreCase(keywords);
  }

  @Override
  public List<Hero> searchHeroByKeywords(final String keywords) {
    return heroRepository.searchHeroByKeywords(keywords);
  }

  @Caching(evict = {@CacheEvict(value = "allheroescache", allEntries = true)})
  @Override
  public Hero createHero(final Hero hero) throws ValidationException {
    final List<Hero> heroes = heroRepository.findByNameContainingIgnoreCase(hero.getName());
    if (!heroes.isEmpty()) {
      throw (new ValidationException("Hero exists"));
    }
    return heroRepository.save(hero);
  }

  @Caching(evict = {@CacheEvict(value = "allheroescache", allEntries = true)})
  @Override
  public Hero updateHero(final Hero hero) throws InstanceNotFoundException {
    final Hero oldHero = searchHeroByKeywords(hero.getName()).stream().findFirst().get();
    Hero newHero;
    if (hero.getId() != 0 && oldHero.getId().equals(hero.getId())) {
      newHero = getHeroById(oldHero.getId());
    } else {
      newHero = searchHeroByKeywords(hero.getName()).stream().findFirst().get();
    }
    newHero.setTelephone(oldHero.getTelephone());
    newHero.setCategory(oldHero.getCategory());
    newHero.setDescription(oldHero.getDescription());
    newHero.setGender(oldHero.getGender());
    newHero.setId(oldHero.getId());
    newHero.setName(oldHero.getName());
    return heroRepository.save(newHero);
  }

  @Caching(evict = {@CacheEvict(value = "allheroescache", allEntries = true)})
  @Override
  public void deleteHeroById(final Integer id) throws InstanceNotFoundException {
    getHeroById(id);
    heroRepository.deleteById(id);
  }

  @Caching(evict = {@CacheEvict(value = "allheroescache", allEntries = true)})
  @Override
  public void deleteHeroByName(final String keywords) throws InstanceNotFoundException {
    final Hero hero = findHeroByKeywords(keywords).stream()
        .findFirst().orElseThrow(() -> new InstanceNotFoundException("Hero not found"));
    heroRepository.deleteById(hero.getId());
  }
}


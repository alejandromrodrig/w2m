package com.example.demo.service.impl;

import java.util.List;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import com.example.demo.service.HeroService;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class HeroServiceImpl implements HeroService {

  @Autowired
  HeroRepository heroRepository;

  @Override
  public List<Hero> getAllHeroes() {
    return heroRepository.findAll();
  }

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

  @Override
  public Hero createHero(final Hero hero) throws ValidationException {
    final List<Hero> heroes = heroRepository.findByNameContainingIgnoreCase(hero.getName());
    if (!heroes.isEmpty()) {
      throw (new ValidationException("Hero exists"));
    }
    return heroRepository.save(hero);
  }

  @Override
  public Hero updateHero(final Hero hero) throws InstanceNotFoundException {
    getHeroById(hero.getId());
    return heroRepository.save(hero);
  }

  @Override
  public void deleteHero(final Integer id) throws InstanceNotFoundException {
    getHeroById(id);
    heroRepository.deleteById(id);
  }
}


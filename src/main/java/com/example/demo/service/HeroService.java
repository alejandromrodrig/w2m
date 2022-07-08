package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class HeroService {

  @Autowired
  HeroRepository heroRepository;

  public List<Hero> getAllHeroes() {
    return heroRepository.findAll();
  }

  public Hero getHeroById(final Integer id) {
    return heroRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not found"));
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
      throw (new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero exists"));
    }
    return heroRepository.save(hero);
  }

  public Hero updateHero(final Hero hero) throws RestClientException {
    final Optional<Hero> heroFound = heroRepository.findById(hero.getId());
    if (!heroFound.isPresent()) {
      throw (new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero do not exists"));
    }
    return heroRepository.save(hero);
  }

  public void deleteHero(final Integer id) {
    heroRepository.deleteById(id);
  }
}


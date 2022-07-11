package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Hero;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;

public interface HeroService {

  List<Hero> getAllHeroes();

  Hero getHeroById(final Integer id) throws InstanceNotFoundException;

  List<Hero> findHeroByKeywords(final String keywords);

  List<Hero> searchHeroByKeywords(final String keywords);

  Hero createHero(final Hero hero) throws ValidationException;

  Hero updateHero(final Hero hero) throws InstanceNotFoundException;

  void deleteHero(final Integer id) throws InstanceNotFoundException;
}


package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Gender;
import com.example.demo.entity.Hero;
import com.example.demo.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hero")
public class HeroController {

  @Autowired
  HeroService heroService;

  @GetMapping()
  public List<Hero> getHeroes() {
    return heroService.getAllHeroes();
  }

  @GetMapping("/{id}")
  public Hero getHero(@PathVariable Integer id) {
    Hero hero = heroService.getHeroById(id);
    return hero;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Hero createHero(@RequestBody Hero hero) {
    return heroService.createHero(hero);
  }

  @PutMapping()
  public Hero updateHero(@RequestBody Hero hero) {
    return heroService.updateHero(hero);
  }

  @DeleteMapping(value = "/{id}")
  public void deletePost(@PathVariable("id") Integer id) {
    heroService.deleteHero(id);
  }

  @GetMapping(value = "/gender/{gender}")
  public List<Hero> getHeroesByGender(@PathVariable String gender) {
    return heroService.findHeroesByGender(Gender.valueOf(gender));
  }

  @GetMapping(value = "/gender2/{gender}")
  public List<Hero> searchHeroesByGender(@PathVariable String gender) {
    return heroService.searchHeroesByGender(Gender.valueOf(gender));
  }
}
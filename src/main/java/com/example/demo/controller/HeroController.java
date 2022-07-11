package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Hero;
import com.example.demo.service.HeroService;
import com.example.demo.utils.Timer;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  @Timer
  public List<Hero> getHeroes() {
    return heroService.getAllHeroes();
  }

  @GetMapping("/{id}")
  @Timer
  public Hero getHero(@PathVariable Integer id) throws InstanceNotFoundException {
    Hero hero = heroService.getHeroById(id);
    return hero;
  }

  @GetMapping(value = "/name/{keywords}")
  public List<Hero> getHeroesByName(@PathVariable String keywords) {
    return heroService.findHeroByKeywords(keywords);
  }

  @GetMapping(value = "/name2/{keywords}")
  public List<Hero> searchHeroesByName(@PathVariable String keywords) {
    return heroService.searchHeroByKeywords(keywords);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Timer
  public Hero createHero(@RequestBody Hero hero) throws ValidationException {
    return heroService.createHero(hero);
  }

  @PutMapping()
  @Timer
  public Hero updateHero(@RequestBody Hero hero) throws InstanceNotFoundException {
    return heroService.updateHero(hero);
  }

  @DeleteMapping(value = "/{id}")
  @Timer
  public void deletePost(@PathVariable("id") Integer id) throws InstanceNotFoundException {
    heroService.deleteHeroById(id);
  }

  @DeleteMapping(value = "/name/{keywords}")
  @Timer
  public void deletePost(@PathVariable String keywords) throws InstanceNotFoundException {
    heroService.deleteHeroByName(keywords);
  }

  @ExceptionHandler(InstanceNotFoundException.class)
  public ResponseEntity<String> handleNoHeroFoundException(
      InstanceNotFoundException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> handleHeroNoValidException(
      ValidationException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.PRECONDITION_FAILED)
        .body(exception.getMessage());
  }

}
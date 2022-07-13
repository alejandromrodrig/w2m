package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Hero;
import com.example.demo.model.HeroDTO;
import com.example.demo.service.HeroService;
import com.example.demo.util.DTO;
import com.example.demo.util.Timer;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
@SecurityRequirement(name = "admin")
@RequestMapping("/hero")
public class HeroController {

  ModelMapper modelMapper = new ModelMapper();

  @Autowired
  HeroService heroService;

  @GetMapping()
  @Timer
  public List<HeroDTO> getHeroes() {
    return modelMapper.map(heroService.getAllHeroes(), new TypeToken<List<HeroDTO>>() {
    }.getType());
  }

  @GetMapping("/{id}")
  @Timer
  public HeroDTO getHero(@PathVariable Integer id) throws InstanceNotFoundException {
    return modelMapper.map(heroService.getHeroById(id), HeroDTO.class);
  }

  @GetMapping(value = "/name/{keywords}")
  public List<HeroDTO> getHeroesByName(@PathVariable String keywords) {
    return modelMapper.map(heroService.findHeroByKeywords(keywords), new TypeToken<List<HeroDTO>>() {
    }.getType());
  }

  @GetMapping(value = "/name2/{keywords}")
  public List<HeroDTO> searchHeroesByName(@PathVariable String keywords) {
    return modelMapper.map(heroService.searchHeroByKeywords(keywords), new TypeToken<List<HeroDTO>>() {
    }.getType());
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Timer
  public HeroDTO createHero(@RequestBody @DTO(HeroDTO.class) HeroDTO heroDTO) throws ValidationException {
    return modelMapper.map(heroService
        .createHero(modelMapper.map(heroDTO, Hero.class)), HeroDTO.class);
  }

  @PutMapping()
  @Timer
  public HeroDTO updateHero(@RequestBody @DTO(HeroDTO.class) HeroDTO heroDTO) throws InstanceNotFoundException {
    return modelMapper.map(heroService
        .updateHero(modelMapper.map(heroDTO, Hero.class)), HeroDTO.class);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Timer
  public void deletePost(@PathVariable("id") Integer id) throws InstanceNotFoundException {
    heroService.deleteHeroById(id);
  }

  @DeleteMapping(value = "/name/{keywords}")
  @ResponseStatus(HttpStatus.OK)
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
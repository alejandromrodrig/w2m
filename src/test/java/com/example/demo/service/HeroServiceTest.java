package com.example.demo.service;

import static com.example.demo.HeroMother.oneHero;
import static com.example.demo.HeroMother.someHeroes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.h2.value.Value.UUID;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

  @Mock
  HeroRepository heroRepository;

  @InjectMocks
  HeroService heroService;

  @Test
  void getsAllHeroes() {
    givenSomeHeroes();
    final List<Hero> heroes = heroService.getAllHeroes();
    assertThat(heroes.size()).isEqualTo(2);
  }

  @Test
  void getsHeroById() {
    givenOneHero();
    final Hero hero = heroService.getHeroById(UUID);
    assertThat(hero.getName()).isEqualTo("Superman");
  }

  @Test
  void throwsExceptionWhenHeroIsNotFound() {
    givenZeroHero();
    assertThrows(ResponseStatusException.class, () -> heroService.getHeroById(UUID));
  }

  @Test
  void findsHeroByKeywords() {
    givenSomeHeroesFindByName();
    final List<Hero> hero = heroService.findHeroByKeywords("man");
    assertThat(isNotEmpty(hero));
  }

  @Test
  void searchesHeroByKeywords() {
    givenSomeHeroesSearchHeroByKeywords();
    final List<Hero> hero = heroService.searchHeroByKeywords("Man");
    assertThat(isNotEmpty(hero));
  }

  @Test
  void createsHero() throws ValidationException {
    givenZeroHerofindByNameContainingIgnoreCase();
    givenOneHeroSaved(oneHero());
    final Hero hero = heroService.createHero(oneHero());
    assertThat(hero.getName()).isEqualTo(oneHero().getName());
  }

  @Test
  void createsHeroWithRepeatedNameThrowsException() {
    givenSomeHeroesFindByName();
    assertThrows(ValidationException.class, () -> heroService.createHero(oneHero()));
  }

  @Test
  void updatesHero() throws InstanceNotFoundException {
    givenOneHero();
    final Hero heroUpdated = oneHero();
    heroUpdated.setNumber("662000000");
    givenOneHeroSaved(heroUpdated);
    final Hero hero = heroService.updateHero(heroUpdated);
    assertThat(hero.getNumber()).isEqualTo(heroUpdated.getNumber());
  }

  @Test
  void updatesHeroThatDoNotExistsThrowsException() {
    givenZeroHero();
    assertThrows(InstanceNotFoundException.class, () -> heroService.updateHero(oneHero()));
  }

  @Test
  void deletesHero() {
  }

  private void givenOneHero() {
    doReturn(Optional.of(oneHero())).when(heroRepository).findById(any());
  }

  private void givenSomeHeroes() {
    doReturn(someHeroes()).when(heroRepository).findAll();
  }

  private void givenSomeHeroesFindByName() {
    doReturn(someHeroes()).when(heroRepository).findByNameContainingIgnoreCase(any());
  }

  private void givenZeroHerofindByNameContainingIgnoreCase() {
    doReturn(List.of()).when(heroRepository).findByNameContainingIgnoreCase(any());
  }

  private void givenSomeHeroesSearchHeroByKeywords() {
    doReturn(someHeroes()).when(heroRepository).searchHeroByKeywords(any());
  }

  private void givenOneHeroSaved(final Hero hero) {
    doReturn(hero).when(heroRepository).save(any());
  }

  ;

  private void givenZeroHero() {
    doReturn(Optional.empty()).when(heroRepository).findById(any());
  }
}
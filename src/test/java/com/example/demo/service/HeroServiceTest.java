package com.example.demo.service;

import static java.time.LocalDate.now;

import static com.example.demo.entity.Gender.M;
import static com.example.demo.entity.Range.A;
import static com.example.demo.entity.Range.S;
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
  void createsHero() {
  }

  @Test
  void updatesHero() {
  }

  @Test
  void deletesHero() {
  }

  private void givenOneHero() {
    final Hero hero = new Hero(1, "Superman", M, now(), "661666666", "Superman returns", S);
    doReturn(Optional.of(hero)).when(heroRepository).findById(any());
  }

  private void givenSomeHeroes() {
    final Hero superman = new Hero(1, "Superman", M, now(), "661666666", "Superman returns", S);
    final Hero batman = new Hero(2, "Batman", M, now(), "662666666", "Night", A);
    doReturn(List.of(superman, batman)).when(heroRepository).findAll();
  }

  private void givenSomeHeroesFindByName() {
    final Hero superman = new Hero(1, "Superman", M, now(), "661666666", "Superman returns", S);
    final Hero batman = new Hero(2, "Batman", M, now(), "662666666", "Night", A);
    doReturn(List.of(superman, batman)).when(heroRepository).findByNameContainingIgnoreCase(any());
  }

  private void givenSomeHeroesSearchHeroByKeywords() {
    final Hero superman = new Hero(1, "Superman", M, now(), "661666666", "Superman returns", S);
    final Hero batman = new Hero(2, "Batman", M, now(), "662666666", "Night", A);
    doReturn(List.of(superman, batman)).when(heroRepository).searchHeroByKeywords(any());
  }

  private void givenZeroHero() {
    doReturn(Optional.empty()).when(heroRepository).findById(any());
  }
}
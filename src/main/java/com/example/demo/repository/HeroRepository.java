package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Gender;
import com.example.demo.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HeroRepository extends JpaRepository<Hero, Integer> {

  public List<Hero> findByGender(Gender gender);

  @Query("select e from Hero e where e.gender = 'M'")
  public List<Hero> searchByGender(Gender gender);

}
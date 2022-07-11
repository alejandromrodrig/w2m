package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hero implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private LocalDate birth_date;

  private String number;

  private String description;

  @Enumerated(EnumType.STRING)
  private Range range;

  public Hero(final String name, final Gender gender, final LocalDate birth_date, final String number, final String description,
      final Range range) {
    this.name = name;
    this.gender = gender;
    this.birth_date = birth_date;
    this.number = number;
    this.description = description;
    this.range = range;
  }
}
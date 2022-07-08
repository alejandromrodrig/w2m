package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

  public Hero() {

  }

}
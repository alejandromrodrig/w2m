package com.example.demo.model;

import com.example.demo.entity.Category;
import com.example.demo.entity.Gender;
import com.sun.istack.NotNull;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HeroDTO {

  @Id
  @NotNull
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private Gender gender;

  @NotNull
  private String telephone;

  private String description;

  @NotNull
  private Category category;

}

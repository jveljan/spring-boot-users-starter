package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Authority extends BaseEntity {

  @Column
  private String name;

  public static Authority of(String role) {
    Authority authority = new Authority();
    authority.setName(role);
    return authority;
  }
}
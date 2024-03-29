package com.nhnacademy.accountapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
  @Id
  private String id;

  @Column
  private String password;

  @Column
  private String status;

  @Column
  @Enumerated(EnumType.STRING)
  private Role role;

  public static enum Role {
    USER, ADMIN
  }

}

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

/***
 * DB에서 관리되는 User Entity
 */
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
  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @Column
  @Enumerated(EnumType.STRING)
  private UserRole role;

  public static enum UserRole {
    USER, ADMIN
  }

  public static enum UserStatus {
    ACTIVE, DEACTIVATE
  }

}

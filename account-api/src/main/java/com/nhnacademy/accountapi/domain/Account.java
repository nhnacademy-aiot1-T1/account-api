package com.nhnacademy.accountapi.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

/***
 * DB에서 관리되는 User Entity
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DynamicInsert
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String phone;

  @Column
  private String email;

  @Column(name = "auth_type")
  @Enumerated(EnumType.STRING)
  private AuthType authType;

  @Column
  @ColumnDefault("ACTIVE")
  @Enumerated(EnumType.STRING)
  private AccountStatus status;

  @Column
  @ColumnDefault("USER")
  @Enumerated(EnumType.STRING)
  private AccountRole role;

  public static enum AccountRole {
    USER, ADMIN, NONE
  }

  public static enum AccountStatus {
    ACTIVE, DEACTIVATED
  }

  public static enum AuthType {
    DIRECT, PAYCO
  }

}

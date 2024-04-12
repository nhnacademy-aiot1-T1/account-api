package com.nhnacademy.accountapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountAuth {

  @Id
  private Long id;
  @Column(name = "login_id")
  private String loginId;
  @Column
  private String password;

}

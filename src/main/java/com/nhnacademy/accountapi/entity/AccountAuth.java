package com.nhnacademy.accountapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 홈페이지에서 직접가입한 계정의 로그인 정보를 관리하는 Entity입니다
 */
@Entity
@Table(name = "account_auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountAuth {
  @Id
  private Long id;

  @Column(name = "login_id")
  private String loginId;

  @Column
  private String password;

  @Builder
  public AccountAuth(Long id, String loginId, String password) {
    this.id = id;
    this.loginId = loginId;
    this.password = password;
  }

  public void changePassword(String password) {
    this.password = password;
  }

}

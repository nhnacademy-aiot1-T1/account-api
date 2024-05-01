package com.nhnacademy.accountapi.entity;

import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 홈페이지에서 직접가입한 계정의 로그인 정보를 관리하는 Entity입니다
 */
@Entity
@Table(name = "account_auth")
@Getter
@NoArgsConstructor
public class AccountAuth {
  @Id
  private Long id;

  @Column(name = "login_id")
  private String loginId;

  @Column
  private String password;

  @MapsId
  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id")
  private Account account;

  @Builder
  public AccountAuth(Long id, String loginId, String password, Account account) {
    this.id = id;
    this.loginId = loginId;
    this.password = password;
    this.account = account;
  }

  public void changePassword(String password) {
    this.password = password;
  }

  public AccountCredentialsResponse toAccountCredentialsResponse() {
    return AccountCredentialsResponse.builder()
        .id(id)
        .loginId(loginId)
        .password(password)
        .build();
  }

}

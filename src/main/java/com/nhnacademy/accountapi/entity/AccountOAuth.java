package com.nhnacademy.accountapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * OAuth로 가입한 계정의 OAuth에서 제공해주는 id를 관리하는 Entity 입니다
 */
@Entity
@Table(name = "account_oauth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class AccountOAuth {
  @Id
  private Long id;

  @Column(name = "oauth_id")
  private String oauthId;

}

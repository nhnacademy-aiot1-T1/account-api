package com.nhnacademy.accountapi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * OAuth로 가입한 계정의 OAuth에서 제공해주는 id를 관리하는 Entity 입니다
 */
@Entity
@Table(name = "account_oauth")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountOAuth {
  @Id
  private Long id;

  @Column(name = "oauth_id")
  private String oauthId;

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id")
  private Account account;

}

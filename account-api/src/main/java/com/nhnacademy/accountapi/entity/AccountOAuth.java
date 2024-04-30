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

package com.nhnacademy.accountapi.entity;


import com.nhnacademy.accountapi.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

/***
 * DB에서 관리되는 account Entity
 *
 * 직접가입, OAuth가입 계정의 공통 정보를 관리하는 Entity 입니다
 */
@Entity
@Table(name = "account")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString
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
  @ColumnDefault("NONE")
  @Enumerated(EnumType.STRING)
  private AccountRole role;


  public void changeName(String newName) {
    this.name = newName;
  }

  public void changePhone(String newPhone) {
    this.phone = newPhone;
  }

  public void changeEmail(String newEmail) {
    this.email = newEmail;
  }

  public void changeStatus(AccountStatus status) {
    this.status = status;
  }

  public void changeRole(AccountRole role) {
    this.role = role;
  }

  public void updateInfo(AccountModifyRequest request) {
    this.name = request.getName();
    this.phone = request.getPhone();
    this.email = request.getEmail();
    this.status = request.getStatus();
    this.role = request.getRole();
  }

}

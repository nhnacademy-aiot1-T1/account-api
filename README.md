# Account API
Account API를 관리하는 리포지토리입니다. <br/>
계정의 정보를 DB와 연결하여 관리합니다
<br/>
<br/>
## 사용 기술
- 데이터베이스 : MYSQL
- DB 연결 관리 : JPA
- 비밀번호 암호화 : Spring security - PasswordEncoder
<br/>

## 주요 기능
- 계정 생성
  - 직접 회원가입
  - OAuth 계정 등록
    
- 계정 공통 정보 조회
- 계정 정보 수정
  - 이름, 이메일, 권한 수정
  - 비밀번호 수정
  - 활성상태 수정

- 계정 인증 정보 조회
  - 직접 회원가입한 계정의 로그인 정보 (id, password)
  - OAuth계정 로그인 정보 (id)
## 담당자
박미정

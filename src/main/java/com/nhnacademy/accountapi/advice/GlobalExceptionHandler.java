package com.nhnacademy.accountapi.advice;

import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.AccountAuthNotFoundException;
import com.nhnacademy.accountapi.exception.AccountDeactivatedException;
import com.nhnacademy.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Account와 Response관련 에러 Handler 입니다
 */
@RestControllerAdvice
public class GlobalExceptionHandler{

  /**
   * Account를 조회할 때 Account 객체가 null인 경우 발생하는 에러에 대한 처리 메서드 입니다
   * @param e
   * @return error에 대한 내용을 응답으로 보냅니다
   */
  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public <T> CommonResponse<T> handleAccountNotFoundException(
      AccountNotFoundException e) {

    return CommonResponse.fail(e.getMessage());
  }

  /**
   * 인증을 위한 AccountAuth 정보를 조회할 때 Account 의 상태가 비활성화인 경우 발생하는 에러 처리 메서드 입니다
   * @param e -유효하지 않은 id 를 메시지로 보냅니다
   * @return error 에 대한 내용을 응답으로 보냅니다
   */
  @ExceptionHandler(AccountDeactivatedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public <T> CommonResponse<T> handleAccountDeactivatedException(AccountDeactivatedException e) {
    return CommonResponse.fail(e.getMessage());
  }

  /**
   * 인증을 위한 AccountAuth 정보를 조회할 때 id가 AccountAuth에 존재하지 않을 경우 발생하는 에러를 처리하는 메서드 입니다.
   * ex) Account테이블에 존재하지 않는 id, OAuth 계정의 pk id로 조회 시도
   * @param e - 유효하지 않은 로그인 id를 메시지로 보냅니다
   * @return
   * @param <T>
   */
  @ExceptionHandler(AccountAuthNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public <T> CommonResponse<T> handleAccountAuthNotFoundException(AccountAuthNotFoundException e) {
    return CommonResponse.fail(e.getMessage());
  }

  /**
   * 이미 존재하는 id로 Account를 등록하려고 할때 발생하는 에러에 대한 처리 메서드 입니다
   * @param e - 이미 존재하는 id를 메시지로 보냅니다
   * @return  error에 대한 내용을 응답으로 보냅니다
   */
  @ExceptionHandler(AccountAlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public <T> CommonResponse<T> handleAccountAlreadyExistException(
      AccountAlreadyExistException e) {
    return CommonResponse.fail(e.getMessage());
  }

}
package com.nhnacademy.accountapi.advice;

import com.nhnacademy.accountapi.dto.CommonResponse;
import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.AccountDeactivatedException;
import com.nhnacademy.accountapi.exception.AccountNotFoundException;
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

    return CommonResponse.fail(null, e.getMessage());
  }

  /**
   * 인증을 위한 AccountAuth 정보를 조회할 때 Account의 상태가 비활성화인 경우 발생하는 에러 처리 메서드 입니다
   * @param e
   * @return error에 대한 내용을 응답으로 보냅니다
   */
  @ExceptionHandler(AccountDeactivatedException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND) // todo : 상태코드 고민
  public <T> CommonResponse<T> handleAccountDeactivatedException(AccountDeactivatedException e) {
    return CommonResponse.fail(null, e.getMessage());
  }

  /**
   * 이미 존재하는 id로 Account를 등록하려고 할때 발생하는 에러에 대한 처리 메서드 입니다
   * @param e AccountAlreadyExistException 타입 에러를 다룹니다
   * @return  error에 대한 내용을 응답으로 보냅니다
   */
  @ExceptionHandler(AccountAlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public <T> CommonResponse<T> handleAccountAlreadyExistException(
      AccountAlreadyExistException e) {
    return CommonResponse.fail(null, e.getMessage());
  }

}
package com.nhnacademy.accountapi.dto;

import java.time.LocalDateTime;
import lombok.Getter;

/***
 * 기본 응답 포맷
 * @param <T> - 전달될 응답 DTO 타입
 */
@Getter
public class Response<T> {
  private final String status;
  private final T data;
  private final String message;
  private final LocalDateTime timestamp;

  private Response(String status, T data, String message) {
    this.status = status;
    this.data = data;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }

  public static <T> Response<T> success(T data, String message) {
    return new Response<>("success", data, message);
  }

  public static <T> Response<T> fail(T data, String message) {
    return new Response<>("fail", data, message);
  }

}

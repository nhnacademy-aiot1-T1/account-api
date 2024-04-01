package com.nhnacademy.accountapi.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Response<T> {
  private String status;
  private T data;
  private String message;
  private LocalDateTime timestamp;

  private Response(String status, T data, String message) {
    this.status = status;
    this.data = data;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }

  public static <T> Response<T> success(T data, String message) {
    return new Response<>("success", data, message);
  }

}

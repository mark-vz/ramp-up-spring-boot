package com.example.myusermgmt.common.exception;

public class UserNotFoundForEmailAddressException extends IllegalArgumentException {

  public UserNotFoundForEmailAddressException(String s) {
    super(s);
  }

  public UserNotFoundForEmailAddressException() {
    this("user not found for given email address");
  }
}

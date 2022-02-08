package com.example.myusermgmt.user.domain;

import java.util.UUID;

public class User {
  private final UUID id;
  private final String firstName;
  private final String lastName;
  private final String emailAddress;

  public User(UUID id, String firstName, String lastName, String emailAddress) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }
}

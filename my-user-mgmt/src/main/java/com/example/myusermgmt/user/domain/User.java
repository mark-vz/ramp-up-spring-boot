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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (!id.equals(user.id)) {
      return false;
    }
    if (!firstName.equals(user.firstName)) {
      return false;
    }
    if (!lastName.equals(user.lastName)) {
      return false;
    }
    return emailAddress.equals(user.emailAddress);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + emailAddress.hashCode();
    return result;
  }
}

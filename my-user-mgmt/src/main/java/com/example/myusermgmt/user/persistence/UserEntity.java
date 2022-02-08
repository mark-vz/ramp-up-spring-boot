package com.example.myusermgmt.user.persistence;

import com.example.myusermgmt.user.domain.User;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  private UUID id;
  private String firstName;
  private String lastName;
  private String emailAddress;

  protected UserEntity() {
  }

  private UserEntity(UUID id, String firstName, String lastName, String emailAddress) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
  }

  static UserEntity fromDomain(final User user) {
    return new UserEntity(user.getId(), user.getFirstName(), user.getLastName(), user.getEmailAddress());
  }

  User toDomain() {
    return new User(id, firstName, lastName, emailAddress);
  }
}

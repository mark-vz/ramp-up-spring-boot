package com.example.myusermgmt.user.persistence;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.persistence.AddressEntity;
import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.readModel.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  private UUID id;
  private String firstName;
  private String lastName;
  private String emailAddress;

  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<AddressEntity> addresses;

  protected UserEntity() {
  }

  private UserEntity(UUID id, String firstName, String lastName, String emailAddress) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
  }

  public static UserEntity fromDomain(final User user) {
    return new UserEntity(user.id(), user.firstName(), user.lastName(), user.emailAddress());
  }

  public User toDomain() {
    return new User(id, firstName, lastName, emailAddress);
  }

  public Contact toContactDomain() {
    return new Contact(
            this.firstName,
            this.lastName,
            this.emailAddress,
            addresses.stream().map(AddressEntity::toReadModel).toList()
    );
  }

  public UUID getId() {
    return id;
  }
}

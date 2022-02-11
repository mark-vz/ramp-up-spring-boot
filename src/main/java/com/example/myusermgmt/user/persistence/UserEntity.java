package com.example.myusermgmt.user.persistence;

import com.example.myusermgmt.address.persistence.AddressEntity;
import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.readmodel.ContactView;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  private UUID id;
  private String firstName;
  private String lastName;
  private String emailAddress;
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
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
    return new UserEntity(
        user.id(),
        user.firstName(),
        user.lastName(),
        user.emailAddress()
    );
  }

  public User toDomain() {
    return new User(
        id,
        firstName,
        lastName,
        emailAddress
    );
  }

  ContactView toContactView() {
    return new ContactView(
        id,
        firstName,
        lastName,
        emailAddress,
        addresses.stream().map(AddressEntity::toAddressView).toList()
    );
  }
}

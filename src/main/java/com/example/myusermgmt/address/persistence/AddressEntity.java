package com.example.myusermgmt.address.persistence;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.readModel.AddressReadModel;
import com.example.myusermgmt.user.domain.User;
import com.example.myusermgmt.user.persistence.UserEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "addresses")
public class AddressEntity {
  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
  private String street;
  private String zipcode;
  private String city;

  public AddressEntity() {
  }

  public AddressEntity(UUID id, UserEntity user, String street, String zipcode, String city) {
    this.id = id;
    this.user = user;
    this.street = street;
    this.zipcode = zipcode;
    this.city = city;
  }

  static AddressEntity fromDomain(final Address address) {
    return new AddressEntity(
            address.id(),
            // address.user(),
            UserEntity.fromDomain(new User(UUID.randomUUID(), "", "", "")),
            address.street(),
            address.zipcode(),
            address.city()
    );
  }

  public Address toDomain() {
    return new Address(id, street, zipcode, city, user.getId().toString());
  }

  public AddressReadModel toReadModel() {
    return new AddressReadModel(street, zipcode, city);
  }
}

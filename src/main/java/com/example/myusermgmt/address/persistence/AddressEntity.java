package com.example.myusermgmt.address.persistence;

import com.example.myusermgmt.address.domain.Address;
import com.example.myusermgmt.address.readmodel.AddressView;
import com.example.myusermgmt.user.persistence.UserEntity;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressEntity {
  @Id
  private UUID id;
  private String street;
  private String zipcode;
  private String city;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  protected AddressEntity() {
  }

  public AddressEntity(UUID id, String street, String zipcode, String city, UserEntity userEntity) {
    this.id = id;
    this.street = street;
    this.zipcode = zipcode;
    this.city = city;
    this.user = userEntity;
  }

  static AddressEntity fromDomain(final Address address) {
    return new AddressEntity(address.id(), address.street(), address.zipcode(), address.city(), UserEntity.fromDomain(address.user()));
  }

  Address toDomain() {
    return new Address(id, street, zipcode, city, user.toDomain());
  }

  public AddressView toView() {
    return new AddressView(street, zipcode, city);
  }
}

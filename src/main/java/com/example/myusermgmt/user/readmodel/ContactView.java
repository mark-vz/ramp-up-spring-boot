package com.example.myusermgmt.user.readmodel;

import com.example.myusermgmt.address.persistence.AddressEntity;
import com.example.myusermgmt.address.readmodel.AddressView;
import com.example.myusermgmt.user.persistence.UserEntity;
import java.util.List;
import java.util.UUID;

public record ContactView(
    UUID id,
    String firstName,
    String lastName,
    String emailAddress,
    List<AddressView> addresses
) {

  public static ContactView toView(final UserEntity userEntity) {
    return new ContactView(
        userEntity.toDomain().id(),
        userEntity.toDomain().firstName(),
        userEntity.toDomain().lastName(),
        userEntity.toDomain().emailAddress(),
        userEntity.getAddresses().stream().map(AddressEntity::toView).toList()
    );
  }
}

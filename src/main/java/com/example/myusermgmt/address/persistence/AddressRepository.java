package com.example.myusermgmt.address.persistence;

import com.example.myusermgmt.address.domain.Address;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

  default List<Address> getAllAddresses() {
    return findAll().stream().map(AddressEntity::toDomain).toList();
  }

  default Address createAddress(Address address) {
    return save(AddressEntity.fromDomain(address)).toDomain();
  }
}

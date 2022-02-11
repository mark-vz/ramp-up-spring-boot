package com.example.myusermgmt.address.readmodel;

import com.example.myusermgmt.address.domain.Address;

public record AddressView(
    String street,
    String zipcode,
    String city
) {
  public static AddressView fromDomain(final Address address) {
    return new AddressView(address.street(), address.zipcode(), address.city());
  }
}

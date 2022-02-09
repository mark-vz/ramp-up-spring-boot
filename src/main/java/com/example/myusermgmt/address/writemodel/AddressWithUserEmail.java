package com.example.myusermgmt.address.writemodel;

public record AddressWithUserEmail(
    String userEmailAddress,
    String street,
    String zipcode,
    String city
) {
}

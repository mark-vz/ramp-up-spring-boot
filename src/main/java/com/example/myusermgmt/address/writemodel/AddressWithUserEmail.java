package com.example.myusermgmt.address.writemodel;

import java.util.UUID;

public record AddressWithUserEmail(
    UUID id,
    String userEmailAddress,
    String street,
    String zipcode,
    String city
) {
}

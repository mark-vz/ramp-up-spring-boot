package com.example.myusermgmt.address.domain;

import com.example.myusermgmt.user.domain.User;
import java.util.UUID;

public record Address(
    UUID id,
    String street,
    String zipcode,
    String city,
    User user
) {
}

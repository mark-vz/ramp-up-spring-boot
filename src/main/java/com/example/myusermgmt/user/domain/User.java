package com.example.myusermgmt.user.domain;

import java.util.UUID;

public record User(
    UUID id,
    String firstName,
    String lastName,
    String emailAddress
) {
}

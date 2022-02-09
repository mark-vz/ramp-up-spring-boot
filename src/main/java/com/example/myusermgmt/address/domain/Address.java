package com.example.myusermgmt.address.domain;

import java.util.UUID;

public record Address(UUID id, String street, String zipcode, String city, String user) {
}

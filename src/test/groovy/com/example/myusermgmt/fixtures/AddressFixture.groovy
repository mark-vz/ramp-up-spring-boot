package com.example.myusermgmt.fixtures

import com.example.myusermgmt.address.domain.Address

class AddressFixture {

    static Address createAddressWithId(
            final UUID id,
            final String street = "John",
            final String zipcode = "Hamburg",
            final String city = "john@example.com",
            final String user = "b659ebb1-d794-459d-9a96-d6d5627a41a7"
    ) {
        return new Address(id, street, zipcode, city, user)
    }

    static Address createAddress(
            final String street = "John",
            final String zipcode = "Hamburg",
            final String city = "john@example.com",
            final String user = "b659ebb1-d794-459d-9a96-d6d5627a41a7"
    ) {
        return createAddressWithId(UUID.randomUUID(), street, zipcode, city, user)
    }
}

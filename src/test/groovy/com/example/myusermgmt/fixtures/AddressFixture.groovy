package com.example.myusermgmt.fixtures

import com.example.myusermgmt.address.domain.Address
import com.example.myusermgmt.user.domain.User

class AddressFixture {

    static Address createAddressWithId(
            final UUID id,
            final String street = "street 1",
            final String zipcode = "50354",
            final String city = "Hürth",
            final User user = UserFixture.createUser()
    ) {
        return new Address(id, street, zipcode, city, user)
    }

    static Address createAddress(
            final String street = "street 1",
            final String zipcode = "50354",
            final String city = "Hürth",
            final User user = UserFixture.createUser()
    ) {
        return createAddressWithId(UUID.randomUUID(), street, zipcode, city, user)
    }
}

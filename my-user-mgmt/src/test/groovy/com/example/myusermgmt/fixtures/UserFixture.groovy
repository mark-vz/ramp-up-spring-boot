package com.example.myusermgmt.fixtures

import com.example.myusermgmt.user.domain.User

class UserFixture {

    static User createUserWithId(
            final UUID id,
            final String firstname = "John",
            final String lastname = "Doe",
            final String emailAddress = "john@example.com"
    ) {
        return new User(id, firstname, lastname, emailAddress)
    }

    static User createUser(
            final String firstname = "John",
            final String lastname = "Doe",
            final String emailAddress = "john@example.com"
    ) {
        return createUserWithId(UUID.randomUUID(), firstname, lastname, emailAddress)
    }
}

package com.example.myusermgmt.fixtures

import com.example.myusermgmt.address.readmodel.AddressView
import com.example.myusermgmt.user.readmodel.ContactView

class ContactViewFixture {

    private static final AddressView addressView1 = new AddressView("Street 1", "12345", "Foo City")
    private static final AddressView addressView2 = new AddressView("Street 2", "54321", "Bar City")

    static ContactView createContactView(
            final String firstname = "John",
            final String lastname = "Doe",
            final String emailAddress = "john@example.com",
            final List<AddressView> addresses = [addressView1, addressView2]
    ) {
        return new ContactView(UUID.randomUUID(), firstname, lastname, emailAddress, addresses)
    }
}

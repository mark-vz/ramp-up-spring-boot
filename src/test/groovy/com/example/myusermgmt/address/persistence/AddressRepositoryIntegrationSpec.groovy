package com.example.myusermgmt.address.persistence

import com.example.myusermgmt.IntegrationSpecification
import com.example.myusermgmt.address.domain.Address
import com.example.myusermgmt.fixtures.AddressFixture
import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import com.example.myusermgmt.user.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired

class AddressRepositoryIntegrationSpec extends IntegrationSpecification {

    final User user1 = UserFixture.createUser("Bob", "Andrews", "bob.andrews@example.com")
    final Address address1 = AddressFixture.createAddressWithUser(user1)

    @Autowired
    AddressRepository addressRepository

    @Autowired
    UserRepository userRepository

    def "should correctly persist and retrieve address with user"() {
        when:
        User createdUser = userRepository.createUser(user1)
        Address createdAddress = addressRepository.createAddress(address1)

        then:
        List<Address> addresses = addressRepository.getAllAddresses()
        addresses.size() == 1
        addresses == [address1]
        addresses == [createdAddress]
        and:
        addresses.first().user() == createdUser
        createdUser.firstName() == "Bob"
        createdUser.lastName() == "Andrews"
    }
}

package com.example.myusermgmt.address

import com.example.myusermgmt.address.domain.Address
import com.example.myusermgmt.address.persistence.AddressRepository
import com.example.myusermgmt.address.writemodel.AddressWithUserEmail
import com.example.myusermgmt.fixtures.AddressFixture
import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.UserService
import com.example.myusermgmt.user.domain.User
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class AddressServiceSpec extends Specification {

    final Address testAddress1 = AddressFixture.createAddress()
    final Address testAddress2 = AddressFixture.createAddress()

    final AddressWithUserEmail testAddressWithUserEmail = new AddressWithUserEmail(UUID.randomUUID(), "test@example.com", testAddress1.street(), testAddress1.zipcode(), testAddress1.city())

    final User testUser1 = UserFixture.createUser()

    final List<Address> testAddresses = [testAddress1, testAddress2]

    @SpringBean
    final AddressRepository addressRepositoryMock = Mock()

    @SpringBean
    final UserService userServiceMock = Mock()

    final AddressService sut = new AddressService(addressRepositoryMock, userServiceMock)

    def "should get all addresses"() {
        when:
        List<Address> addresses = sut.getAllAddresses()

        then:
        1 * addressRepositoryMock.getAllAddresses() >> testAddresses
        addresses == testAddresses
    }

    def "should create a new address"() {
        when:
        Address createdAddress = sut.createAddress(testAddressWithUserEmail)

        then:
        1 * userServiceMock.getUserByEmailAddress(testAddressWithUserEmail.userEmailAddress()) >> testUser1
        1 * addressRepositoryMock.createAddress(_) >> testAddress1
        createdAddress == testAddress1
    }
}

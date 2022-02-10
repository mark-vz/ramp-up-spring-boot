package com.example.myusermgmt.user.persistence

import com.example.myusermgmt.IntegrationSpecification
import com.example.myusermgmt.address.domain.Address
import com.example.myusermgmt.address.persistence.AddressRepository
import com.example.myusermgmt.address.readmodel.AddressView
import com.example.myusermgmt.fixtures.AddressFixture
import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import com.example.myusermgmt.user.readmodel.ContactView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Commit
import org.springframework.test.context.transaction.TestTransaction

import javax.transaction.Transactional

class ContactViewRepositoryIntegrationSpec extends IntegrationSpecification {

    final User user1 = UserFixture.createUser("Mark", "Snyder", "m.snyder@example.com")
    final Address address1 = AddressFixture.createAddressWithUser(user1)
    final Address address2 = AddressFixture.createAddressWithUser(user1)

    @Autowired
    ContactViewRepository contactViewRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    AddressRepository addressRepository

    def "should correctly retrieve empty list if no contactViews exist yet"() {
        expect:
        contactViewRepository.getAllContactViews() == []
    }

    @Transactional
    @Commit
    def "should create user and addresses and retrieve contactViews"() {
        when:
        User createdUser = userRepository.createUser(user1)

        then:
        createdUser

        when:
        Address createdAddress1 = addressRepository.createAddress(address1)
        Address createdAddress2 = addressRepository.createAddress(address2)

        then:
        createdAddress1
        createdAddress2
        and:
        addressRepository.getAllAddresses().size() == 2

        userRepository.flush()
        addressRepository.flush()

        TestTransaction.end()

        TestTransaction.start()

        when:
        List<ContactView> contactViews = contactViewRepository.getAllContactViews()

        then:
        contactViews.first().firstName() == "Mark"
        contactViews.first().lastName() == "Snyder"
        contactViews.first().emailAddress() == "m.snyder@example.com"
        contactViews.first().addresses().size() == 2
        contactViews.first().addresses() == [AddressView.fromDomain(address1), AddressView.fromDomain(address2)]

        TestTransaction.end()
    }
}

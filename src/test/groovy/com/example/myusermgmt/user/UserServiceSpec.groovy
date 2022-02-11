package com.example.myusermgmt.user

import com.example.myusermgmt.address.readmodel.AddressView
import com.example.myusermgmt.common.exception.UserNotFoundForEmailAddressException
import com.example.myusermgmt.fixtures.ContactViewFixture
import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import com.example.myusermgmt.user.persistence.ContactViewRepository
import com.example.myusermgmt.user.persistence.UserRepository
import com.example.myusermgmt.user.readmodel.ContactView
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class UserServiceSpec extends Specification {

    final User testUser1 = UserFixture.createUser("Mark", "Foo", "mark@...")
    final User testUser2 = UserFixture.createUser("Jan", "Bar", "jan@...")

    final AddressView testAddressView1 = new AddressView("str1", "50354", "Hürth")
    final AddressView testAddressView2 = new AddressView("str2", "50123", "Brühl")

    final ContactView testContactView1 = ContactViewFixture.createContactView("Mark", "Foo", "mark@...", [testAddressView1, testAddressView2])
    final ContactView testContactView2 = ContactViewFixture.createContactView("Jan", "Bar", "jan@...", [testAddressView1])

    final List<User> testUsers = [testUser1, testUser2]
    final List<ContactView> testContactViews = [testContactView1, testContactView2]

    @SpringBean
    final UserRepository userRepositoryMock = Mock()

    @SpringBean
    final ContactViewRepository contactViewRepositoryMock = Mock()

    final UserService sut = new UserService(userRepositoryMock, contactViewRepositoryMock)

    def "should get all users"() {
        when:
        List<User> users = sut.getAllUsers()

        then:
        1 * userRepositoryMock.getAllUsers() >> testUsers
        users == testUsers
    }

    def "should get user by email address"() {
        given:
        def emailAddress = "foo@example.com"

        when:
        User user = sut.getUserByEmailAddress(emailAddress)

        then:
        1 * userRepositoryMock.getUserByEmailAddress(emailAddress) >> testUser1
        user == testUser1
    }

    def "getUserByEmailAddress: should throw exception if no user for given email address exists"() {
        given:
        def emailAddress = "does-not-exist@example.com"

        when:
        sut.getUserByEmailAddress(emailAddress)

        then:
        1 * userRepositoryMock.getUserByEmailAddress(emailAddress) >> null
        UserNotFoundForEmailAddressException ex = thrown()
        ex.message == "user not found for given email address"
    }

    def "should get all contact views"() {
        when:
        List<ContactView> contactViews = sut.getAllContactViews()

        then:
        1 * contactViewRepositoryMock.getAllContactViews() >> testContactViews
        contactViews == testContactViews
    }

    def "should create a new user"() {
        when:
        User createdUser = sut.createUser(testUser1)

        then:
        1 * userRepositoryMock.createUser(testUser1) >> testUser1
        createdUser == testUser1
    }
}

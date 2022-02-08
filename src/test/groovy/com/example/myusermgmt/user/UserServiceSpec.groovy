package com.example.myusermgmt.user

import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import com.example.myusermgmt.user.persistence.UserRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class UserServiceSpec extends Specification {

    final User user1 = UserFixture.createUser("Mark", "Foo", "mark@...")
    final User user2 = UserFixture.createUser("Jan", "Bar", "jan@...")

    final List<User> testUsers = [user1, user2]

    @SpringBean
    final UserRepository userRepositoryMock = Mock()

    final UserService sut = new UserService(userRepositoryMock)

    def "should get all users"() {
        when:
        List<User> users = sut.getAllUsers()

        then:
        1 * userRepositoryMock.getAllUsers() >> testUsers
        users == testUsers
    }

    def "should create a new user"() {
        when:
        User createdUser = sut.createUser(user1)

        then:
        1 * userRepositoryMock.createUser(user1) >> user1
        createdUser == user1
    }
}

package com.example.myusermgmt.user.persistence

import com.example.myusermgmt.IntegrationSpecification
import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import org.springframework.beans.factory.annotation.Autowired

class UserRepositorySpec extends IntegrationSpecification {

    final User user1 = UserFixture.createUser("John Maria", "Doe-Smith", "doe-smith@example.com")
    final User user2 = UserFixture.createUser("Peter Maria", "Parker-Bowl", "parker-bowl@example.com")

    @Autowired
    UserRepository userRepository

    def "should correctly persist and retrieve user"() {
        when:
        User createdUser = userRepository.createUser(user1)

        then:
        List<User> users = userRepository.getAllUsers()
        users.size() == 1
        users == [user1]
        users == [createdUser]
    }

    def "should retrieve user by email address"() {
        when:
        userRepository.createUser(user1)
        userRepository.createUser(user2)

        then:
        User userJohn = userRepository.getUserByEmailAddress("doe-smith@example.com")
        User userPeter = userRepository.getUserByEmailAddress("parker-bowl@example.com")
        and:
        userJohn.lastName() == "Doe-Smith"
        userJohn.emailAddress() == "doe-smith@example.com"
        userPeter.lastName() == "Parker-Bowl"
        userPeter.emailAddress() == "parker-bowl@example.com"
    }

    def "should return null if no user for given email address exists"() {
        expect:
        userRepository.getUserByEmailAddress("not-existent@example.com") == null
    }
}

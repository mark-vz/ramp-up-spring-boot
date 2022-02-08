package com.example.myusermgmt.user.persistence

import com.example.myusermgmt.IntegrationSpecification
import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import org.springframework.beans.factory.annotation.Autowired

class UserRepositorySpec extends IntegrationSpecification {

    final User user1 = UserFixture.createUser()

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
}

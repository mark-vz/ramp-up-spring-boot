package com.example.myusermgmt.user

import com.example.myusermgmt.IntegrationSpecification
import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import com.example.myusermgmt.user.persistence.UserRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired

class UserServiceCacheSpec extends IntegrationSpecification {

    final User testUser1 = UserFixture.createUser("Mark", "Foo", "mark@...")
    final User testUser2 = UserFixture.createUser("Jan", "Bar", "jan@...")

    final List<User> testUsers = [testUser1, testUser2]

    @SpringBean
    final UserRepository userRepositoryMock = Mock()

    @Autowired
    UserService sut

    def "should get all users from cache"() {
        when:
        List<User> users1 = sut.getAllUsers()
        List<User> users2 = sut.getAllUsers()

        then:
        1 * userRepositoryMock.getAllUsers() >> testUsers
        users1 == testUsers
        users2 == testUsers
    }
}

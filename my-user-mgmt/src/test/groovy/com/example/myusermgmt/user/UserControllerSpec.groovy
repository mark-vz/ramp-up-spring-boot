package com.example.myusermgmt.user

import com.example.myusermgmt.user.domain.User
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = UserController.class)
class UserControllerSpec extends Specification {

    final User user1 = new User(UUID.randomUUID(), "Mark", "Foo", "mark@...")
    final User user2 = new User(UUID.randomUUID(), "Jan", "Bar", "jan@...")

    @Autowired
    MockMvc mockMvc

    @SpringBean
    final UserController sut = Mock()

    def "getUsers endpoint is called"() {
        when:
        ResultActions resultActions = mockMvc.perform(get("/api/users"))

        then:
        1 * sut.getUsers() >> [user1, user2]
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        '[{"firstName": "Mark", "lastName":  "Foo", "emailAddress":  "mark@..."}, {"firstName": "Jan", "lastName":  "Bar", "emailAddress":  "jan@..."}]'
                ))
                .andReturn()
    }
}

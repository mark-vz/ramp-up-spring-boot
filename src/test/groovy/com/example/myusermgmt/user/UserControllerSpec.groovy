package com.example.myusermgmt.user

import com.example.myusermgmt.fixtures.UserFixture
import com.example.myusermgmt.user.domain.User
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = UserController.class)
class UserControllerSpec extends Specification {

    final User user1 = UserFixture.createUser("Mark", "Foo", "mark@...")
    final User user2 = UserFixture.createUser("Jan", "Bar", "jan@...")

    @Autowired
    MockMvc mockMvc

    @SpringBean
    final UserService userServiceMock = Mock()

    def "should get users"() {
        when:
        ResultActions resultActions = mockMvc.perform(get("/api/users"))

        then:
        1 * userServiceMock.getAllUsers() >> [user1, user2]
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json('[{"firstName": "Mark", "lastName":  "Foo", "emailAddress":  "mark@..."}, {"firstName": "Jan", "lastName":  "Bar", "emailAddress":  "jan@..."}]'))
    }

    def "should create user"() {
        when:
        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"firstName": "Jan", "lastName":  "Bar", "emailAddress":  "jan@..."}'))

        then:
        1 * userServiceMock.createUser(_) >> user2
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json('{"id": "' + user2.id() + '" ,"firstName": "Jan", "lastName":  "Bar", "emailAddress":  "jan@..."}'))
    }

    def "should throw validation error during user creation if names or email address are too short"() {
        when:
        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"${firstname}\", \"lastName\":  \"${lastname}\", \"emailAddress\":  \"${emailAddress}\"}"))

        then:
        0 * userServiceMock.createUser(_)
        def result = resultActions
                .andExpect(status().isBadRequest())
                .andReturn()
        and:
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) result.getResolvedException()
        List<String> errorMessages = ex.getFieldErrors().defaultMessage
        errorMessages.size() == expectedErrorMessages.size()
        errorMessages.containsAll(expectedErrorMessages)

        where:
        firstname | lastname | emailAddress || expectedErrorMessages
        ''        | '1'      | '333'        || ['firstname must not be blank']
        '1'       | ''       | '333'        || ['lastname must not be blank']
        '1'       | '1'      | '22'         || ['email address must be at least 3 characters long']
        ''        | ''       | '333'        || ['firstname must not be blank', 'lastname must not be blank']
    }

    def "should create and validate DTO correctly"() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        Validator validator = factory.getValidator()

        var createUserDto = new CreateUserDto(firstname, lastname, emailAddress)

        Set<ConstraintViolation<CreateUserDto>> violations = validator.validate(createUserDto)

        expect:
        violations.size() == validationErrorCount
        and:
        createUserDto.firstName() == firstname
        createUserDto.lastName() == lastname
        createUserDto.emailAddress() == emailAddress

        where:
        firstname | lastname | emailAddress    || validationErrorCount
        'a'       | 'b'      | 'm@example.com' || 0
        ''        | 'b'      | 'm@example.com' || 1
        'a'       | ''       | 'm@example.com' || 1
        'a'       | 'b'      | ''              || 1
        'a'       | 'b'      | '1'             || 1
        'a'       | 'b'      | '12'            || 1
        'a'       | 'b'      | '123'           || 0
        ''        | ''       | ''              || 3
    }
}

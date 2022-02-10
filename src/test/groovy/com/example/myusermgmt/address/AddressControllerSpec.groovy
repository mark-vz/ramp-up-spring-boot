package com.example.myusermgmt.address

import com.example.myusermgmt.address.domain.Address
import com.example.myusermgmt.fixtures.AddressFixture
import com.example.myusermgmt.user.CreateUserDto
import com.example.myusermgmt.user.UserService
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

@WebMvcTest(controllers = AddressController.class)
class AddressControllerSpec extends Specification {

    final Address address1 = AddressFixture.createAddress("Teststrasse", "12345", "Hamburg", "b659ebb1-d794-459d-9a96-d6d5627a41a7")
    final Address address2 = AddressFixture.createAddress("Allee 1", "12341", "Kiel", "b659ebb1-d794-459d-9a96-d6d5627a41a1")

    @Autowired
    MockMvc mockMvc

    @SpringBean
    final AddressService addressService = Mock()

    def "should get addresses"() {
        when:
        ResultActions resultActions = mockMvc.perform(get("/api/addresses"))

        then:
        1 * addressService.getAllAddresses() >> [address1, address2]
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        '[{"street": "Teststrasse", "zipcode":  "12345", "city": "Hamburg", "user": "b659ebb1-d794-459d-9a96-d6d5627a41a1" }, ' +
                        '{"street": "Allee 1", "zipcode":  "12341", "city":  "Kiel", "user": "b659ebb1-d794-459d-9a96-d6d5627a41a7"}]'
                ))
    }

    def "should create address"() {
        when:
        ResultActions resultActions = mockMvc.perform(post("/api/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"street": "Hamburg", "zipCode":  "12345", "city": "Hamburg", "user": "b659ebb1-d794-459d-9a96-d6d5627a41a1"}'))

        then:
        1 * addressService.createAddress(_) >> address2
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json('{"id": "' + address2.id() + '" ,"street": "Hamburg", "zipCode":  "12345", "city":  "Hamburg", "user":  "b659ebb1-d794-459d-9a96-d6d5627a41a1"}'))
    }

    def "should throw validation error during address creation if street, zipcode or city are too short"() {
        when:
        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"street\": \"${street}\", \"zipcode\":  \"${zipcode}\", \"city\":  \"${city}\", \"user\":  \"${user}\"}"))

        then:
        0 * addressService.createAddress(_)
        def result = resultActions
                .andExpect(status().isBadRequest())
                .andReturn()
        and:
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) result.getResolvedException()
        List<String> errorMessages = ex.getFieldErrors().defaultMessage
        errorMessages.size() == expectedErrorMessages.size()
        errorMessages.containsAll(expectedErrorMessages)

        where:
        street | zipcode | city      | user                                     || expectedErrorMessages
        ''     | '1'     | 'Hamburg' | 'b659ebb1-d794-459d-9a96-d6d5627a41a7'   || ['street must not be blank']
        '1'    | ''      | 'Hamburg' | 'b659ebb1-d794-459d-9a96-d6d5627a41a7'   || ['zipcode must not be blank']
        '1'    | '1'     | '' | 'b659ebb1-d794-459d-9a96-d6d5627a41a7'          || ['city must be 3 characters long']
        ''     | ''      | 'Hamburg' | 'b659ebb1-d794-459d-9a96-d6d5627a41a7'   || ['street must not be blank', 'city must not be blank']
    }

    def "should create and validate DTO correctly"() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        Validator validator = factory.getValidator()

        var createUserDto = new CreateAddressDto(street, zipcode, city, user)
        Set<ConstraintViolation<CreateAddressDto>> violations = validator.validate(CreateAddressDto)

        expect:
        violations.size() == validationErrorCount
        and:
        createUserDto.street() == street
        createUserDto.zipcode() == zipcode
        createUserDto.city() == city
        createUserDto.user() == user

        where:
        street    | zipcode  | city             | user                                     || validationErrorCount
        'a'       | 'b'      | 'm@example.com'  | UUID.randomUUID().toString()             || 0
        ''        | 'b'      | 'm@example.com'  | UUID.randomUUID().toString()             || 1
        'a'       | ''       | 'm@example.com'  | UUID.randomUUID().toString()             || 1
        'a'       | 'b'      | ''               | UUID.randomUUID().toString()             || 1
        'a'       | 'b'      | '1'              | UUID.randomUUID().toString()             || 1
        'a'       | 'b'      | '12'             | UUID.randomUUID().toString()             || 1
        'a'       | 'b'      | '123'            | UUID.randomUUID().toString()             || 0
        ''        | ''       | ''               | UUID.randomUUID().toString()             || 3
    }
}

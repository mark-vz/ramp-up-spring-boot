package com.example.myusermgmt.address

import com.example.myusermgmt.address.domain.Address
import com.example.myusermgmt.fixtures.AddressFixture
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = AddressController.class)
class AddressControllerSpec extends Specification {

    final Address testAddress1 = AddressFixture.createAddress("street 1", "12345", "Foo City")
    final Address testAddress2 = AddressFixture.createAddress("street 2", "33333", "Bar City")

    @Autowired
    MockMvc mockMvc

    @SpringBean
    final AddressService addressServiceMock = Mock()

    def "should get addresses"() {
        when:
        ResultActions resultActions = mockMvc.perform(get("/api/addresses"))

        then:
        1 * addressServiceMock.getAllAddresses() >> [testAddress1, testAddress2]
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json('[{"street": "street 1", "zipcode":  "12345", "city":  "Foo City", "user": {"firstName":  "John", "lastName": "Doe", "emailAddress": "john@example.com"}}, {"street": "street 2", "zipcode":  "33333", "city":  "Bar City", "user": {"firstName":  "John", "lastName": "Doe", "emailAddress": "john@example.com"}}]'))
    }

    def "should create address"() {
        when:
        ResultActions resultActions = mockMvc.perform(post("/api/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"email": "foo@example.com", "street": "street 1", "zipcode":  "12345", "city":  "Foo City"}'))

        then:
        1 * addressServiceMock.createAddress(_) >> testAddress1
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json('{"id": "' + testAddress1.id() + '" ,"street": "street 1", "zipcode":  "12345", "city":  "Foo City", "user": {"firstName":  "John", "lastName": "Doe", "emailAddress": "john@example.com"}}'))
    }
}

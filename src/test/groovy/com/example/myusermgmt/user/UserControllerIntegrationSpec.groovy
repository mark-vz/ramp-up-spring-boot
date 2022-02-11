package com.example.myusermgmt.user

import com.example.myusermgmt.IntegrationSpecification
import groovy.json.JsonSlurper
import io.restassured.http.ContentType
import io.restassured.response.Response
import org.springframework.test.context.jdbc.Sql
import spock.lang.Stepwise

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD

@Stepwise
class UserControllerIntegrationSpec extends IntegrationSpecification {

    JsonSlurper jsonSlurper = new JsonSlurper()

    def "should create user"() {
        when:
        Response response = given()
                .body('{"firstName": "Mark", "lastName": "Smith", "emailAddress": "m.smith@example.com"}')
                .when()
                .post("/api/users")

        then:
        response
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200)

        when:
        def json = jsonSlurper.parseText(response.asString())

        then:
        json['id']
        json['firstName'] == 'Mark'
        json['lastName'] == 'Smith'
        json['emailAddress'] == 'm.smith@example.com'
    }

    // This @Sql statement overrides the statement defined in the IntegrationSpecification class so that the previously created user is preserved
    @Sql(executionPhase = BEFORE_TEST_METHOD, statements = [
            "insert into users (id, first_name, last_name, email_address) values ('b282a3cd-d540-4de5-86a3-46e2d8d585b9', 'Bob', 'Brown', 'b.brown@example.com')",
            "insert into users (id, first_name, last_name, email_address) values ('a63e029d-11af-44a3-8479-cf8477749745', 'Tommy', 'Lee', 't.lee@example.com')",
    ])
    def "should get all users"() {
        when:
        Response response = given()
                .when()
                .get("/api/users")

        then:
        response
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200)

        when:
        List json = jsonSlurper.parseText(response.asString()) as List

        then:
        json.size() == 3
        and:
        json[0]['firstName'] == 'Mark'
        json[1]['firstName'] == 'Bob'
        json[2]['firstName'] == 'Tommy'
    }
}

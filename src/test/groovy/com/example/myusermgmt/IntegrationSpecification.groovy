package com.example.myusermgmt

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD

@ActiveProfiles("test")
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        classes = MyUserManagementApplication.class,
        initializers = [
                PostgresResource.class,
        ]
)
@Sql(executionPhase = BEFORE_TEST_METHOD, statements = [
        "DELETE FROM addresses",
        "DELETE FROM users",
])
class IntegrationSpecification extends Specification {
}

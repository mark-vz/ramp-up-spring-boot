package com.example.myusermgmt

import org.jboss.logging.Logger
import org.junit.ClassRule
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainerProvider
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class PostgresResource implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = Logger.getLogger(PostgresResource.class)

    private static final String POSTGRESQL_IMAGE_VERSION = "13.3"

    @Container
    @ClassRule
    protected static final JdbcDatabaseContainer db = (JdbcDatabaseContainer) new PostgreSQLContainerProvider()
            .newInstance(POSTGRESQL_IMAGE_VERSION)
            .withReuse(true)

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        db.setNetworkMode("bridge")
        db.start()
        logger.infov("**** Test DB: {0}, user: {1} / pass: {2}", db.getJdbcUrl(), db.getUsername(), db.getPassword())
        TestPropertyValues.of(
                "spring.datasource.url=" + db.getJdbcUrl(),
                "spring.datasource.username=" + db.getUsername(),
                "spring.datasource.password=" + db.getPassword()
        ).applyTo(applicationContext.getEnvironment())
    }
}

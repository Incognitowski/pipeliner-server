package io.pipeliner.extensions

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.pipeliner.infrastructure.environment.DatabaseEnvironmentVariables
import javax.sql.DataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class DatabaseTest : AfterAllCallback, BeforeAllCallback, AfterEachCallback, BeforeEachCallback {

    private var postgresPort : Int = 0
    private lateinit var container : PostgreSQLContainer<*>

    override fun beforeAll(context: ExtensionContext?) {
        container = PostgreSQLContainer("postgres")
            .withDatabaseName("postgres")
            .withPassword("root")
            .withUsername("postgres")
            .also {
                it.start()
            }
        waitForContainerToBeReady()
    }

    override fun beforeEach(context: ExtensionContext?) {
        Database.connect(getDataSource())
        Flyway.configure().dataSource(getDataSource()).load().also {
            it.migrate()
        }
    }

    override fun afterEach(context: ExtensionContext?) {
        container.stop()
        container.start()
        waitForContainerToBeReady()
    }

    override fun afterAll(context: ExtensionContext?) {
        container.stop()
    }

    private fun getDataSource(): DataSource {
        postgresPort = container.firstMappedPort ?: 5432
        return HikariConfig().apply {
            jdbcUrl = DatabaseEnvironmentVariables.jdbcUrl.replace("5432", "$postgresPort")
            driverClassName = DatabaseEnvironmentVariables.driverClass
            username = DatabaseEnvironmentVariables.username
            password = DatabaseEnvironmentVariables.password
            maximumPoolSize = DatabaseEnvironmentVariables.maximumPoolSize
            minimumIdle = DatabaseEnvironmentVariables.minimumIdleConnections
        }.let { HikariDataSource(it) }
    }

    private fun waitForContainerToBeReady() {
        while(!container.logs.contains("database system is ready to accept connections")) {
            Thread.sleep(1_000)
        }
    }
}

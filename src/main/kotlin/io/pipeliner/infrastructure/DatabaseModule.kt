package io.pipeliner.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.pipeliner.infrastructure.environment.DatabaseEnvironmentVariables
import javax.sql.DataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DatabaseModule {
    operator fun invoke() = module {
        single<DataSource>(named("main")) {
             HikariConfig().apply {
                jdbcUrl = DatabaseEnvironmentVariables.jdbcUrl
                driverClassName = DatabaseEnvironmentVariables.driverClass
                username = DatabaseEnvironmentVariables.username
                password = DatabaseEnvironmentVariables.password
                maximumPoolSize = DatabaseEnvironmentVariables.maximumPoolSize
                minimumIdle = DatabaseEnvironmentVariables.minimumIdleConnections
            }.let { HikariDataSource(it) }
        }
    }
}

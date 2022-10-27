package io.pipeliner.infrastructure.environment

object DatabaseEnvironmentVariables {

    val jdbcUrl = System.getenv(Keys.DATABASE_JDBC_URL) ?: Defaults.DATABASE_JDBC_URL
    val driverClass = System.getenv(Keys.DATABASE_DRIVER_CLASS) ?: Defaults.DATABASE_DRIVER_CLASS
    val username = System.getenv(Keys.DATABASE_USERNAME) ?: Defaults.DATABASE_USERNAME
    val password = System.getenv(Keys.DATABASE_PASSWORD) ?: Defaults.DATABASE_PASSWORD
    val maximumPoolSize = System.getenv(Keys.DATABASE_MAXIMUM_POOL_SIZE)?.toIntOrNull()
        ?: Defaults.DATABASE_MAXIMUM_POOL_SIZE
    val minimumIdleConnections = System.getenv(Keys.DATABASE_MINIMUM_IDLE_CONNECTIONS)?.toIntOrNull()
        ?: Defaults.DATABASE_MINIMUM_IDLE_CONNECTIONS

    private object Keys {
        const val DATABASE_JDBC_URL = "DATABASE_JDBC_URL"
        const val DATABASE_DRIVER_CLASS = "DATABASE_DRIVER_CLASS"
        const val DATABASE_USERNAME = "DATABASE_USERNAME"
        const val DATABASE_PASSWORD = "DATABASE_PASSWORD"
        const val DATABASE_MAXIMUM_POOL_SIZE = "DATABASE_MAXIMUM_POOL_SIZE"
        const val DATABASE_MINIMUM_IDLE_CONNECTIONS = "DATABASE_MINIMUM_IDLE_CONNECTIONS"
    }

    private object Defaults {
        const val DATABASE_JDBC_URL = "jdbc:pgsql://localhost:5432/postgres"
        const val DATABASE_DRIVER_CLASS = "com.impossibl.postgres.jdbc.PGDriver"
        const val DATABASE_USERNAME = "postgres"
        const val DATABASE_PASSWORD = "root"
        const val DATABASE_MAXIMUM_POOL_SIZE = 5
        const val DATABASE_MINIMUM_IDLE_CONNECTIONS = 1
    }
}

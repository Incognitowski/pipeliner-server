package io.pipeliner.infrastructure.environment

object ServerEnvironmentVariables {

    val serverPort = System.getenv(Keys.SERVER_PORT)?.toIntOrNull() ?: Defaults.SERVER_PORT
    val serviceKey = System.getenv(Keys.SERVICE_KEY) ?: Defaults.SERVICE_KEY

    private object Keys {
        const val SERVER_PORT = "SERVER_PORT"
        const val SERVICE_KEY = "SERVICE_KEY"
    }

    private object Defaults {
        const val SERVER_PORT = 7070
        const val SERVICE_KEY = "secret"
    }
}

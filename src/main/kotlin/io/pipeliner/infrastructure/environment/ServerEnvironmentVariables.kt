package io.pipeliner.infrastructure.environment

object ServerEnvironmentVariables {

    val serverPort = System.getenv(Keys.SERVER_PORT)?.toIntOrNull() ?: Defaults.SERVER_PORT

    private object Keys {
        const val SERVER_PORT = "SERVER_PORT"
    }
    private object Defaults {
        const val SERVER_PORT = 7070
    }
}

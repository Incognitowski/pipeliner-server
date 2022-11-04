package io.pipeliner.infrastructure

import io.javalin.security.AccessManager
import io.pipeliner.transport.configuration.PipelinerAccessManager
import org.koin.dsl.module

object ConfigurationModule {

    operator fun invoke() = module {
        single<AccessManager> { PipelinerAccessManager() }
    }
}

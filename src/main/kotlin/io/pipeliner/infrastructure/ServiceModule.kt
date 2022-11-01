package io.pipeliner.infrastructure

import io.pipeliner.data.access.IServiceDAO
import io.pipeliner.data.service.ServiceDAO
import org.koin.dsl.module

object ServiceModule {

    operator fun invoke() = module {
        single<IServiceDAO> { ServiceDAO() }
    }
}

package io.pipeliner.infrastructure

import io.pipeliner.data.action.ActionDAO
import io.pipeliner.data.access.IActionDAO
import io.pipeliner.domain.ActionService
import io.pipeliner.domain.access.IActionService
import io.pipeliner.transport.action.ActionController
import io.pipeliner.transport.action.ActionRouter
import org.koin.dsl.module

object ActionModule {

    operator fun invoke() = module {
        single<IActionDAO> { ActionDAO() }
        single<IActionService> { ActionService(get()) }
        single { ActionController(get()) }
        single { ActionRouter(get()) }
    }
}

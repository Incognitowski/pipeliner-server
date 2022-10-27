package io.pipeliner.transport.action

import io.javalin.http.Context
import io.pipeliner.domain.access.IActionService

class ActionController(
    private val actionService: IActionService,
) {

    fun createActions(ctx: Context) {
        actionService.createActions()
        ctx.result("yaaaaaaaaa")
    }
}

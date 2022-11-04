package io.pipeliner.transport.action

import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.pipeliner.commons.exceptions.RequestValidationException
import io.pipeliner.domain.access.IActionService

class ActionController(
    private val actionService: IActionService,
) {
    fun listActionsByServiceId(ctx: Context) {
        val serviceId = ctx.pathParam("serviceId")
        if (serviceId.isBlank())
            throw RequestValidationException("You must provide a non-empty service id to query actions.")
        val actions = actionService.listActionsByService(serviceId)
        ctx.status(HttpStatus.OK)
        ctx.json(actions)
    }
}

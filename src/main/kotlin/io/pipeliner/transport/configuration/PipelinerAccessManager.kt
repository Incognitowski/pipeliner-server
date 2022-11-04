package io.pipeliner.transport.configuration

import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.HttpStatus
import io.javalin.security.AccessManager
import io.javalin.security.RouteRole
import io.pipeliner.infrastructure.environment.ServerEnvironmentVariables
import io.pipeliner.transport.responses.ExceptionResponse

class PipelinerAccessManager : AccessManager {

    override fun manage(handler: Handler, ctx: Context, routeRoles: Set<RouteRole>) {
        val authorizationHeader = ctx.header("Authorization")
        if (authorizationHeader != ServerEnvironmentVariables.serviceKey) {
            ctx.status(HttpStatus.UNAUTHORIZED)
            ctx.json(ExceptionResponse("AUTHORIZATION", "Access Denied."))
            return
        }
        handler.handle(ctx)
    }
}

package io.pipeliner.transport.action

import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post

class ActionRouter(
    private val actionController: ActionController,
) {

    fun registerRoutes() {
        path("actions") {
            post(actionController::createActions)
        }
    }
}

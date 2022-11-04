package io.pipeliner.transport.action

import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path

class ActionRouter(
    private val actionController: ActionController,
) {
    fun registerRoutes() {
        path("actions") {
            get("by-service/{serviceId}", actionController::listActionsByServiceId)
        }
    }
}

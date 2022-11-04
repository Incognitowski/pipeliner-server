package io.pipeliner.domain

import io.pipeliner.data.access.IActionDAO
import io.pipeliner.domain.access.IActionService
import io.pipeliner.domain.objects.action.Action

class ActionService(
    private val actionDAO : IActionDAO
) : IActionService {
    override fun listActionsByService(serviceId: String): List<Action> {
        return actionDAO.listByService(serviceId)
    }
}

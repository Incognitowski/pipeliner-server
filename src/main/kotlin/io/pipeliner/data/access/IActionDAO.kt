package io.pipeliner.data.access

import io.pipeliner.domain.objects.action.Action
import io.pipeliner.domain.objects.action.ActionCreation

interface IActionDAO {
    fun create(actionCreation: ActionCreation): Action
    fun update(action: Action): Action
    fun findById(actionId: String): Action?
    fun listByService(serviceId: String): List<Action>
}

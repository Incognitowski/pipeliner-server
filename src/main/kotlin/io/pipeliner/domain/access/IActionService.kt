package io.pipeliner.domain.access

import io.pipeliner.domain.objects.action.Action

interface IActionService {
    fun listActionsByService(serviceId: String): List<Action>
}

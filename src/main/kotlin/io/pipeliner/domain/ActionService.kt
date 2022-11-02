package io.pipeliner.domain

import io.pipeliner.data.access.IActionDAO
import io.pipeliner.domain.access.IActionService

class ActionService(
    private val actionDAO : IActionDAO
) : IActionService {
    override fun createActions(): List<String> {
        actionDAO.listByService("")
        return emptyList()
    }
}

package io.pipeliner.data

import io.pipeliner.data.access.IActionDAO

class ActionDAO : IActionDAO {
    override fun createActions(): List<String> {
        return emptyList()
    }
}

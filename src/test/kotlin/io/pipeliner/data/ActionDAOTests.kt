package io.pipeliner.data

import io.pipeliner.data.action.ActionDAO
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

internal class ActionDAOTests {

    @Test
    fun `Should return empty list`() {
        val actionDAO = ActionDAO()
        assertEquals(emptyList(), actionDAO.createActions())
    }
}

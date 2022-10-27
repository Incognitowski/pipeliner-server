package io.pipeliner.data

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

internal class ActionDAOTests {

    @Test
    fun `Should return empty list`() {
        val actionDAO = ActionDAO()
        assertEquals(emptyList(), actionDAO.createActions())
    }
}

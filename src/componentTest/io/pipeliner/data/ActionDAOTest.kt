package io.pipeliner.data

import io.pipeliner.commons.exceptions.PersistenceException
import io.pipeliner.data.action.ActionDAO
import io.pipeliner.data.service.ServiceDAO
import io.pipeliner.domain.objects.action.Action
import io.pipeliner.domain.objects.action.ActionCreation
import io.pipeliner.domain.objects.service.ServiceCreation
import io.pipeliner.extensions.DatabaseTest
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DatabaseTest::class)
class ActionDAOTest {

    private lateinit var serviceId: String

    @BeforeEach
    fun beforeEach() {
        resetService()
    }

    @Test
    fun `Should successfully create action`() {
        val actionDAO = ActionDAO()
        val result = assertDoesNotThrow {
            actionDAO.create(ActionCreation(serviceId, "ActionA", "InputA", "OutputA", false))
        }
        assertNotNull(actionDAO.findById(result.id!!))
    }

    @Test
    fun `Should successfully update action`() {
        val actionDAO = ActionDAO()
        val createdAction = actionDAO.create(ActionCreation(serviceId, "ActionA", "InputA", "OutputA", false))
        val updatedService = actionDAO.update(createdAction.copy(outputSample = "{'sample':'input'}"))
        assertNotNull(updatedService.outputSample)
        assertNotNull(updatedService.updatedAt)
    }

    @Test
    fun `Should raise PersistenceException when updating a non-persisted action`() {
        val actionDAO = ActionDAO()
        assertThrows<PersistenceException> {
            actionDAO.update(
                Action(
                    service = serviceId,
                    name = "Non persisted action",
                    inputName = "InputA",
                    outputName = "OutputA",
                    hasHealthCheck = false,
                    createdAt = Instant.now(),
                )
            )
        }
    }

    @Test
    fun `Find by ID should return null on empty database`() {
        val actionDAO = ActionDAO()
        assertNull(actionDAO.findById("non-existent-id"))
    }

    @Test
    fun `Should successfully list by service id`() {
        val actionDAO = ActionDAO()
        val referencedServiceId = serviceId
        actionDAO.create(ActionCreation(serviceId, "ActionA", "InputA", "OutputA", false))
        actionDAO.create(ActionCreation(serviceId, "ActionB", "InputA", "OutputA", false))
        actionDAO.create(ActionCreation(serviceId, "ActionC", "InputA", "OutputA", false))
        actionDAO.create(ActionCreation(serviceId, "ActionD", "InputA", "OutputA", false))
        resetService()
        actionDAO.create(ActionCreation(serviceId, "ActionE", "InputA", "OutputA", false))
        actionDAO.create(ActionCreation(serviceId, "ActionF", "InputA", "OutputA", false))
        val result = actionDAO.listByService(referencedServiceId)
        assertEquals(4, result.size)
        assertTrue { result.all { it.service == referencedServiceId } }
    }

    private fun resetService() {
        serviceId = ServiceDAO().create(ServiceCreation("COMPONENT TEST SERVICE", "http://component-test-service.com")).id!!
    }
}

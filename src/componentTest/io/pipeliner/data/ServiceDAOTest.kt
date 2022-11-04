package io.pipeliner.data

import io.pipeliner.commons.exceptions.PersistenceException
import io.pipeliner.data.service.ServiceDAO
import io.pipeliner.domain.objects.service.Service
import io.pipeliner.domain.objects.service.ServiceCreation
import io.pipeliner.extensions.DatabaseTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DatabaseTest::class)
class ServiceDAOTest {

    @Test
    fun `Should successfully create service`() {
        val serviceName = "NEW_SERVICE_NAME"
        val serviceDAO = ServiceDAO()
        val result = assertDoesNotThrow {
            serviceDAO.create(ServiceCreation(serviceName, "http://whatever-component-test.net"))
            serviceDAO.findByName(serviceName)
        }
        assertEquals(serviceName, result!!.name)
    }

    @Test
    fun `Should successfully update service`() {
        val serviceName = "ANOTHER_SERVICE"
        val serviceDAO = ServiceDAO()
        val createdService = serviceDAO.create(ServiceCreation(serviceName, "http://whatever-component-test.net"))
        val updatedService = serviceDAO.update(createdService.copy(description = "Something something"))
        assertNotEquals("", updatedService.description)
        assertNotNull(updatedService.updatedAt)
    }

    @Test
    fun `Should raise PersistenceException when updating a non-persisted service`() {
        val serviceDAO = ServiceDAO()
        assertThrows<PersistenceException> {
            serviceDAO.update(Service(name = "something", displayName = "something", url = "fake-url"))
        }
    }

    @Test
    fun `Should successfully find service by id`() {
        val serviceName = "NEW_SERVICE_NAME"
        val serviceDAO = ServiceDAO()
        val result = serviceDAO.create(ServiceCreation(serviceName, "http://whatever-component-test.net"))
        assertNotNull(serviceDAO.findById(result.id!!))
    }

    @Test
    fun `Find by ID should return null on empty database`() {
        val serviceDAO = ServiceDAO()
        assertNull(serviceDAO.findById("non-existent-id"))
    }

    @Test
    fun `Find by name should return null on empty database`() {
        val serviceDAO = ServiceDAO()
        assertNull(serviceDAO.findByName("non-existent-service-name"))
    }
}

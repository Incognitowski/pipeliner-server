package io.pipeliner.data.service

import com.github.f4b6a3.ulid.UlidCreator
import io.pipeliner.commons.exceptions.PersistenceException
import io.pipeliner.data.access.IServiceDAO
import io.pipeliner.domain.objects.service.Service
import io.pipeliner.domain.objects.service.ServiceCreation
import java.time.Instant
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ServiceDAO : IServiceDAO {
    override fun create(serviceCreation: ServiceCreation): Service {
        return transaction {
            val service = Service(
                name = serviceCreation.name,
                displayName = serviceCreation.name,
                url = serviceCreation.url,
            )
            val id = ServiceTable.insert {
                it[id] = UlidCreator.getUlid().toString()
                it[name] = service.name
                it[displayName] = service.displayName
                it[description] = service.description
                it[url] = service.url
                it[createdAt] = service.createdAt
                it[updatedAt] = service.updatedAt
            } get ServiceTable.id
            service.copy(id = id)
        }
    }

    override fun update(service: Service): Service {
        val idToUpdate = service.id
            ?: throw PersistenceException("Attempted update on non-persisted service")
        return transaction {
            val instantOfUpdate = Instant.now()
            ServiceTable.update({
                ServiceTable.id eq idToUpdate
            }) {
                it[name] = service.name
                it[displayName] = service.displayName
                it[description] = service.description
                it[url] = service.url
                it[updatedAt] = instantOfUpdate
            }
            service.copy(updatedAt = instantOfUpdate)
        }
    }

    override fun findByName(name: String): Service? {
        return transaction {
            ServiceTable.select {
                ServiceTable.name eq name
            }.firstOrNull()?.toService()
        }
    }

    override fun findById(id: String): Service? {
        return transaction {
            ServiceTable.select {
                ServiceTable.id eq id
            }.firstOrNull()?.toService()
        }
    }
}

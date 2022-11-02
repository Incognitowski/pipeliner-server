package io.pipeliner.data.action

import com.github.f4b6a3.ulid.UlidCreator
import io.pipeliner.commons.exceptions.PersistenceException
import io.pipeliner.data.access.IActionDAO
import io.pipeliner.domain.objects.action.Action
import io.pipeliner.domain.objects.action.ActionCreation
import java.time.Instant
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ActionDAO : IActionDAO {
    override fun create(actionCreation: ActionCreation): Action {
        return transaction {
            val action = Action(
                name = actionCreation.name,
                service = actionCreation.service,
                inputName = actionCreation.inputName,
                outputName = actionCreation.outputName,
                hasHealthCheck = actionCreation.hasHealthCheck,
                createdAt = Instant.now()
            )
            val insertedId = ActionTable.insert {
                it[id] = UlidCreator.getUlid().toString()
                it[service] = action.service
                it[name] = action.name
                it[inputName] = action.inputName
                it[inputSample] = action.inputSample
                it[outputName] = action.outputName
                it[outputSample] = action.outputSample
                it[hasHealthCheck] = action.hasHealthCheck
                it[createdAt] = action.createdAt
                it[updatedAt] = action.updatedAt
            } get ActionTable.id
            action.copy(id = insertedId)
        }
    }

    override fun update(action: Action): Action {
        val idToUpdate = action.id
            ?: throw PersistenceException("Attempted update on non-persisted action")
        return transaction {
            val updateInstant = Instant.now()
            ActionTable.update({
                ActionTable.id eq idToUpdate
            }) {
                it[inputName] = action.inputName
                it[inputSample] = action.inputSample
                it[outputName] = action.outputName
                it[outputSample] = action.outputSample
                it[hasHealthCheck] = action.hasHealthCheck
                it[updatedAt] = updateInstant
            }
            action.copy(updatedAt = updateInstant)
        }
    }

    override fun findById(actionId: String): Action? {
        return transaction {
            ActionTable.select {
                ActionTable.id eq actionId
            }.firstOrNull()?.toAction()
        }
    }

    override fun listByService(serviceId: String): List<Action> {
        return transaction {
            ActionTable.select {
                ActionTable.service eq serviceId
            }.map { it.toAction() }
        }
    }
}

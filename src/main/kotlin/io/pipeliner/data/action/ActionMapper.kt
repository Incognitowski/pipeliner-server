package io.pipeliner.data.action

import io.pipeliner.domain.objects.action.Action
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toAction(): Action {
    return Action(
        id = this[ActionTable.id],
        service = this[ActionTable.service],
        name = this[ActionTable.name],
        inputName = this[ActionTable.inputName],
        inputSample = this[ActionTable.inputSample],
        outputName = this[ActionTable.outputName],
        outputSample = this[ActionTable.outputSample],
        hasHealthCheck = this[ActionTable.hasHealthCheck],
        createdAt = this[ActionTable.createdAt],
        updatedAt = this[ActionTable.updatedAt],
    )
}

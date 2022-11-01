package io.pipeliner.data.service

import io.pipeliner.domain.objects.service.Service
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toService(): Service {
    return Service(
        id = this[ServiceTable.id],
        name = this[ServiceTable.name],
        displayName = this[ServiceTable.displayName],
        description = this[ServiceTable.description],
        url = this[ServiceTable.url],
        createdAt = this[ServiceTable.createdAt],
        updatedAt = this[ServiceTable.updatedAt],
    )
}

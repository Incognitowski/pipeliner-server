package io.pipeliner.data.action

import io.pipeliner.data.service.ServiceTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object ActionTable : Table("action") {
    private const val PRIMARY_KEY_LENGTH = 30
    private const val SERVICE_REFERENCE_LENGTH = 30

    val id = varchar("id", PRIMARY_KEY_LENGTH)
    val service = varchar("service", SERVICE_REFERENCE_LENGTH)
    val name = text("name")
    val inputName = text("input_name")
    val inputSample = text("input_sample").nullable()
    val outputName = text("output_name")
    val outputSample = text("output_sample").nullable()
    val hasHealthCheck = bool("has_health_check")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at").nullable()

    override val primaryKey = PrimaryKey(ServiceTable.id)
}

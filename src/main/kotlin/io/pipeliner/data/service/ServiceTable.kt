package io.pipeliner.data.service

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object ServiceTable : Table("service") {
    private const val PRIMARY_KEY_LENGTH = 30

    val id = varchar("id", PRIMARY_KEY_LENGTH)
    val name = text("name")
    val displayName = text("display_name")
    val description = text("description")
    val url = text("url")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at").nullable()

    override val primaryKey = PrimaryKey(id)
}

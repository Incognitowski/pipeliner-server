package io.pipeliner.domain.objects.service

import java.time.Instant

data class Service(
    val id: String? = null,
    val name: String,
    val displayName: String,
    val description: String = "",
    val url: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant? = null,
)

package io.pipeliner.domain.objects.action

import java.time.Instant

data class Action(
    val id: String? = null,
    val service: String,
    val name: String,
    val inputName: String,
    val inputSample: String? = null,
    val outputName: String,
    val outputSample: String? = null,
    val hasHealthCheck: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
)

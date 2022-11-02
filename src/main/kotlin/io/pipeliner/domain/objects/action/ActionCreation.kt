package io.pipeliner.domain.objects.action

data class ActionCreation(
    val service: String,
    val name: String,
    val inputName: String,
    val outputName: String,
    val hasHealthCheck: Boolean,
)

package io.pipeliner.commons.exceptions

open class PipelinerException(
    message: String,
    val type: ExceptionType,
) : Exception(message)

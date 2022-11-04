package io.pipeliner.transport.responses

import io.pipeliner.commons.exceptions.PipelinerException

data class ExceptionResponse(
    val type: String,
    val message: String,
) {
    companion object {
        fun fromPipelinerException(pipelinerException: PipelinerException): ExceptionResponse {
            return ExceptionResponse(pipelinerException.type.name, pipelinerException.localizedMessage)
        }
    }
}

package io.pipeliner.transport.configuration

import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.pipeliner.commons.exceptions.ExceptionType
import io.pipeliner.commons.exceptions.PipelinerException
import io.pipeliner.transport.responses.ExceptionResponse
import java.lang.Exception
import org.slf4j.LoggerFactory

object ExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun handle(exception: Exception, ctx: Context) {
        logger.info("[${this::class.qualifiedName}] About to handle exception ${exception::class.qualifiedName}")
        when (exception) {
            is PipelinerException -> {
                when (exception.type) {
                    ExceptionType.INTERNAL -> ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    ExceptionType.VALIDATION -> ctx.status(HttpStatus.BAD_REQUEST)
                }
                ctx.json(ExceptionResponse.fromPipelinerException(exception))
            }

            else -> {
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                ctx.json(ExceptionResponse("UNEXPECTED", exception.localizedMessage))
            }
        }
        logger.info("[${this::class.qualifiedName}] Handled exception ${exception::class.qualifiedName}", exception)
    }
}

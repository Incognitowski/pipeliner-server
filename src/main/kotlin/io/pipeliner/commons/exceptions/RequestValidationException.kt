package io.pipeliner.commons.exceptions

class RequestValidationException(message: String) : PipelinerException(message, ExceptionType.VALIDATION)

package io.redbee.spk_seed.domain.error

data class NotFound(val code: Int, val message: String) : ApplicationError
data class UnprocessableEntity(val code: Int, val message: String) : ApplicationError

package io.redbee.spk_seed.application.ports.out

import arrow.core.Either
import io.redbee.spk_seed.domain.Currency
import io.redbee.spk_seed.domain.error.ApplicationError
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CurrencyJdbcOutputPort {
    suspend fun fetch(date: LocalDate): Either<ApplicationError, Flow<Currency>>
}

package io.redbee.spk_seed.adapter.sql

import arrow.core.Either
import arrow.core.rightIfNotNull
import io.redbee.spk_seed.application.ports.out.CurrencyJdbcOutputPort
import io.redbee.spk_seed.domain.Currency
import io.redbee.spk_seed.domain.error.ApplicationError
import io.redbee.spk_seed.domain.error.NotFound
import io.redbee.spk_seed.shared.CompanionLogger
import io.redbee.spk_seed.shared.benchmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class SqlServerAdapter(
    private val client: DatabaseClient,
    private val mapper: CurrencyRowMapper
) : CurrencyJdbcOutputPort {

    companion object : CompanionLogger()

    override suspend fun fetch(date: LocalDate): Either<ApplicationError, Flow<Currency>> =
        log.benchmark("fetch data from legacy database") {
            client
                .sql("SELECT * from MOVPROP;")
                .map(mapper::apply)
                .all()
                .asFlow()
                .map { it.to() }
                .rightIfNotNull { NotFound(404, "Data not found") }
        }
}

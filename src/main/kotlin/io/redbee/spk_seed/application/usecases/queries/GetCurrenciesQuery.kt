package io.redbee.spk_seed.application.usecases.queries

import io.redbee.spk_seed.application.ports.`in`.GetCurrenciesQuery
import io.redbee.spk_seed.domain.Currency
import org.springframework.stereotype.Component

@Component
class GetCurrenciesQuery: GetCurrenciesQuery {
    override fun execute(): Set<Currency> {
        TODO("Not yet implemented")
    }
}

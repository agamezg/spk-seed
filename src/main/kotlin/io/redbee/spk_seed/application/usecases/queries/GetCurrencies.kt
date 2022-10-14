package io.redbee.spk_seed.application.usecases.queries

import io.redbee.spk_seed.application.ports.`in`.GetCurrenciesQuery
import io.redbee.spk_seed.application.ports.out.CurrencyApiClient
import io.redbee.spk_seed.domain.Currency
import org.springframework.stereotype.Component

@Component
class GetCurrencies(
    private val currencyApiClient: CurrencyApiClient
) : GetCurrenciesQuery {

    override fun execute(): Set<Currency> =
        currencyApiClient.getCurrencies()
}

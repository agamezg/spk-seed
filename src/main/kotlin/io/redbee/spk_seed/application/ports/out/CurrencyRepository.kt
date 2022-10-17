package io.redbee.spk_seed.application.ports.out

import io.redbee.spk_seed.domain.Currency

interface CurrencyRepository {
    suspend fun save(currency: Currency): Currency?
}

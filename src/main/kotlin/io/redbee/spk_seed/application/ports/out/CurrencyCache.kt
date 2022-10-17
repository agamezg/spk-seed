package io.redbee.spk_seed.application.ports.out

import io.redbee.spk_seed.domain.Currency

interface CurrencyCache {
    suspend fun get(symbol: String): Currency?
    suspend fun put(currency: Currency): Boolean
}

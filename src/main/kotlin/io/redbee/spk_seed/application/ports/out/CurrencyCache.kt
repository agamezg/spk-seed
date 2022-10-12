package io.redbee.spk_seed.application.ports.out

import io.redbee.spk_seed.domain.Currency

interface CurrencyCache {
    fun get(symbol: String)
    fun put(currency: Currency)
}

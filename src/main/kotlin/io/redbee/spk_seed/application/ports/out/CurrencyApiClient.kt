package io.redbee.spk_seed.application.ports.out

import io.redbee.spk_seed.domain.Currency

interface CurrencyApiClient {
    fun getCurrencies(): Set<Currency>
}

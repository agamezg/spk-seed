package io.redbee.spk_seed.adapter.rest

import io.redbee.spk_seed.application.ports.out.CurrencyApiClient
import io.redbee.spk_seed.domain.Currency

class CurrencyRestAdapter: CurrencyApiClient {
    override fun getCurrencies(): Set<Currency> {
        TODO("Not yet implemented")
    }
}

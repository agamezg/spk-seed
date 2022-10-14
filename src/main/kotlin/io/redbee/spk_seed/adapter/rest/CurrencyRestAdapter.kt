package io.redbee.spk_seed.adapter.rest

import io.redbee.spk_seed.application.ports.out.CurrencyApiClient
import io.redbee.spk_seed.domain.Currency
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyRestAdapter : CurrencyApiClient {
    override fun getCurrencies(): Set<Currency> =
        setOf(
            Currency("BTC", BigDecimal(20000)),
            Currency("ETC", BigDecimal(1800))
        )
}

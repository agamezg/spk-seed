package io.redbee.spk_seed.adapter.cache

import io.redbee.spk_seed.application.ports.out.CurrencyCache
import io.redbee.spk_seed.domain.Currency
import org.springframework.stereotype.Service

@Service
class RedisAdapter : CurrencyCache{

    override fun get(symbol: String) {
        TODO("Not yet implemented")
    }

    override fun put(currency: Currency) {
        TODO("Not yet implemented")
    }
}

package io.redbee.spk_seed.adapter.cache

import io.redbee.spk_seed.application.ports.out.CurrencyCache
import io.redbee.spk_seed.domain.Currency
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisAdapter(
    private val redisClient: ReactiveRedisTemplate<String, Currency>
) : CurrencyCache {

    override suspend fun get(symbol: String): Currency? = redisClient
        .opsForValue().get(symbol).awaitFirstOrNull()


    override suspend fun put(currency: Currency): Boolean = redisClient
        .opsForValue().set(currency.symbol, currency).awaitFirst()
}

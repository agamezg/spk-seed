package io.redbee.spk_seed.adapter.cache.config

import io.redbee.spk_seed.domain.Currency
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {

    @Bean
    fun redisClient(factory: ReactiveRedisConnectionFactory?): ReactiveRedisTemplate<String, Currency>? {
        val serializer = Jackson2JsonRedisSerializer(Currency::class.java)
        val builder = RedisSerializationContext.newSerializationContext<String, Currency>(StringRedisSerializer())
        val context: RedisSerializationContext<String, Currency> = builder.value(serializer).build()
        return ReactiveRedisTemplate(factory!!, context)
    }
}

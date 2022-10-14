package io.redbee.spk_seed.adapter.event.config

import io.redbee.spk_seed.domain.Currency
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class ProducerConfig {

    @Bean
    fun producerFactory(): ProducerFactory<String, Currency> =
        DefaultKafkaProducerFactory(
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
            )
        )

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Currency> =
        KafkaTemplate(producerFactory())
}

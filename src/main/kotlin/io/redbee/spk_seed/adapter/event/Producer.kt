package io.redbee.spk_seed.adapter.event

import io.redbee.spk_seed.application.ports.`in`.BuyCommand
import io.redbee.spk_seed.application.ports.out.CurrencyMessenger
import io.redbee.spk_seed.domain.Currency
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import reactor.util.Logger
import reactor.util.Loggers

@Service
class Producer(
    private val kafkaTemplate: KafkaTemplate<String, Currency>
) : CurrencyMessenger {

    override fun publish(buyCommand: BuyCommand) = kafkaTemplate.send(TOPIC, buyCommand.currency)
        .let { logger.info("Buy command published: $it") }

    companion object {
        const val TOPIC = "buy-commands"
        val logger: Logger = Loggers.getLogger(Producer::class.java)
    }
}

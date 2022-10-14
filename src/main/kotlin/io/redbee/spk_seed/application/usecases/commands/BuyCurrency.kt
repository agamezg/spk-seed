package io.redbee.spk_seed.application.usecases.commands

import io.redbee.spk_seed.application.ports.`in`.BuyCommand
import io.redbee.spk_seed.application.ports.`in`.BuyCurrencyCommand
import io.redbee.spk_seed.application.ports.out.CurrencyCache
import io.redbee.spk_seed.application.ports.out.CurrencyMessenger
import io.redbee.spk_seed.application.ports.out.CurrencyRepository
import org.springframework.stereotype.Component

@Component
class BuyCurrency(
    private val currencyRepository: CurrencyRepository,
    private val currencyMessenger: CurrencyMessenger,
    private val currencyCache: CurrencyCache
) : BuyCurrencyCommand {

    override fun execute(buyCommand: BuyCommand) =
        currencyRepository.save(buyCommand.currency)
            .let { this.currencyCache.put(it) }
            .let { currencyMessenger.publish(buyCommand) }
}

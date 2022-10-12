package io.redbee.spk_seed.application.usecases.commands

import io.redbee.spk_seed.application.ports.`in`.BuyCommand
import io.redbee.spk_seed.application.ports.`in`.BuyCurrencyCommand
import org.springframework.stereotype.Component

@Component
class BuyCurrencyCommand: BuyCurrencyCommand {
    override fun execute(buyCommand: BuyCommand) {
        TODO("Not yet implemented")
    }
}

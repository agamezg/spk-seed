package io.redbee.spk_seed.application.ports.out

import io.redbee.spk_seed.application.ports.`in`.BuyCommand

interface CurrencyMessenger {
    fun publish(buyCommand: BuyCommand)
}

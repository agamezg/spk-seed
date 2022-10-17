package io.redbee.spk_seed.application.ports.out

import io.redbee.spk_seed.application.ports.`in`.BuyCommand

interface CurrencyMessenger {
    suspend fun publish(buyCommand: BuyCommand)
}

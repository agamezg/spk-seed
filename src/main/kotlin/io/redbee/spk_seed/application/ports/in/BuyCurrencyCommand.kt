package io.redbee.spk_seed.application.ports.`in`

import io.redbee.spk_seed.domain.Currency
import java.math.BigDecimal

interface BuyCurrencyCommand {
    suspend fun execute(buyCommand: BuyCommand)
}

data class BuyCommand(
    val currency: Currency,
    val walletAddress: String,
    val amount: BigDecimal
)

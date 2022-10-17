package io.redbee.spk_seed.modelTest

import io.redbee.spk_seed.application.ports.`in`.BuyCommand
import io.redbee.spk_seed.domain.Currency
import java.math.BigDecimal

val aValidBuyCommand =
    BuyCommand(
        currency = Currency("BTC", BigDecimal.TEN),
        walletAddress = "asd123",
        amount = BigDecimal.TEN
    )

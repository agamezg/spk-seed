package io.redbee.spk_seed.domain

import java.math.BigDecimal

data class Currency(
    val symbol: String,
    val value: BigDecimal
)

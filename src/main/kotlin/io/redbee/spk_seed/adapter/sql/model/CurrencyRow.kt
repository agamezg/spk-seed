package io.redbee.spk_seed.adapter.sql.model

import io.redbee.spk_seed.domain.Currency
import java.math.BigDecimal

data class CurrencyRow(
    val symbol: String,
    val value: BigDecimal,
){
    fun to() = Currency(
        symbol = symbol,
        value = value
    )

}


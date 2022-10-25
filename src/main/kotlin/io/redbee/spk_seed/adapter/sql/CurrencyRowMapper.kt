package io.redbee.spk_seed.adapter.sql

import io.r2dbc.spi.Row
import io.redbee.spk_seed.adapter.sql.model.CurrencyRow
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.function.BiFunction

@Component
class CurrencyRowMapper : BiFunction<Row, Any, CurrencyRow> {

    override fun apply(row: Row, o: Any): CurrencyRow =
        CurrencyRow(
            symbol = row.get("symbol", String::class.java) ?: "",
            value = row.get("value", BigDecimal::class.java) ?: BigDecimal.ZERO
        )
}

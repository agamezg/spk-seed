package io.redbee.spk_seed.adapter.database.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.UUID

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrencyVO(
    @Id
    val id: UUID,
    val symbol: String,
    val value: BigDecimal
)

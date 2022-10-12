package io.redbee.spk_seed.application.ports.`in`

import io.redbee.spk_seed.domain.Currency

interface GetCurrenciesQuery {
    fun execute(): Set<Currency>
}

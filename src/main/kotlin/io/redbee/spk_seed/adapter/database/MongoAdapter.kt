package io.redbee.spk_seed.adapter.database

import io.redbee.spk_seed.application.ports.out.CurrencyRepository
import io.redbee.spk_seed.domain.Currency
import org.springframework.stereotype.Repository

@Repository
class MongoAdapter : CurrencyRepository {
    override fun save(currency: Currency): Currency {
        TODO("Not yet implemented")
    }
}

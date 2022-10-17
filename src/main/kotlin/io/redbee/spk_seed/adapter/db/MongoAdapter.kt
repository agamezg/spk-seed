package io.redbee.spk_seed.adapter.db

import io.redbee.spk_seed.application.ports.out.CurrencyRepository
import io.redbee.spk_seed.domain.Currency
import org.springframework.stereotype.Repository

@Repository
class MongoAdapter : CurrencyRepository {
    // TODO insert in mongo
    override fun save(currency: Currency) = currency
}

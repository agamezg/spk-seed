package io.redbee.spk_seed.adapter.db

import io.redbee.spk_seed.application.ports.out.CurrencyRepository
import io.redbee.spk_seed.domain.Currency
import io.redbee.spk_seed.shared.CompanionLogger
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository

@Repository
class MongoAdapter(
    private val reactiveTemplate: ReactiveMongoTemplate
) : CurrencyRepository{

    override suspend fun save(currency: Currency): Currency =
        this.reactiveTemplate.save(currency).awaitFirst()
            .log { info("Currency saved $it") }

    companion object: CompanionLogger()
}

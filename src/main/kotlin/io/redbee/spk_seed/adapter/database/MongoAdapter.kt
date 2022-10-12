package io.redbee.spk_seed.adapter.database

import io.redbee.spk_seed.application.ports.out.CurrencyRepository
import org.springframework.data.mongodb.core.MongoTemplate

class MongoAdapter(
    private val mongoTemplate: MongoTemplate
): CurrencyRepository {

    override fun save() {

    }
}

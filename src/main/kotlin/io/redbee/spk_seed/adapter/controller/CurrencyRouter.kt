package io.redbee.spk_seed.adapter.controller

import io.redbee.spk_seed.application.ports.`in`.BuyCommand
import io.redbee.spk_seed.application.ports.`in`.BuyCurrencyCommand
import io.redbee.spk_seed.application.ports.`in`.GetCurrenciesQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyToFlow

@Component
class CurrencyRouter(
    val getCurrenciesQuery: GetCurrenciesQuery,
    val buyCurrencyCommand: BuyCurrencyCommand
) {

    @Bean
    fun coRouter() =
        org.springframework.web.reactive.function.server.coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/api/v2/currencies").nest {
                POST("", ::buyCurrency)
                GET("", ::getCurrencies)
            }
        }

    private suspend fun buyCurrency(serverRequest: ServerRequest) =
        serverRequest
            .bodyToFlow<BuyCommand>()
            .map { buyCurrencyCommand.execute(it) }
            .toServerResponse()

    private suspend fun getCurrencies(serverRequest: ServerRequest) =
        getCurrenciesQuery
            .execute()
            .asFlow()
            .toServerResponse()

    private suspend inline fun <reified T : Any> Flow<T>.toServerResponse() =
        ServerResponse
            .ok()
            .bodyAndAwait(this)

}

package io.redbee.spk_seed.adapter.controller

import io.redbee.spk_seed.application.ports.`in`.BuyCommand
import io.redbee.spk_seed.application.ports.`in`.BuyCurrencyCommand
import io.redbee.spk_seed.application.ports.`in`.GetCurrenciesQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.util.Loggers

@RestController
@RequestMapping("/api/v1/currencies")
class CurrencyController(
    private val getCurrenciesQuery: GetCurrenciesQuery,
    private val buyCurrencyCommand: BuyCurrencyCommand
) {

    @GetMapping
    fun getCurrencies() = getCurrenciesQuery
        .also { logger.info("Getting currencies") }
        .execute()

    @PostMapping
    fun buyCurrency(@RequestBody buyCommand: BuyCommand) = buyCurrencyCommand.execute(buyCommand)

    companion object {
        val logger = Loggers.getLogger(CurrencyController::class.java)
    }
}

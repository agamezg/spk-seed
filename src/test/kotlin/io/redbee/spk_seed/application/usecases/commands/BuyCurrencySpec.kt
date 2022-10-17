package io.redbee.spk_seed.application.usecases.commands

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.mockk.*
import io.redbee.spk_seed.application.ports.out.CurrencyCache
import io.redbee.spk_seed.application.ports.out.CurrencyMessenger
import io.redbee.spk_seed.application.ports.out.CurrencyRepository
import io.redbee.spk_seed.modelTest.aValidBuyCommand
import kotlinx.coroutines.launch

class BuyCurrencySpec : BehaviorSpec({

    lateinit var currencyRepository: CurrencyRepository
    lateinit var currencyMessenger: CurrencyMessenger
    lateinit var currencyCache: CurrencyCache
    lateinit var buyCurrency: BuyCurrency

    beforeTest {
        currencyRepository = mockk()
        currencyMessenger = mockk()
        currencyCache = mockk()
        buyCurrency = BuyCurrency(
            currencyRepository = currencyRepository,
            currencyMessenger = currencyMessenger,
            currencyCache = currencyCache
        )
    }

    Given("a valid buy command") {
        val aBuyCommand = aValidBuyCommand
        And("mock dependencies") {
            coEvery {
                currencyRepository.save(aBuyCommand.currency)
            } returns aBuyCommand.currency

            coEvery {
                currencyMessenger.publish(aBuyCommand)
            } returns Unit

            coEvery {
                currencyCache.put(aBuyCommand.currency)
            } returns true
        }

        When("call a execute method") {
            runBlocking { buyCurrency.execute(aBuyCommand) }
        }

        Then("verify calls") {
            verify(exactly = 1) { runBlocking { currencyMessenger.publish(aBuyCommand) } }
            verify(exactly = 1) { runBlocking { currencyMessenger.publish(aBuyCommand) } }
            verify(exactly = 1) { runBlocking { currencyCache.put(aBuyCommand.currency) } }
        }
    }


})


class BuyCurrencySpec2 : FunSpec({

    lateinit var currencyRepository: CurrencyRepository
    lateinit var currencyMessenger: CurrencyMessenger
    lateinit var currencyCache: CurrencyCache
    lateinit var buyCurrency: BuyCurrency

    beforeTest {
        currencyRepository = mockk()
        currencyMessenger = mockk()
        currencyCache = mockk()
        buyCurrency = BuyCurrency(
            currencyRepository = currencyRepository,
            currencyMessenger = currencyMessenger,
            currencyCache = currencyCache
        )
    }

    test("sdasdas").config(coroutineTestScope = true) {
        val aBuyCommand = aValidBuyCommand

        coEvery {
            currencyRepository.save(aBuyCommand.currency)
        } returns aBuyCommand.currency

        coEvery {
            currencyMessenger.publish(aBuyCommand)
        } returns Unit

        coEvery {
            currencyCache.put(aBuyCommand.currency)
        } returns true


        launch { buyCurrency.execute(aBuyCommand) }


        coVerify(exactly = 1) { currencyRepository.save(aBuyCommand.currency) }
        coVerify(exactly = 1) { currencyMessenger.publish(aBuyCommand) }
        coVerify(exactly = 1) { currencyCache.put(aBuyCommand.currency) }

    }

})


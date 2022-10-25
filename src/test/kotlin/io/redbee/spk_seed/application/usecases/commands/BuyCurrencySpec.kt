package io.redbee.spk_seed.application.usecases.commands

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.testCoroutineScheduler
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.redbee.spk_seed.application.ports.out.CurrencyCache
import io.redbee.spk_seed.application.ports.out.CurrencyMessenger
import io.redbee.spk_seed.application.ports.out.CurrencyRepository
import io.redbee.spk_seed.modelTest.aValidBuyCommand
import io.redbee.spk_seed.shared.GIVEN
import io.redbee.spk_seed.shared.THEN
import io.redbee.spk_seed.shared.WHEN
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalStdlibApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
object BuyCurrencySpec2 : FunSpec({

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

    test("buy currency successfully").config(coroutineTestScope = true) {
        val aBuyCommand = aValidBuyCommand
        val duration = 1.days

        GIVEN("mock dependencies") {
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


        WHEN("call execute method") {
            launch { buyCurrency.execute(aBuyCommand) }
        }


        testCoroutineScheduler.advanceTimeBy(duration.inWholeMilliseconds)

        THEN("verify calls") {
            coVerify(exactly = 1) { currencyMessenger.publish(aBuyCommand) }
            coVerify(exactly = 1) { currencyRepository.save(aBuyCommand.currency) }
            coVerify(exactly = 1) { currencyCache.put(aBuyCommand.currency) }
        }
    }

})


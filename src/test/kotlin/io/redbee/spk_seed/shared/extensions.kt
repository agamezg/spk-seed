package io.redbee.spk_seed.shared

import io.kotest.core.test.TestScope

fun TestScope.GIVEN(description: String? , body: () -> Any ) = run { body() }
fun TestScope.WHEN(description: String? , body: () -> Any ) = run { body() }
fun TestScope.THEN(description: String? , body: () -> Any ) = run { body() }

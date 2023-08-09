package com.yoti.android.cryptocurrencychallenge.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestContextProvider : CoroutineContextProvider {
    val test = UnconfinedTestDispatcher()

    override val io: CoroutineDispatcher = test

    override val default: CoroutineDispatcher = test

    override val main: CoroutineDispatcher = test
}

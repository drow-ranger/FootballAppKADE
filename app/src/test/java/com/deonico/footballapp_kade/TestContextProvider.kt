package com.deonico.footballapp_kade

import com.deonico.footballapp_kade.helper.CoroutinesContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutinesContextProvider() {
    override val main: CoroutineContext = Unconfined
}
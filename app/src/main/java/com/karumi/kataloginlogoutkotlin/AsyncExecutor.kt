package com.karumi.kataloginlogoutkotlin

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

interface AsyncExecutor {

    val asyncContext: CoroutineContext
    val uiContext: CoroutineContext

    fun async(block: suspend CoroutineScope.() -> Unit) {
        launch(uiContext) { block() }
    }

    suspend fun <T> CoroutineScope.await(block: suspend CoroutineScope.() -> T): T {
        return kotlinx.coroutines.experimental.async(asyncContext) { block.invoke(this) }.await()
    }
}

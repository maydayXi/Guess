package com.wei.guess.db

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: ()-> Unit) {
    IO_EXECUTOR.execute(f)
}
package ru.av3969.stickers.jetpack

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Volatile
    var i = 1

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        myTest()
    }

    fun myTest() = runBlocking {

        launch {
            delay(2000)
            i = 2
        }
        launch {
            delay(3000)
            if(i == 1) {
                println("Still loading...")
            } else {
                println("Already loaded.")
            }
        }
    }
}

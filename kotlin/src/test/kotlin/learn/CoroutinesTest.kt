package learn

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class CoroutinesTest {
    @Test
    fun testFirstExample() {
        runBlocking {
            val job = GlobalScope.launch {
                delay(100L)
                println("World!")
            }
            println("Hello,")

           job.join()
        }
    }
}
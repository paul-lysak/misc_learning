package learn

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicLong


class CoroutinesStressTest {
    @Test
    fun stressTest() {
//        val iMax = 10000
//        val iMax = 10000
        val iMax = 1000
//        val jMax = 1000000
        val jMax = 300
        val c = AtomicLong(0)
//        runBlocking(newFixedThreadPoolContext(2, "defaultCtx")) {
            runBlocking(newFixedThreadPoolContext(5, "defaultCtx")) {
//        runBlocking(newSingleThreadContext("defaultCtx")) {
//        runBlocking {
            for (i in 1..iMax) {
                for (j in 1..jMax) {
//                    launch(start = CoroutineStart.UNDISPATCHED) {
                    launch {
                        //noop
                        c.incrementAndGet()
//                        delay(1)
                    }
                }
                println("end iteration i=$i, c=$c")
            }
        }
        println("end blocking, c=$c")
    }

}
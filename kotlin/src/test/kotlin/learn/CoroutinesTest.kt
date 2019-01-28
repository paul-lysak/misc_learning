package learn

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


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
        println("End blocking")
    }

    @Test
    fun testStructuredExample() {
        runBlocking {
            coroutineScope {
                val job = launch {
                    delay(100L)
                    println("World!")
                }
                println("Hello,")
            }
            println("Scope end")
        }
        println("End blocking")
    }

    @Test
    fun testCancelable() {
        runBlocking {
            val job = launch {
                repeat(1000) { i ->
                    println("Hello, $i")
                    delay(10000L)
                }
            }
            println("Started the loop")
            delay(500L)
            job.cancelAndJoin()
            println("Done with the loop")
        }
    }

    @Test
    fun testWithTimeout() {
        assertThrows<TimeoutCancellationException> {
            runBlocking {
                withTimeout(500) {
                    launch {
                        repeat(1000) { i ->
                            println("Hello, $i")
                            delay(100L)
                        }
                    }
                    println("After the launch")
                }
                println("After the timeout")
            }
        }
    }

    @Test
    fun testWithTimeoutOrNull() {
        runBlocking {
            val result = withTimeoutOrNull(500) {
                launch {
                    repeat(1000) { i ->
                        println("Hello, $i")
                        delay(100L)
                    }
                }
                println("After the launch")
            }
            println("After the timeout: result=$result")
        }
    }

    @Test
    fun testChannel() {
        val ch = Channel<String>()
        runBlocking {
            launch {
                delay(100)
                for (i in 1..3) {
                    println("Sending $i")
                    //Suspends if previous value isn't consumed
                    ch.send("tx$i")
                }
                println("Sent everything")
                ch.close() //without it receiving side suspends forever
            }

            launch {
                println("Receiving:")
                for (i in 1..5) {
                    delay(100)
                    //Suspends until a new value is available. Returns null i fchannel is closed
                    @UseExperimental(kotlinx.coroutines.ObsoleteCoroutinesApi::class)
                    val rx = ch.receiveOrNull()
                    println("Received: $rx")
                }
            }
        }
    }

    @Test
    fun testChannelProduce() {
        //TODO: find out if it's possible to declare such functions without extending CoroutineScope
        @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
        fun CoroutineScope.produceMessages(n: Int): ReceiveChannel<String> = produce {
            for (x in 1..n) send("tx: $x")
        }

        @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
        fun CoroutineScope.processMessages(messages: ReceiveChannel<String>): ReceiveChannel<String> = produce {
            for (m in messages) send("$m - processed")
        }

        runBlocking {
            val messages = processMessages(produceMessages(3))
            @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
            messages.consumeEach { println("Consumed: $it") }
        }
    }

    @Test
    fun testLazyAsync() {
        runBlocking {

            val lazyValue = async(start = CoroutineStart.LAZY) {
                println("started lazy evaluation")
                "LazyResult"
            }

            println("defined lazyValue")
//            lazyValue.start() //may be used to trigger execution without suspsending
            val lazyResult = lazyValue.await()
            println("got result: $lazyResult")
        }
    }

    @Test
    fun testAsyncFunction() {
        //notice - no 'suspend' keyword here
        fun asyncFoo(): Deferred<String> = GlobalScope.async {
            println("asyncFoo started")
            "asyncFooResult"
        }

        //asyncFoo is independent from the outer context here, so if something in runBlocking fails asyncFoo may remain hanging forever
        runBlocking {
            val asyncFooResult = asyncFoo().await()
            println("Foo Result = $asyncFooResult")
        }

    }

    @Test
    fun testCoroutineScope() {
        suspend fun bar(): String = coroutineScope() {
            println("bar started")
            "asyncBarResult"
        }

        //if outer coroutine fails, bar stops as well
        runBlocking(Dispatchers.Default + CoroutineName("blockingCR")) { //customize dispatcher and coroutine name by specifying custom CoroutineContext
            val barResult = bar() //no explicit await, as asyncBar already returns plain string
            val asyncBar: Deferred<String> = async {bar()}
            println("Bar Result = $barResult")
            val asyncBarResult = asyncBar.await()
            println("Async Bar Result = $asyncBarResult")

            val job = coroutineContext[Job]
            println("Job=$job")
        }


    }
}
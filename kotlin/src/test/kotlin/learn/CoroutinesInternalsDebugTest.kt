package learn

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class CoroutinesInternalsDebugTest {
    @Test
    fun testCoroutines() {
        val cd = CoroutinesInternalsExploration()
        runBlocking {
            cd.suspendCaller2()
        }
    }

}
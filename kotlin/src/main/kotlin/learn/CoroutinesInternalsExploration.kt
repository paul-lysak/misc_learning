package learn

//import kotlinx.coroutines.CopyableThrowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class CoroutinesInternalsExploration {
    fun normalFun(): Unit {
        //nop
    }

    suspend fun suspendFun(): Unit {
        suspendCancellableCoroutine<Unit> { cancellableContinuation ->
            cancellableContinuation.resume(Unit)
        }
    }

    suspend fun suspendFun2(): Unit {
        //stack overwriting works like this
        suspendCancellableCoroutine<Unit> { cancellableContinuation ->
            cancellableContinuation.resumeWithException(Exception("suspendFun2 never supposed to complete"))
        }
        //but not like this
//        throw Exception("suspendFun2 never supposed to complete")
    }


    suspend fun suspendCaller1(): Unit {
        suspendFun()
    }

    suspend fun suspendCaller2(): Unit {
//        val a = 2 + 2
//        println(a)
//        for(i in 0..5)
//            suspendFun()
//        delay(10)
        println("***** st1:")
//        CustomException("st1").printStackTrace()
        Exception().printStackTrace()
        suspendFun()
//        delay(10)
        suspendCoroutine<Unit> { continuation ->
            Thread() {
                kotlin.run {
                    Thread.sleep(10)
                    println("***** stMid:")
                    Exception().printStackTrace()
                    continuation.resume(Unit)
                }
            }.start()
        }
        println("***** st2:")
//        CustomException("st2").printStackTrace()
//        Exception().printStackTrace()
//        throw Exception()
//        suspendFun()
        suspendFun2()
    }


}

class CustomException(msg: String): java.lang.Exception() {}
//class CustomException(private val msg: String) : java.lang.Exception(), CopyableThrowable<CustomException> {
//    override fun createCopy(): CustomException? {
//        val ex = CustomException(msg)
//        ex.stackTrace = this.stackTrace
//        return ex
//    }
//
//}


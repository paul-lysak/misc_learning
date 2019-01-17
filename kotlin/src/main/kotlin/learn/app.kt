package learn

fun main(args: Array<String>) {
    println("Hi there!")

    val l1= listOf("lorem", "ipsum", "dolor", "sit", "amet")
    println("l1 = ${l1}, l1 duplicated = ${l1.duplicate()}")

    for(item in l1)
        println("list item: $item")

    val v1: String by lazy { "hi there" }
    println("v1=$v1")

    lateinit var v2: String //lateinit only available for mutable local variables
//    println("v2=$v2") //runtime exception here - not initialized yet
    v2 = "bye there"
    println("v2=$v2")
    v2 = "hi again" //runs ok - can be reassigned
    println("v2=$v2")

    //'new' keyword not used for class creation
    val fc1 = FooClass("firstFoo", "Foo1", 3)
    println("fc1=$fc1, fc1.name=${fc1.name}")

    val sfc1 = SimpleFooClass(name = "secondFoo", description = "foo2")
    println("sfc1=$sfc1")

    val b1 = Bar("stout", "chardonay")
    println("b1=$b1. b1.1=${b1.component1()}")

    val nullableV1: String? = null
    println("nval1=${nullableV1?.toUpperCase() ?: "weee" + "wooo"}")

    val whenInput = "cloud"
//    val whenInput = "grass"
    val whenResult = when (whenInput) {
        "grass" -> "green"
        "cloud" -> "white"
        else -> "idk" //doesn't compile without "else"
    }
    println("whenResult=$whenResult")

    val whenConditionalResult = when {
        whenInput.length > 10 -> "very long word"
        whenInput.length > 5 -> "long word"
        else -> "short word"
    }
    println("whenConditionalResult=$whenConditionalResult")

    for (i in 0 until 10 step 2)
        println("iteration $i")

    val someObject: Any = "actually, a string"
    if(someObject is String) {
        //smart cast
        println("It's a string of length ${someObject.length}: $someObject")
    }

    println("""
        One two
        three four five
        once I've caught
        a fish alive
    """.trimIndent())

    //is there a way to subvert List variance without unsafe casts? Haven't find it yet
    val aMl1 = mutableListOf<Int>(1, 2, 3)
    val aL1: List<Int> = aMl1
    val aL2: List<Any> = aL1
    //doesn't compile - no typecheck of erased type
//    if(aL1 is MutableList<Any>) {
}

//class FooClass constructor(name: String, description: String, count: Int)  - does the same
class FooClass(name: String, description: String, count: Int) {
    val name: String
    val count: Int = count + 1 //members may be initialized without explicit 'init' block

    init {
        println("FooClass created: name=$name, description=$description, count=$count")
        this.name = name //compiler tracks that each 'val' member is initialized here
    }

//    println("Hi there") //doesn't compile - only members declaration possible here

    //no automatic toString for the regular classes
    override fun toString(): String {
        return "FooClass(name=$name, count=$count)"
//        return "FooClass(name=$name, description=$description)" //doesn't compile - constructor arguments available in constructor only
    }
}

class SimpleFooClass(val name: String, description: String) {
    override fun toString(): String {
        return "FooClass(name=$name)"
//        return "FooClass(name=$name, description=$description)" //doesn't compile - only 'val's generate a member automatically
    }
}

//just like case class in Scala
data class Bar(val beer: String, val wine: String) {
    companion object {
        fun description(): String {
            TODO()
        }
    }
}

//extension function
fun <T> List<T>.duplicate() = this.flatMap { element -> listOf(element, element) }


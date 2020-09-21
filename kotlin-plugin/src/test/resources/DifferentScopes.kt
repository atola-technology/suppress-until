import org.atola.internal.suppressUntil.SuppressUntilTestAnnotation

class Test1 {
    fun testFun() {
        @SuppressUntilTestAnnotation("2020.12", "UNUSED_VARIABLE")
        var vasa = 0
    }
}

class Test2 {
    @SuppressUntilTestAnnotation("2020.12", "UNUSED_VARIABLE")
    fun testFun() {
        var vasa = 0
    }
}

@SuppressUntilTestAnnotation("2020.12", "UNUSED_VARIABLE")
class Test3 {
    fun testFun() {
        var vasa = 0
    }
}

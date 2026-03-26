package org.example.logarithm

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NaturalLogarithmIntegrationTest {

    private val ln = NaturalLogarithm()

    companion object {
        private const val DELTA = 1e-9
    }

    @Test
    fun `ln(a) + ln(b) == ln(a times b)`() {
        val a = 33.0
        val b = 22.0
        assertEquals(ln.calculate(a * b), ln.calculate(a) + ln.calculate(b), DELTA)
    }

    @Test
    fun `ln(a) - ln(b) == ln(a div b)`() {
        val a = 33.0
        val b = 22.0
        assertEquals(ln.calculate(a / b), ln.calculate(a) - ln.calculate(b), DELTA)
    }

    @Test
    fun `n times ln(x) == ln(x pow n)`() {
        val x = 5.0
        val n = 5.0
        assertEquals(n * ln.calculate(x), ln.calculate(Math.pow(x, n)), DELTA)
    }
}

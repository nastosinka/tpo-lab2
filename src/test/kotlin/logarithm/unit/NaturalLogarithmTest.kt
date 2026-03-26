package org.example.logarithm

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.E

class NaturalLogarithmTest {

    private val ln = NaturalLogarithm()

    companion object {
        private const val DELTA = 1e-9

        @JvmStatic
        fun knownLnValues(): Stream<Arguments> = Stream.of(
            Arguments.of(1.0, 0.0),
            Arguments.of(E, 1.0),
            Arguments.of(E * E, 2.0),
            Arguments.of(0.5, -0.6931471805599453),
            Arguments.of(2.0, 0.6931471805599453),
            Arguments.of(10.0, 2.302585092994046),
            Arguments.of(0.1, -2.302585092994046),
        )

        @JvmStatic
        fun invalidInputs(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0),
            Arguments.of(-1.0),
            Arguments.of(Double.NaN),
            Arguments.of(Double.POSITIVE_INFINITY),
            Arguments.of(Double.NEGATIVE_INFINITY),
        )
    }

    @ParameterizedTest(name = "ln({0}) = {1}")
    @MethodSource("knownLnValues")
    fun `Проверка значений логарифма на известных значениях`(x: Double, expected: Double) {
        assertEquals(expected, ln.calculate(x), DELTA)
    }

    @ParameterizedTest(name = "ln({0}) throws IllegalArgumentException")
    @MethodSource("invalidInputs")
    fun `Функция выбрасывает IllegalArgumentException для недопустимых значений`(x: Double) {
        assertThrows(IllegalArgumentException::class.java) { ln.calculate(x) }
    }

    @Test
    fun `ln(a * b) = ln(a) + ln(b)`() {
        val a = 3.0
        val b = 5.0
        assertEquals(ln.calculate(a) + ln.calculate(b), ln.calculate(a * b), DELTA)
    }

    @Test
    fun `ln(a div b) = ln(a) - ln(b)`() {
        val a = 7.0
        val b = 2.0
        assertEquals(ln.calculate(a) - ln.calculate(b), ln.calculate(a / b), DELTA)
    }

    @Test
    fun `Проверка сходимости для невысокой точности`() {
        val lowPrec = NaturalLogarithm(1e-3)
        assertEquals(0.0, lowPrec.calculate(1.0), 1e-3)
    }
}

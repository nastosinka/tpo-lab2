package org.example.logarithm

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class BaseNLogarithmTest {

    private val log2  = BaseNLogarithm(2.0)
    private val log10 = BaseNLogarithm(10.0)

    companion object {
        private const val DELTA = 1e-9

        @JvmStatic
        fun knownLog2Values(): Stream<Arguments> = Stream.of(
            Arguments.of(1.0, 0.0),
            Arguments.of(2.0, 1.0),
            Arguments.of(4.0, 2.0),
            Arguments.of(8.0, 3.0),
            Arguments.of(0.5, -1.0),
            Arguments.of(0.25, -2.0),
        )

        @JvmStatic
        fun knownLog10Values(): Stream<Arguments> = Stream.of(
            Arguments.of(1.0, 0.0),
            Arguments.of(10.0, 1.0),
            Arguments.of(100.0, 2.0),
            Arguments.of(0.1, -1.0),
            Arguments.of(0.01, -2.0),
        )

        @JvmStatic
        fun invalidXInputs(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0),
            Arguments.of(-1.0),
            Arguments.of(Double.NaN),
            Arguments.of(Double.POSITIVE_INFINITY),
            Arguments.of(Double.NEGATIVE_INFINITY),
        )

        @JvmStatic
        fun invalidBaseInputs(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0),
            Arguments.of(-2.0),
            Arguments.of(1.0),
            Arguments.of(Double.NaN),
            Arguments.of(Double.POSITIVE_INFINITY),
        )
    }

    @ParameterizedTest(name = "log2({0}) = {1}")
    @MethodSource("knownLog2Values")
    fun `Проверка значений log2 на известных значениях`(x: Double, expected: Double) {
        assertEquals(expected, log2.calculate(x), DELTA)
    }

    @ParameterizedTest(name = "log10({0}) = {1}")
    @MethodSource("knownLog10Values")
    fun `Проверка значений log10 на известных значениях`(x: Double, expected: Double) {
        assertEquals(expected, log10.calculate(x), DELTA)
    }

    @ParameterizedTest(name = "log2({0}) throws IllegalArgumentException")
    @MethodSource("invalidXInputs")
    fun `Функция выбрасывает IllegalArgumentException для недопустимых x`(x: Double) {
        assertThrows(IllegalArgumentException::class.java) { log2.calculate(x) }
    }

    @ParameterizedTest(name = "BaseNLogarithm(base={0}) throws IllegalArgumentException")
    @MethodSource("invalidBaseInputs")
    fun `Логарифм выбрасывает IllegalArgumentException для недопустимых оснований`(base: Double) {
        assertThrows(IllegalArgumentException::class.java) { BaseNLogarithm(base) }
    }

    @Test
    fun `log_b(a * c) = log_b(a) + log_b(c)`() {
        val a = 3.0
        val c = 5.0
        assertEquals(log2.calculate(a) + log2.calculate(c), log2.calculate(a * c), DELTA)
    }

    @Test
    fun `log_b(b) = 1`() {
        val bases = listOf(2.0, 3.0, 5.0, 10.0)
        for (b in bases) {
            assertEquals(1.0, BaseNLogarithm(b).calculate(b), DELTA)
        }
    }

    @Test
    fun `log_b(1) = 0`() {
        assertEquals(0.0, log10.calculate(1.0), DELTA)
    }
}

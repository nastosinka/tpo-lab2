package org.example.trigonometry

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.PI

class SineTest {

    private val sine = Sine()

    companion object {
        private const val DELTA = 1e-9

        @JvmStatic
        fun knownSineValues(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0,            0.0),
            Arguments.of(PI / 6,         0.5),
            Arguments.of(PI / 2,         1.0),
            Arguments.of(PI,             0.0),
            Arguments.of(3.0 * PI / 2,  -1.0),
            Arguments.of(2.0 * PI,       0.0),
            Arguments.of(-PI / 6,       -0.5),
            Arguments.of(-PI / 2,       -1.0),
            Arguments.of(-PI,            0.0),
            Arguments.of(-2.0 * PI,      0.0),
        )

        @JvmStatic
        fun invalidInputs(): Stream<Arguments> = Stream.of(
            Arguments.of(Double.NaN),
            Arguments.of(Double.POSITIVE_INFINITY),
            Arguments.of(Double.NEGATIVE_INFINITY)
        )
    }

    @ParameterizedTest(name = "sin({0}) = {1}")
    @MethodSource("knownSineValues")
    fun `Проверка значений синуса на известных значениях`(x: Double, expected: Double) {
        assertEquals(expected, sine.calculate(x), DELTA)
    }

    @ParameterizedTest(name = "sin({0}) throws IllegalArgumentException")
    @MethodSource("invalidInputs")
    fun `Функция выбрасывает IllegalArgumentException в точках разрывах`(x: Double) {
        assertThrows(IllegalArgumentException::class.java) { sine.calculate(x) }
    }

    @Test
    fun `Синус - нечетная функция`() {
        val x = 1.23
        assertEquals(-sine.calculate(x), sine.calculate(-x), DELTA)
    }

    @Test
    fun `Синус - периодическая функция`() {
        val x = 0.7
        assertEquals(sine.calculate(x), sine.calculate(x + 2 * PI), DELTA)
    }

    @Test
    fun `Проверка сходимости для невысокой точности`() {
        val lowPrec = Sine(1e-3)
        assertEquals(0.0, lowPrec.calculate(0.0), 1e-3)
    }
}

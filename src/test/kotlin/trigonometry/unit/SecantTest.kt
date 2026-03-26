package org.example.trigonometry

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.PI
import kotlin.math.sqrt

class SecantTest {

    private val secant = Secant()

    companion object {
        private const val DELTA = 1e-7

        @JvmStatic
        fun knownSecantValues(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0, 1.0),
            Arguments.of(PI / 3, 2.0),
            Arguments.of(PI, -1.0),
            Arguments.of(2.0 * PI, 1.0),
            Arguments.of(-PI / 3, 2.0),
            Arguments.of(PI / 4, sqrt(2.0)),
            Arguments.of(-PI / 4, sqrt(2.0)),
            Arguments.of(PI / 6, 2.0 / sqrt(3.0)),
        )

        @JvmStatic
        fun singularities(): Stream<Arguments> = Stream.of(
            Arguments.of(PI / 2),
            Arguments.of(-PI / 2),
            Arguments.of(3.0 * PI / 2),
        )

        @JvmStatic
        fun invalidInputs(): Stream<Arguments> = Stream.of(
            Arguments.of(Double.NaN),
            Arguments.of(Double.POSITIVE_INFINITY),
            Arguments.of(Double.NEGATIVE_INFINITY)
        )
    }

    @ParameterizedTest(name = "sec({0}) = {1}")
    @MethodSource("knownSecantValues")
    fun `Проверка значений секанса на известных значениях`(x: Double, expected: Double) {
        assertEquals(expected, secant.calculate(x), DELTA)
    }

    @ParameterizedTest(name = "sec({0}) выбрасывает ArithmeticException")
    @MethodSource("singularities")
    fun `Функция выбрасывает ArithmeticException в точках разрыва`(x: Double) {
        assertThrows(ArithmeticException::class.java) { secant.calculate(x) }
    }

    @ParameterizedTest(name = "sec({0}) выбрасывает IllegalArgumentException")
    @MethodSource("invalidInputs")
    fun `Функция выбрасывает исключение на невалидных входных значениях`(x: Double) {
        assertThrows(IllegalArgumentException::class.java) { secant.calculate(x) }
    }

    @Test
    fun `Секанс - четная функция`() {
        val x = 1.23
        assertEquals(secant.calculate(x), secant.calculate(-x), DELTA)
    }

    @Test
    fun `Секанс - периодическая функция`() {
        val x = 0.7
        assertEquals(secant.calculate(x), secant.calculate(x + 2 * PI), DELTA)
    }

    @Test
    fun `Проверка сходимости для невысокой точности`() {
        val lowPrec = Secant(Cosine(1e-3))
        assertEquals(1.0, lowPrec.calculate(0.0), 1e-3)
    }
}

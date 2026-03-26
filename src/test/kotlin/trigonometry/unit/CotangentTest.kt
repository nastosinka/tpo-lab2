package org.example.trigonometry

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.PI
import kotlin.math.sqrt

class CotangentTest {

    private val cotangent = Cotangent()

    companion object {
        private const val DELTA = 1e-7

        @JvmStatic
        fun knownCotangentValues(): Stream<Arguments> = Stream.of(
            Arguments.of(PI / 4, 1.0),
            Arguments.of(PI / 6, sqrt(3.0)),
            Arguments.of(PI / 3, 1.0 / sqrt(3.0)),
            Arguments.of(PI / 2, 0.0),
            Arguments.of(3.0 * PI / 4, -1.0),
            Arguments.of(-PI / 4, -1.0),
            Arguments.of(-PI / 6, -sqrt(3.0)),
            Arguments.of(-PI / 2, 0.0),
        )

        @JvmStatic
        fun singularities(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0),
            Arguments.of(PI),
            Arguments.of(-PI),
            Arguments.of(2.0 * PI),
        )

        @JvmStatic
        fun invalidInputs(): Stream<Arguments> = Stream.of(
            Arguments.of(Double.NaN),
            Arguments.of(Double.POSITIVE_INFINITY),
            Arguments.of(Double.NEGATIVE_INFINITY)
        )
    }

    @ParameterizedTest(name = "cot({0}) = {1}")
    @MethodSource("knownCotangentValues")
    fun `Проверка значений котангенса на известных значениях`(x: Double, expected: Double) {
        assertEquals(expected, cotangent.calculate(x), DELTA)
    }

    @ParameterizedTest(name = "cot({0}) выбрасывает ArithmeticException")
    @MethodSource("singularities")
    fun `Функция выбрасывает ArithmeticException в точках разрыва`(x: Double) {
        assertThrows(ArithmeticException::class.java) { cotangent.calculate(x) }
    }

    @ParameterizedTest(name = "cot({0}) выбрасывает IllegalArgumentException")
    @MethodSource("invalidInputs")
    fun `Функция выбрасывает исключение на невалидных входных значениях`(x: Double) {
        assertThrows(IllegalArgumentException::class.java) { cotangent.calculate(x) }
    }

    @Test
    fun `Котангенс - нечетная функция`() {
        val x = 1.23
        assertEquals(-cotangent.calculate(x), cotangent.calculate(-x), DELTA)
    }

    @Test
    fun `Котангенс - периодическая функция`() {
        val x = 0.7
        assertEquals(cotangent.calculate(x), cotangent.calculate(x + PI), DELTA)
    }

    @Test
    fun `Проверка сходимости для невысокой точности`() {
        val lowPrec = Cotangent(Sine(1e-3), Cosine(1e-3))
        assertEquals(1.0, lowPrec.calculate(PI / 4), 1e-3)
    }
}

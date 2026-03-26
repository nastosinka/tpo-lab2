package org.example.trigonometry

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.PI

class CosineTest {

    private val cosine = Cosine()

    companion object {
        private const val DELTA = 1e-9

        @JvmStatic
        fun knownCosineValues(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0, 1.0),
            Arguments.of(PI / 3, 0.5),
            Arguments.of(PI / 2, 0.0),
            Arguments.of(PI, -1.0),
            Arguments.of(3.0 * PI / 2, 0.0),
            Arguments.of(2.0 * PI, 1.0),
            Arguments.of(-PI / 3, 0.5),
            Arguments.of(-PI / 2, 0.0),
            Arguments.of(-PI, -1.0),
            Arguments.of(-2.0 * PI, 1.0),
        )

        @JvmStatic
        fun invalidInputs(): Stream<Arguments> = Stream.of(
            Arguments.of(Double.NaN),
            Arguments.of(Double.POSITIVE_INFINITY),
            Arguments.of(Double.NEGATIVE_INFINITY)
        )
    }

    @ParameterizedTest(name = "cos({0}) = {1}")
    @MethodSource("knownCosineValues")
    fun `Проверка значений косинуса на известных значениях`(x: Double, expected: Double) {
        assertEquals(expected, cosine.calculate(x), DELTA)
    }

    @ParameterizedTest(name = "cos({0}) throws IllegalArgumentException")
    @MethodSource("invalidInputs")
    fun `Функция выбрасывает IllegalArgumentException в точках разрывах`(x: Double) {
        assertThrows(IllegalArgumentException::class.java) { cosine.calculate(x) }
    }

    @Test
    fun `Косинус - четная фукнция`() {
        val x = 1.23
        assertEquals(cosine.calculate(x), cosine.calculate(-x), DELTA)
    }

    @Test
    fun `Косинус - периодическая функция`() {
        val x = 0.7
        assertEquals(cosine.calculate(x), cosine.calculate(x + 2 * PI), DELTA)
    }

    @Test
    fun `Проверка сходимости для невысокой точности`() {
        val lowPrec = Cosine(1e-3)
        assertEquals(1.0, lowPrec.calculate(0.0), 1e-3)
    }

}

package org.example

import org.example.trigonometry.*
import org.example.logarithm.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.math.PI

class EquationSystemTest {

    companion object {
        private const val DELTA = 1e-9

        @JvmStatic
        fun safeTrigValues(): Stream<Arguments> = Stream.of(
            Arguments.of(-PI / 4),
            Arguments.of(-PI / 3),
            Arguments.of(-PI / 6)
        )

        @JvmStatic
        fun trigDiscontinuities(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0),
            Arguments.of(-PI / 2),
            Arguments.of(-PI),
            Arguments.of(-3 * PI / 2)
        )

        @JvmStatic
        fun logValues(): Stream<Arguments> = Stream.of(
            Arguments.of(2.0),
            Arguments.of(3.0),
            Arguments.of(10.0)
        )

        @JvmStatic
        fun logDiscontinuities(): Stream<Arguments> = Stream.of(
            Arguments.of(1.0)
        )
    }

    @ParameterizedTest(name = "Тригонометрическая ветка: f({0}) вычисляется")
    @MethodSource("safeTrigValues")
    fun `Проверка тригонометрической ветки безопасных точек`(x: Double) {
        val system = EquationSystem(
            sin = Sine(),
            cos = Cosine(),
            cot = Cotangent(),
            sec = Secant()
        )
        val result = system(x)
        assertTrue(result.isFinite(), "Значение функции должно быть конечным")
    }

    @ParameterizedTest(name = "Логарифмическая ветка выбрасывает ArithmeticException для x={0}")
    @MethodSource("logDiscontinuities")
    fun `Проверка выброса в опасных точках логарифмов`(x: Double) {
        val system = EquationSystem(
            ln = NaturalLogarithm(),
            log3 = BaseNLogarithm(3.0),
            log5 = BaseNLogarithm(5.0),
            log10 = BaseNLogarithm(10.0)
        )
        assertThrows(ArithmeticException::class.java) { system(x) }
    }

    @ParameterizedTest(name = "Функция выбрасывает ArithmeticException в точках разрыва: x={0}")
    @MethodSource("trigDiscontinuities")
    fun `Проверка выброса в точках разрыва`(x: Double) {
        val system = EquationSystem(
            sin = Sine(),
            cos = Cosine(),
            cot = Cotangent(),
            sec = Secant()
        )
        assertThrows(ArithmeticException::class.java) { system(x) }
    }

    @ParameterizedTest(name = "Логарифмическая ветка: f({0}) вычисляется")
    @MethodSource("logValues")
    fun `Проверка логарифмической ветки`(x: Double) {
        val system = EquationSystem(
            ln = NaturalLogarithm(),
            log3 = BaseNLogarithm(3.0),
            log5 = BaseNLogarithm(5.0),
            log10 = BaseNLogarithm(10.0)
        )
        val result = system(x)
        assertTrue(result.isFinite(), "Значение функции должно быть конечным")
    }


}
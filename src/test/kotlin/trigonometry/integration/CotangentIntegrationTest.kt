package org.example.trigonometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.stream.Stream
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@ExtendWith(MockitoExtension::class)
class CotangentIntegrationTest {

    @Mock
    lateinit var mockSine: Sine

    @Mock
    lateinit var mockCosine: Cosine

    @Spy
    val spySine: Sine = Sine()

    @Spy
    val spyCosine: Cosine = Cosine()

    companion object {
        private const val DELTA = 1e-6

        @JvmStatic
        fun cotangentTestValues(): Stream<Arguments> = Stream.of(
            Arguments.of(PI / 4, 1.0),
            Arguments.of(PI / 6, sqrt(3.0)),
            Arguments.of(PI / 3, 1.0 / sqrt(3.0)),
            Arguments.of(PI / 2, 0.0),
            Arguments.of(3.0 * PI / 4, -1.0),
        )
    }

    @Test
    fun `Котангенс вызывает функции синуса и косинуса`() {
        val cot = Cotangent(spySine, spyCosine)
        cot.calculate(1.0)
        verify(spySine, atLeastOnce()).calculate(any())
        verify(spyCosine, atLeastOnce()).calculate(any())
    }

    @ParameterizedTest(name = "mock.cot({0}) = {1}")
    @MethodSource("cotangentTestValues")
    fun `Тестирование котангенса с моками`(x: Double, expected: Double) {
        whenever(mockSine.calculate(x)).thenReturn(sin(x))
        whenever(mockCosine.calculate(x)).thenReturn(cos(x))
        val cot = Cotangent(mockSine, mockCosine)
        assertEquals(expected, cot.calculate(x), DELTA)
    }
}

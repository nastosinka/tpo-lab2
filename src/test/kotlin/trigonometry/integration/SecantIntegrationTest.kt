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
import kotlin.math.sqrt

@ExtendWith(MockitoExtension::class)
class SecantIntegrationTest {

    @Mock
    lateinit var mockCosine: Cosine

    @Spy
    val spyCosine: Cosine = Cosine()

    companion object {
        private const val DELTA = 1e-6

        @JvmStatic
        fun secantTestValues(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0, 1.0),
            Arguments.of(PI / 3, 2.0),
            Arguments.of(PI / 4, sqrt(2.0)),
            Arguments.of(PI / 6, 2.0 / sqrt(3.0)),
            Arguments.of(PI, -1.0),
        )
    }

    @Test
    fun `Секанс вызывает косинус`() {
        val sec = Secant(spyCosine)
        sec.calculate(1.0)
        verify(spyCosine, atLeastOnce()).calculate(any())
    }

    @ParameterizedTest(name = "mock.sec({0}) = {1}")
    @MethodSource("secantTestValues")
    fun `Тестирование секанса с моками`(x: Double, expected: Double) {
        whenever(mockCosine.calculate(x)).thenReturn(cos(x))
        val sec = Secant(mockCosine)
        assertEquals(expected, sec.calculate(x), DELTA)
    }
}

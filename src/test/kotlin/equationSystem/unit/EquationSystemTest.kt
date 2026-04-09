package org.example

import org.example.trigonometry.*
import org.example.logarithm.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.*

class EquationSystemTest {

    private lateinit var sinMock: Sine
    private lateinit var cotMock: Cotangent
    private lateinit var secMock: Secant
    private lateinit var lnMock: NaturalLogarithm
    private lateinit var log3Mock: BaseNLogarithm
    private lateinit var log5Mock: BaseNLogarithm
    private lateinit var log10Mock: BaseNLogarithm
    private lateinit var system: EquationSystem

    @BeforeEach
    fun setUp() {
        sinMock = mock()
        cotMock = mock()
        secMock = mock()
        lnMock = mock()
        log3Mock = mock()
        log5Mock = mock()
        log10Mock = mock()

        system = EquationSystem(
            sin = sinMock, cot = cotMock, sec = secMock,
            ln = lnMock, log3 = log3Mock, log5 = log5Mock, log10 = log10Mock
        )
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.0, -0.5, -2.0])
    fun `Тригонометрическая ветвь корректно всё считает`(x: Double) {
        whenever(sinMock.calculate(x)).thenReturn(2.0)
        whenever(cotMock.calculate(x)).thenReturn(3.0)
        whenever(secMock.calculate(x)).thenReturn(4.0)

        val result = system(x)

        val inner = 2.0 * (3.0 * 3.0) * 4.0
        val expected = (inner * inner) * (inner * inner)

        assertEquals(expected, result)
        verify(sinMock).calculate(x)
        verify(cotMock).calculate(x)
        verify(secMock).calculate(x)
        verifyNoInteractions(lnMock, log3Mock, log5Mock, log10Mock)
    }

    @ParameterizedTest
    @ValueSource(doubles = [2.0, 3.0, 5.0])
    fun `Логарифмическая ветвь тоже всё считает`(x: Double) {
        whenever(lnMock.calculate(x)).thenReturn(2.0)
        whenever(log3Mock.calculate(x)).thenReturn(1.0)
        whenever(log5Mock.calculate(x)).thenReturn(3.0)
        whenever(log10Mock.calculate(x)).thenReturn(2.0)

        val result = system(x)

        val log10_x3 = 2.0 * 2.0 * 2.0
        val firstPart = log10_x3 / 1.0
        val secondPart = (3.0 / 2.0) - 3.0
        val numerator = (firstPart + secondPart) * (firstPart + secondPart)
        val denominator = (2.0 * 2.0 * 2.0) / 2.0
        val expected = numerator / denominator

        assertEquals(expected, result)
        verify(lnMock).calculate(x)
        verify(log3Mock).calculate(x)
        verify(log5Mock).calculate(x)
        verify(log10Mock).calculate(x)
        verifyNoInteractions(sinMock, cotMock, secMock)
    }

    @Test
    fun `Эксепшн при логарифме3 0`() {
        whenever(log3Mock.calculate(2.0)).thenReturn(0.0)
        whenever(log10Mock.calculate(2.0)).thenReturn(1.0)
        whenever(lnMock.calculate(2.0)).thenReturn(1.0)

        assertThrows(ArithmeticException::class.java) { system(2.0) }
    }

    @Test
    fun `Эксепшн при логарифме10 0`() {
        whenever(log3Mock.calculate(2.0)).thenReturn(1.0)
        whenever(log10Mock.calculate(2.0)).thenReturn(0.0)
        whenever(lnMock.calculate(2.0)).thenReturn(1.0)

        assertThrows(ArithmeticException::class.java) { system(2.0) }
    }

    @Test
    fun `Эксепшн при логарифмеН 0`() {
        whenever(log3Mock.calculate(2.0)).thenReturn(1.0)
        whenever(log10Mock.calculate(2.0)).thenReturn(1.0)
        whenever(lnMock.calculate(2.0)).thenReturn(0.0)

        assertThrows(ArithmeticException::class.java) { system(2.0) }
    }

}
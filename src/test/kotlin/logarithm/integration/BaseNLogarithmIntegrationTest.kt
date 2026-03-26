package org.example.logarithm

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class BaseNLogarithmIntegrationTest {

    @Mock
    lateinit var mockLn: NaturalLogarithm

    @Spy
    val spyLn: NaturalLogarithm = NaturalLogarithm()

    companion object {
        private const val DELTA = 1e-6
    }

    @Test
    fun `Логарифм по основанию N вызывает вычисление натурального логарифма`() {
        val logarithm = BaseNLogarithm(5.0, spyLn)
        logarithm.calculate(3322.0)
        verify(spyLn, atLeastOnce()).calculate(any())
    }

    @Test
    fun `Проверка вычисления с моками`() {
        val arg = 3322.0
        whenever(mockLn.calculate(arg)).thenReturn(8.1083222)
        whenever(mockLn.calculate(5.0)).thenReturn(1.6094379)

        val log5 = BaseNLogarithm(5.0, mockLn)
        val expected = 8.1083222 / 1.6094379
        assertEquals(expected, log5.calculate(arg), DELTA)
    }
}

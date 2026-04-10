package org.example.trigonometry

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class CosineIntegrationTest {

    @Mock
    lateinit var mockSine: Sine

    @Spy
    val spySine: Sine = Sine()

    companion object {
        private const val DELTA = 1e-6

    }

    @Test
    fun `Косинус вызывает синус`() {
        val cos = Cosine(spySine)
        cos.calculate(1.0)
        verify(spySine, atLeastOnce()).calculate(any())
    }
}
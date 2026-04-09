package equationSystem.integration;

import org.example.EquationSystem
import org.example.trigonometry.*
import org.example.logarithm.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sin
import kotlin.math.ln
import kotlin.math.log10

class EquationSystemIntegrationTest {

    private val system = EquationSystem(
            sin = Sine(),
            cot = Cotangent(),
            sec = Secant(),
            ln = NaturalLogarithm(),
            log3 = BaseNLogarithm(3.0),
            log5 = BaseNLogarithm(5.0),
            log10 = BaseNLogarithm(10.0)
    )

    @Test
    fun `бесконечная величина для тригонометрической ветки`() {
        val result = system(-PI / 4)

        assertTrue(result.isFinite())
        assertFalse(result.isNaN())
    }

    @Test
    fun `значение больше 0 в тригонометрической ветке`() {
        val x = -0.5

        val result = system(x)

        assertTrue(result.isFinite())

        assertTrue(result >= 0)
    }

    @Test
    fun `логарифмическая ветка считает х`() {
        val result = system(2.0)

        assertTrue(result.isFinite())
        assertFalse(result.isNaN())
    }

    @Test
    fun `логарифмическая ветка делает разные выводы для разных входных`() {
        val r1 = system(2.0)
        val r2 = system(3.0)

        assertNotEquals(r1, r2)
    }

    @Test
    fun `не крашится на крайних значениях`() {
        val values = listOf(-PI / 2 + 0.1, -PI / 3, -1.0)

        for (x in values) {
            val result = system(x)
            assertTrue(result.isFinite())
        }
    }

    @Test
    fun `не крашится на стандартных значениях`() {
        val values = listOf(0.1, 1.5, 10.0)

        for (x in values) {
            val result = system(x)
            assertTrue(result.isFinite())
        }
    }
}
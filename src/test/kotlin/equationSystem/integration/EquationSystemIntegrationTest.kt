package equationSystem.integration;

import org.example.EquationSystem
import org.example.trigonometry.*
import org.example.logarithm.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.math.PI

@ExtendWith(MockitoExtension::class)
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

    @Mock
    lateinit var sinMock: Sine

    @Mock
    lateinit var cotMock: Cotangent

    @Mock
    lateinit var secMock: Secant
    @Mock
    lateinit var lnMock: NaturalLogarithm

    @Mock
    lateinit var log3Mock: BaseNLogarithm

    @Mock
    lateinit var log5Mock: BaseNLogarithm

    @Mock
    lateinit var log10Mock: BaseNLogarithm

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

    @Test
    fun `тригонометрия в equationsystem`() {
        val system = EquationSystem(
            sin = Sine(),
            cot = Cotangent(),
            sec = Secant(),
            ln = lnMock,
            log3 = log3Mock,
            log5 = log5Mock,
            log10 = log10Mock
        )

        val result = system(-PI / 4)

        assertTrue(result.isFinite())
    }

    @Test
    fun `логарифмическая интеграция`() {
        val system = EquationSystem(
            sin = sinMock,
            cot = cotMock,
            sec = secMock,
            ln = NaturalLogarithm(),
            log3 = BaseNLogarithm(3.0),
            log5 = BaseNLogarithm(5.0),
            log10 = BaseNLogarithm(10.0)
        )

        val result = system(2.0)

        assertTrue(result.isFinite())
    }

    @Test
    fun `ln в equationsystem`() {
        val system = EquationSystem(
            sin = sinMock,
            cot = cotMock,
            sec = secMock,
            ln = NaturalLogarithm(),
            log3 = log3Mock,
            log5 = log5Mock,
            log10 = log10Mock
        )

        val result = system(-PI / 4)

        assertTrue(result.isFinite())
    }

    @Test
    fun `secnant в equationsystem`() {
        val system = EquationSystem(
            sin = sinMock,
            cot = cotMock,
            sec = Secant(),
            ln = lnMock,
            log3 = log3Mock,
            log5 = log5Mock,
            log10 = log10Mock
        )

        val result = system(-PI / 4)

        assertTrue(result.isFinite())
    }

    @Test
    fun `cotantgent в equationsystem`() {
        val system = EquationSystem(
            sin = sinMock,
            cot = Cotangent(),
            sec = secMock,
            ln = lnMock,
            log3 = log3Mock,
            log5 = log5Mock,
            log10 = log10Mock
        )

        val result = system(-PI / 4)

        assertTrue(result.isFinite())
    }

    @Test
    fun `cotantgent and log3 в equationsystem`() {
        val system = EquationSystem(
            sin = sinMock,
            cot = Cotangent(),
            sec = secMock,
            ln = NaturalLogarithm(),
            log3 = BaseNLogarithm(3.0),
            log5 = BaseNLogarithm(5.0),
            log10 = BaseNLogarithm(10.0),
        )

        val result = system(2.0)

        assertTrue(result.isFinite())
    }

    @Test
    fun `cotantgent and ln and log10 в equationsystem`() {
        val system = EquationSystem(
            sin = sinMock,
            cot = Cotangent(),
            sec = secMock,
            ln = NaturalLogarithm(),
            log3 = BaseNLogarithm(3.0),
            log5 = log5Mock,
            log10 = BaseNLogarithm(10.0)
        )

        val result = system(2.0)

        assertTrue(result.isFinite())
    }

}
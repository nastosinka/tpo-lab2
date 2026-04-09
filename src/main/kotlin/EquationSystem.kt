package org.example

import org.example.trigonometry.*
import org.example.logarithm.*

class EquationSystem (
    private val sin: Sine = Sine(),
    private val cot: Cotangent = Cotangent(),
    private val sec: Secant = Secant(),

    private val ln: NaturalLogarithm = NaturalLogarithm(),
    private val log3: BaseNLogarithm = BaseNLogarithm(3.0),
    private val log5: BaseNLogarithm = BaseNLogarithm(5.0),
    private val log10: BaseNLogarithm = BaseNLogarithm(10.0)
) : (Double) -> Double {

    override fun invoke(x: Double): Double {
        if (x <= 0.0) {
            return calculateTrig(x)
        } else {
            return calculateLog(x)
        }
    }

    private fun calculateTrig(x: Double): Double {
        val sinVal = sin.calculate(x)
        val cotVal = cot.calculate(x)
        val secVal = sec.calculate(x)

        val inner = sinVal * cotVal * cotVal * secVal
        val squared = inner * inner
        return squared * squared
    }

    private fun calculateLog(x: Double): Double {
        val log3_x = log3.calculate(x)
        val log5_x = log5.calculate(x)
        val log10_x = log10.calculate(x)
        val log10_x3 = log10_x * log10_x * log10_x
        val ln_x = ln.calculate(x)

        if (log3_x == 0.0 || log10_x == 0.0 || ln_x == 0.0) {
            throw ArithmeticException("Логарифм равен нулю, вычисление невозможно для x=$x")
        }

        val firstPart = log10_x3 / log3_x
        val secondPart = (log5_x / log10_x) - log5_x

        val numerator = (firstPart + secondPart) * (firstPart + secondPart)
        val denominator = (ln_x * ln_x * ln_x) / log10_x

        return numerator / denominator
    }
}
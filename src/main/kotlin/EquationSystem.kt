package org.example

import org.example.trigonometry.*
import org.example.logarithm.*

class EquationSystem (
    private val sin: Sine = Sine(),
    private val cos: Cosine = Cosine(),
    private val cot: Cotangent = Cotangent(),
    private val sec: Secant = Secant(),

    private val ln: NaturalLogarithm = NaturalLogarithm(),
    private val log3: BaseNLogarithm = BaseNLogarithm(3.0),
    private val log5: BaseNLogarithm = BaseNLogarithm(5.0),
    private val log10: BaseNLogarithm = BaseNLogarithm(10.0)
) : Function {

    override fun calculate(x: Double): Double {
        if (x <= 0.0) {
            return calculateTrig(x)
        } else {
            return calculateLog(x)
        }
    }

    private fun calculateTrig(x: Double): Double {
        val sinVal = sin.calculate(x)
        val cosVal = cot.calculate(x)
        val secVal = sec.calculate(x * x)

        val inner = sinVal * cotVal * cotVal * secVal
        return inner * inner
    }

    private fun calculateLog(x: Double): Double {
        val log10_x3 = log10.calculate(x * x * x)
        val log3_x = log3.calculate(x)
        val log5_x = log5.calculate(x)
        val log10_x = log10.calculate(x)
        val ln_x = ln.calculate(x)

        val firstPart = log10_x3 / log3_x
        val secondPart = (log5_x / log10_x) - log5_x

        val numerator = (firstPart + secondPart) * (firstPart + secondPart)
        val denominator = (ln_x * ln_x * ln_x) / log10_x

        return numerator / denominator
    }
}
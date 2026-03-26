package org.example.trigonometry

import kotlin.math.abs

open class Cotangent(private val sine: Sine = Sine(), private val cosine: Cosine = Cosine()) {

    companion object {
        const val SINGULARITY_THRESHOLD = 1e-10
    }

    open fun calculate(x: Double): Double {
        require(x.isFinite()) { "x должен быть конечным, на вход передан $x" }

        val sinVal = sine.calculate(x)
        if (abs(sinVal) < SINGULARITY_THRESHOLD) {
            throw ArithmeticException("cot(x) неопределен в точке x = $x (sin(x) = 0)")
        }

        return cosine.calculate(x) / sinVal
    }
}

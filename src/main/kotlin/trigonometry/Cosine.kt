package org.example.trigonometry

import kotlin.math.PI

open class Cosine(private val sine: Sine = Sine(), val precision: Double = DEFAULT_PRECISION) {

    companion object {
        const val DEFAULT_PRECISION = 1e-15
    }

    open fun calculate(x: Double): Double {
        require(x.isFinite()) { "x должен быть конечным, на вход передан $x" }

        return sine.calculate(PI / 2 - x);

    }
}

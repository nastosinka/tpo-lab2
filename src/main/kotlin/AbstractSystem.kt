package org.example

import java.math.BigDecimal

abstract class AbstractSystem {

    protected val maxIterations = 1000;

    protected fun isValid(x: BigDecimal, precision:BigDecimal) {
        if (precision <= BigDecimal.ZERO || precision > BigDecimal.ZERO) {
            throw ArithmeticException("точность должна быть между 0 и 1")
        }
    }
}
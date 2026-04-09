package org.example

import org.example.trigonometry.*
import org.example.logarithm.*
import org.example.util.CSVGraphWriter
import java.io.File

object Main {

    private val outputDir = File(
        System.getProperty("user.dir"),
        "plots"
    ).absolutePath + File.separator

    private const val START = -10.0
    private const val END = 10.0
    private const val STEP = 0.01

    @JvmStatic
    fun main(args: Array<String>) {

        val functions = listOf(
            Pair("Sine") { x: Double -> Sine().calculate(x) },
            Pair("Cosine") { x: Double -> Cosine().calculate(x) },
            Pair("Secant") { x: Double -> Secant().calculate(x) },
            Pair("Cotangent") { x: Double -> Cotangent().calculate(x) },

            Pair("NaturalLogarithm") { x: Double -> NaturalLogarithm().calculate(x) },
            Pair("Log2") { x: Double -> BaseNLogarithm(2.0).calculate(x) },
            Pair("Log3") { x: Double -> BaseNLogarithm(3.0).calculate(x) },
            Pair("Log10") { x: Double -> BaseNLogarithm(10.0).calculate(x) },

            Pair("EquationSystem") { x: Double -> EquationSystem()(x) }
        )

        functions.forEach { (name, func) ->
            CSVGraphWriter(func, outputDir, name).write(START, END, STEP)
        }
    }
}
package org.example.util

import org.example.Function
import java.io.File
import java.io.PrintWriter

class CSVGraphWriter(
    private val function: Function,
    private val outputDir: String
) {

    fun write(start: Double, end: Double, step: Double) {
        require(step > 0) { "шаг должен быть положительным" }

        val fileName = function.javaClass.simpleName + ".csv"
        val file = File(outputDir + fileName)

        file.parentFile.mkdirs()

        PrintWriter(file).use { writer ->
            writer.println("x, f(x)")

            var x = start
            while (x <= end) {
                val result = try {
                    function.calculate(x)
                } catch (e: Exception) {
                    Double.NaN
                }

                writer.println("$x, $result")

                x += step
            }
        }
    }
}
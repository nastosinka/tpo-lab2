package org.example.util

import java.io.File
import java.io.PrintWriter

class CSVGraphWriter(
    private val function: (Double) -> Double,
    private val outputDir: String,
    private val name: String
) {

    fun write(start: Double, end: Double, step: Double) {
        require(step > 0) { "шаг должен быть положительным" }

        val file = File(outputDir, "$name.csv")
        file.parentFile?.mkdirs()

        PrintWriter(file).use { writer ->
            writer.println("x,f(x)")

            var x = start
            while (x <= end) {
                val result = try {
                    function(x)
                } catch (e: Exception) {
                    Double.NaN
                }

                writer.println("$x, $result")
                x += step
            }
        }
    }
}
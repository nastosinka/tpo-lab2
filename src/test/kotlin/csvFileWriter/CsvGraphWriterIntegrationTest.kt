package csvFileWriter

import org.example.util.CSVGraphWriter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import kotlin.math.sin

class CsvGraphWriterIntegrationTest {

    @Test
    fun `может записывать репальнй csv`() {
        val tempDir = Files.createTempDirectory("csv_integration_test").toFile()

        val function: (Double) -> Double = { x -> sin(x) }

        val writer = CSVGraphWriter(
            function = function,
            outputDir = tempDir.absolutePath + File.separator,
            name = "sin_test"
        )

        writer.write(0.0, Math.PI, Math.PI / 2)

        val file = File(tempDir, "sin_test.csv")

        assertTrue(file.exists(), "CSV файл должен быть создан")

        val lines = file.readLines()

        assertEquals("x,f(x)", lines[0])

        assertTrue(lines.size >= 3)

        val secondLine = lines[1].split(",")
        val x = secondLine[0].toDouble()
        val y = secondLine[1].toDouble()

        assertEquals(0.0, x, 1e-9)
        assertEquals(sin(0.0), y, 1e-9)
    }

    @Test
    fun `записывает NaN`() {
        val tempDir = Files.createTempDirectory("csv_integration_test2").toFile()

        val function: (Double) -> Double = { x ->
            if (x == 1.0) throw RuntimeException("boom")
            x
        }

        val writer = CSVGraphWriter(
            function = function,
            outputDir = tempDir.absolutePath + File.separator,
            name = "exception_test"
        )

        writer.write(0.0, 2.0, 1.0)

        val file = File(tempDir, "exception_test.csv")
        val lines = file.readLines()

        assertTrue(lines.any { it.contains("NaN") })
    }
}
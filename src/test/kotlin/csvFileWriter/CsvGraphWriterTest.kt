package org.example.util

import org.junit.jupiter.api.*
import org.mockito.kotlin.*
import java.io.File
import java.nio.file.Files
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CSVGraphWriterTest {

    private lateinit var tempDir: File
    private lateinit var csvWriter: CSVGraphWriter
    private val mockFunction: (Double) -> Double = mock()

    @BeforeAll
    fun setupAll() {
        tempDir = createTempDirectory("csv_test").toFile()
    }

    @AfterAll
    fun cleanupAll() {
        tempDir.deleteRecursively()
    }

    @BeforeEach
    fun setup() {
        reset(mockFunction)
    }

    @Test
    fun `создаёт файл`() {
        csvWriter = CSVGraphWriter(mockFunction, tempDir.path, "test")
        csvWriter.write(0.0, 0.0, 1.0)

        val expectedFile = File(tempDir, "test.csv")
        assertTrue(expectedFile.exists(), "файл должен быть создан")
    }

    @Test
    fun `записывает в файл`() {
        whenever(mockFunction.invoke(any())).thenReturn(1.0)

        csvWriter = CSVGraphWriter(mockFunction, tempDir.path, "test")
        csvWriter.write(0.0, 1.0, 1.0)

        val file = File(tempDir, "test.csv")
        val lines = file.readLines()

        assertEquals("x,f(x)", lines[0])
        assertEquals("0.0, 1.0", lines[1])
    }

    @Test
    fun `обрабатывает ArithmeticException`() {
        whenever(mockFunction.invoke(any())).thenThrow(ArithmeticException("разрыв"))

        csvWriter = CSVGraphWriter(mockFunction, tempDir.path, "test")
        csvWriter.write(0.0, 1.0, 1.0)

        val file = File(tempDir, "test.csv")
        val lines = file.readLines()

        assertEquals("x,f(x)", lines[0])
        assertEquals("0.0, NaN", lines[1])
    }

    @Test
    fun `корректная обработка исключений`() {
        val tempDir = Files.createTempDirectory("csv_test").toFile()

        val func: (Double) -> Double = {
            if (it == 2.0) throw RuntimeException("boom")
            it
        }

        val writer = CSVGraphWriter(func, tempDir.absolutePath, "test")

        writer.write(1.0, 3.0, 1.0)

        val file = File(tempDir, "test.csv")
        val lines = file.readLines()

        assertTrue(lines.any { it.contains("NaN") })
    }

    @Test
    fun `файл создаётся даже в пограничных случаях`() {
        val tempDir = Files.createTempDirectory("csv_test").toFile()

        val func: (Double) -> Double = { it }

        val writer = CSVGraphWriter(func, tempDir.absolutePath, "test")

        writer.write(5.0, 1.0, 1.0)

        val file = File(tempDir, "test.csv")

        assertTrue(file.exists())
    }

}
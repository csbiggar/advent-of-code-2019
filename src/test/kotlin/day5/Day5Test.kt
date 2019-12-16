package day5

import FileReader
import intcode.Program
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day5Test {

    @Test
    fun `test real part 1`(){
        assertThat(Program(FileReader.readFile("day5/program.txt")).run(1)).isEqualTo(13285749)
    }

    @Test
    fun `test real part 2`() {
        assertThat(Program(FileReader.readFile("day5/program.txt")).run(5)).isEqualTo(5000972)
    }
}
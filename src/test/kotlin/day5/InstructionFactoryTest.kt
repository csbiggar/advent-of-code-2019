package day5

import day5.ParameterMode.IMMEDIATE
import day5.ParameterMode.POSITION
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class InstructionFactoryTest {

    @Test
    fun `should create an add instruction`() {
        val instruction = InstructionFactory.from(listOf(1001, 4, 3, 4), 0)

        if (instruction !is Add) fail("Should be Add")

        assertThat(instruction.first).isEqualTo(Parameter(POSITION, 4))
        assertThat(instruction.second).isEqualTo(Parameter(IMMEDIATE, 3))
        assertThat(instruction.result).isEqualTo(Parameter(POSITION, 4))
    }

    @ParameterizedTest(name = "{0} is {1} {2} {3}")
    @CsvSource(
        "1,POSITION,POSITION,POSITION",
        "101,IMMEDIATE,POSITION,POSITION",
        "1101,IMMEDIATE,IMMEDIATE,POSITION",
        "11101,IMMEDIATE,IMMEDIATE,IMMEDIATE",
        "10001,POSITION,POSITION,IMMEDIATE"
    )
    fun `should set parameter modes for an add instruction`(
        operation: Int,
        expectedFirst: ParameterMode,
        expectedSecond: ParameterMode,
        expectedResult: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is Add) fail("Should be Add")
        assertThat(instruction.first.mode).isEqualTo(expectedFirst)
        assertThat(instruction.second.mode).isEqualTo(expectedSecond)
        assertThat(instruction.result.mode).isEqualTo(expectedResult)
    }

    @Test
    fun `should create a multiply instruction`() {
        val instruction = InstructionFactory.from(listOf(1002, 4, 3, 4), 0)

        if (instruction !is Multiply) fail("Should be Multiply")

        assertThat(instruction.first).`as`("first").isEqualTo(Parameter(POSITION, 4))
        assertThat(instruction.second).`as`("second").isEqualTo(Parameter(IMMEDIATE, 3))
        assertThat(instruction.result).`as`("result").isEqualTo(Parameter(POSITION, 4))
    }

    @ParameterizedTest(name = "{0} is {1} {2} {3}")
    @CsvSource(
        "2,POSITION,POSITION,POSITION",
        "102,IMMEDIATE,POSITION,POSITION",
        "1102,IMMEDIATE,IMMEDIATE,POSITION",
        "11102,IMMEDIATE,IMMEDIATE,IMMEDIATE",
        "10002,POSITION,POSITION,IMMEDIATE"
    )
    fun `should set parameter modes for multiply instruction`(
        operation: Int,
        expectedFirst: ParameterMode,
        expectedSecond: ParameterMode,
        expectedResult: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is Multiply) fail("Should be Multiply")
        assertThat(instruction.first.mode).isEqualTo(expectedFirst)
        assertThat(instruction.second.mode).isEqualTo(expectedSecond)
        assertThat(instruction.result.mode).isEqualTo(expectedResult)
    }

    @Test
    fun `should create a save instruction`() {
        val instruction = InstructionFactory.from(listOf(3, 4), 0)

        if (instruction !is Save) fail("Should be Save")

        assertThat(instruction.result).`as`("result").isEqualTo(Parameter(POSITION, 4))
    }

    @ParameterizedTest(name = "{0} is {1} ")
    @CsvSource(
        "3,POSITION",
        "103,IMMEDIATE"
    )
    fun `should set parameter modes for save instruction`(
        operation: Int,
        expectedResult: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is Save) fail("Should be Save")
        assertThat(instruction.result.mode).isEqualTo(expectedResult)
    }

    @Test
    fun `should create an output instruction`() {
        val instruction = InstructionFactory.from(listOf(4, 4), 0)

        if (instruction !is Output) fail("Should be Output")

        assertThat(instruction.result).`as`("result").isEqualTo(Parameter(POSITION, 4))
    }

    @ParameterizedTest(name = "{0} is {1} ")
    @CsvSource(
        "4,POSITION",
        "104,IMMEDIATE"
    )
    fun `should set parameter modes for output instruction`(
        operation: Int,
        expectedResult: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is Output) fail("Should be Output")
        assertThat(instruction.result.mode).isEqualTo(expectedResult)
    }

    @Test
    fun `should create a terminate instruction`() {
        val instruction = InstructionFactory.from(listOf(99), 0)

        if (instruction !is Terminate) fail("Should be Terminate")
    }
}
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

    @Test
    fun `should create a jump-if-true instruction`() {
        val instruction = InstructionFactory.from(listOf(5,9,10), 0)

        if (instruction !is JumpIfTrue) fail("Should be JumpIfTrue")
        assertThat(instruction.first).`as`("first").isEqualTo(Parameter(POSITION, 9))
        assertThat(instruction.second).`as`("second").isEqualTo(Parameter(POSITION, 10))
    }

    @ParameterizedTest(name = "{0} is {1} {2}")
    @CsvSource(
        "5,POSITION,POSITION",
        "105,IMMEDIATE,POSITION",
        "1105,IMMEDIATE,IMMEDIATE"
    )
    fun `should set parameter modes for jumpIfTrue instruction`(
        operation: Int,
        expectedFirst: ParameterMode,
        expectedSecond: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is JumpIfTrue) fail("Should be JumpIfTrue")
        assertThat(instruction.first.mode).isEqualTo(expectedFirst)
        assertThat(instruction.second.mode).isEqualTo(expectedSecond)
    }

    @Test
    fun `should create a jump-if-false instruction`() {
        val instruction = InstructionFactory.from(listOf(6,9,10), 0)

        if (instruction !is JumpIfFalse) fail("Should be JumpIfFalse")
        assertThat(instruction.first).`as`("first").isEqualTo(Parameter(POSITION, 9))
        assertThat(instruction.second).`as`("second").isEqualTo(Parameter(POSITION, 10))
    }

    @ParameterizedTest(name = "{0} is {1} {2}")
    @CsvSource(
        "6,POSITION,POSITION",
        "106,IMMEDIATE,POSITION",
        "1106,IMMEDIATE,IMMEDIATE"
    )
    fun `should set parameter modes for JumpIfFalse instruction`(
        operation: Int,
        expectedFirst: ParameterMode,
        expectedSecond: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is JumpIfFalse) fail("Should be JumpIfFalse")
        assertThat(instruction.first.mode).isEqualTo(expectedFirst)
        assertThat(instruction.second.mode).isEqualTo(expectedSecond)
    }

    @Test
    fun `should create a less than instruction`() {
        val instruction = InstructionFactory.from(listOf(1007, 4, 3, 4), 0)

        if (instruction !is LessThan) fail("Should be LessThan")

        assertThat(instruction.first).`as`("first").isEqualTo(Parameter(POSITION, 4))
        assertThat(instruction.second).`as`("second").isEqualTo(Parameter(IMMEDIATE, 3))
        assertThat(instruction.result).`as`("result").isEqualTo(Parameter(POSITION, 4))
    }

    @ParameterizedTest(name = "{0} is {1} {2} {3}")
    @CsvSource(
        "7,POSITION,POSITION,POSITION",
        "107,IMMEDIATE,POSITION,POSITION",
        "1107,IMMEDIATE,IMMEDIATE,POSITION",
        "11107,IMMEDIATE,IMMEDIATE,IMMEDIATE",
        "10007,POSITION,POSITION,IMMEDIATE"
    )
    fun `should set parameter modes for less than instruction`(
        operation: Int,
        expectedFirst: ParameterMode,
        expectedSecond: ParameterMode,
        expectedResult: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is LessThan) fail("Should be LessThan")
        assertThat(instruction.first.mode).isEqualTo(expectedFirst)
        assertThat(instruction.second.mode).isEqualTo(expectedSecond)
        assertThat(instruction.result.mode).isEqualTo(expectedResult)
    }

    @Test
    fun `should create an Equals instruction`() {
        val instruction = InstructionFactory.from(listOf(1008, 4, 3, 4), 0)

        if (instruction !is Equals) fail("Should be Equals")

        assertThat(instruction.first).`as`("first").isEqualTo(Parameter(POSITION, 4))
        assertThat(instruction.second).`as`("second").isEqualTo(Parameter(IMMEDIATE, 3))
        assertThat(instruction.result).`as`("result").isEqualTo(Parameter(POSITION, 4))
    }

    @ParameterizedTest(name = "{0} is {1} {2} {3}")
    @CsvSource(
        "8,POSITION,POSITION,POSITION",
        "108,IMMEDIATE,POSITION,POSITION",
        "1108,IMMEDIATE,IMMEDIATE,POSITION",
        "11108,IMMEDIATE,IMMEDIATE,IMMEDIATE",
        "10008,POSITION,POSITION,IMMEDIATE"
    )
    fun `should set parameter modes for Equals instruction`(
        operation: Int,
        expectedFirst: ParameterMode,
        expectedSecond: ParameterMode,
        expectedResult: ParameterMode
    ) {
        val instruction = InstructionFactory.from(listOf(operation, 4, 3, 4), 0)

        if (instruction !is Equals) fail("Should be Equals")
        assertThat(instruction.first.mode).isEqualTo(expectedFirst)
        assertThat(instruction.second.mode).isEqualTo(expectedSecond)
        assertThat(instruction.result.mode).isEqualTo(expectedResult)
    }
}
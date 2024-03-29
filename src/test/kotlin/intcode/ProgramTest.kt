package intcode

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class ProgramTest {
    @Test
    fun `option 1 (addition) simple example`() {
        val program = Program("1,0,0,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("2,0,0,0,99")
    }

    @Test
    fun `option 2 (multiplication) simple example`() {
        val program = Program("2,3,0,3,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("2,3,0,6,99")
    }

    @Test
    fun `option 2 (multiplication) slightly more complex example`() {
        val program = Program("2,4,4,5,99,0")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("2,4,4,5,99,9801")
    }

    @Test
    fun `example where termination is mutated into a command before it is reached`() {
        val program = Program("1,1,1,4,99,5,6,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("30,1,1,4,2,5,6,0,99")
    }

    @Test
    fun `mixed options example`() {
        val program = Program("1,9,10,3,2,3,11,0,99,30,40,50")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("3500,9,10,70,2,3,11,0,99,30,40,50")
    }

    @Test
    fun `option 3 (save) takes an input and saves it at given index`() {
        val program = Program("3,1,99")
        program.run(50)
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("3,50,99")

        val program2 = Program("3,0,99")
        program2.run(51)
        Assertions.assertThat(program2.showMeTheInstructions()).isEqualTo("51,0,99")
    }

    @Test
    fun `option 4 (retrieve) returns a value at given index`() {
        Assertions.assertThat(Program("4,0,99").run()).isEqualTo(4)
        Assertions.assertThat(Program("4,2,99").run()).isEqualTo(99)
    }

    @Test
    fun `option 3 and 4 combined`() {
        Assertions.assertThat(Program("3,0,4,0,99").run(23)).isEqualTo(23)
    }

    @Test
    fun `multiply with mix of positional and immediate parameter modes`() {
        val program = Program("1002,4,3,4,33")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("1002,4,3,4,99")
    }

    @Test
    fun `add with mix of positional and immediate parameter modes`() {
        val program = Program("1001,4,3,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("102,4,3,0,99")
    }

    @Test
    fun `immediate mode for an output operation`() {
        Assertions.assertThat(Program("104,58,99").run()).isEqualTo(58)
    }

    @Test
    fun `should handle negatives`() {
        val program = Program("1101,100,-1,4,0")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("1101,100,-1,4,99")
    }

    @Test
    fun `less than, immediate`() {
        val program = Program("1107,23,42,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("1,23,42,0,99")
    }

    @Test
    fun `less than, positional`() {
        val program = Program("7,3,2,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("1,3,2,0,99")
    }

    @Test
    fun `not less than, immediate`() {
        val program = Program("1107,53,42,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("0,53,42,0,99")
    }

    @Test
    fun `not less than, positional`() {
        val program = Program("7,0,2,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("0,0,2,0,99")
    }


    @Test
    fun `equals, immediate`() {
        val program = Program("1108,23,23,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("1,23,23,0,99")
    }

    @Test
    fun `equals, positional`() {
        val program = Program("8,1,1,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("1,1,1,0,99")
    }

    @Test
    fun `not equals, immediate`() {
        val program = Program("1108,53,42,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("0,53,42,0,99")
    }

    @Test
    fun `not equals, positional`() {
        val program = Program("8,0,2,0,99")
        program.run()
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("0,0,2,0,99")
    }

    @Test
    fun `should tell if input is equal to 8, position mode`() {
        Assertions.assertThat(Program("3,9,8,9,10,9,4,9,99,-1,8").run(8)).`as`("return 1 when input is 8").isEqualTo(1)
        Assertions.assertThat(Program("3,9,8,9,10,9,4,9,99,-1,8").run(9)).`as`("return 0 when input is not 8")
            .isEqualTo(0)
    }

    @Test
    fun `should tell if input is less than 8, position mode`() {
        Assertions.assertThat(Program("3,9,7,9,10,9,4,9,99,-1,8").run(7)).`as`("return 1 when input is less than 8")
            .isEqualTo(1)
        Assertions.assertThat(Program("3,9,7,9,10,9,4,9,99,-1,8").run(8)).`as`("return 0 when input is 8").isEqualTo(0)
        Assertions.assertThat(Program("3,9,7,9,10,9,4,9,99,-1,8").run(9)).`as`("return 0 when input is greater than  8")
            .isEqualTo(0)
    }

    @Test
    fun `should tell if input is equal to 8, immediate mode`() {
        Assertions.assertThat(Program("3,3,1108,-1,8,3,4,3,99").run(8)).`as`("return 1 when input is 8").isEqualTo(1)
        Assertions.assertThat(Program("3,3,1108,-1,8,3,4,3,99").run(9)).`as`("return 0 when input is not 8")
            .isEqualTo(0)
    }

    @Test
    fun `should tell if input is less than 8, immediate mode`() {
        Assertions.assertThat(Program("3,3,1107,-1,8,3,4,3,99").run(7)).`as`("return 1 when input is less than 8")
            .isEqualTo(1)
        Assertions.assertThat(Program("3,3,1107,-1,8,3,4,3,99").run(8)).`as`("return 0 when input is 8").isEqualTo(0)
        Assertions.assertThat(Program("3,3,1107,-1,8,3,4,3,99").run(9)).`as`("return 0 when input is greater than  8")
            .isEqualTo(0)
    }

    @Test
    fun `should tell if input is zero, position mode`() {
        val input = "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9"
        Assertions.assertThat(Program(input).run(0)).`as`("return 0 when input is 0").isEqualTo(0)
        Assertions.assertThat(Program(input).run(9)).`as`("return 1 when input is not 0").isEqualTo(1)
    }

    @Test
    fun `should tell if input is zero, immediate mode`() {
        val initialInstructions = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1"
        Assertions.assertThat(Program(initialInstructions).run(0)).`as`("return 0 when input is 0").isEqualTo(0)
        Assertions.assertThat(Program(initialInstructions).run(9)).`as`("return 1 when input is not 0").isEqualTo(1)
    }

    @Test
    fun `larger example`() {
        val initialInstructions =
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"
        Assertions.assertThat(Program(initialInstructions).run(7)).`as`("less than 8").isEqualTo(999)
        Assertions.assertThat(Program(initialInstructions).run(8)).`as`("is 8").isEqualTo(1000)
        Assertions.assertThat(Program(initialInstructions).run(9)).`as`("greater than  8").isEqualTo(1001)
    }

    @Test
    fun `should handle multiple input parameters`() {
        val program = Program("3,0,3,1,99")
        program.run(42, 79)
        Assertions.assertThat(program.showMeTheInstructions()).isEqualTo("42,79,3,1,99")
    }

    @Test
    @Disabled
    fun `should complain if not enough inputs`() {
        //TODO Nice error handling for this:
        val program = Program("3,0,3,1,99")
        program.run(42)
    }

}
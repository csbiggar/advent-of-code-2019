package day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day5Test {

    @Test
    fun `option 1 (addition) simple example`() {
        val program = Program("1,0,0,0,99")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("2,0,0,0,99")
    }

    @Test
    fun `option 2 (multiplication) simple example`() {
        val program = Program("2,3,0,3,99")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("2,3,0,6,99")
    }

    @Test
    fun `option 2 (multiplication) slightly more complex example`() {
        val program = Program("2,4,4,5,99,0")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("2,4,4,5,99,9801")
    }

    @Test
    fun `example where termination is mutated into a command before it is reached`() {
        val program = Program("1,1,1,4,99,5,6,0,99")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("30,1,1,4,2,5,6,0,99")
    }

    @Test
    fun `mixed options example`() {
        val program = Program("1,9,10,3,2,3,11,0,99,30,40,50")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("3500,9,10,70,2,3,11,0,99,30,40,50")
    }

    @Test
    fun `option 3 (save) takes an input and saves it at given index`() {
        val program = Program("3,1,99")
        program.run(50)
        assertThat(program.showMeTheInstructions()).isEqualTo("3,50,99")

        val program2 = Program("3,0,99")
        program2.run(51)
        assertThat(program2.showMeTheInstructions()).isEqualTo("51,0,99")
    }

    @Test
    fun `option 4 (retrieve) returns a value at given index`() {
        assertThat(Program("4,0,99").run()).isEqualTo(4)
        assertThat(Program("4,2,99").run()).isEqualTo(99)
    }

    @Test
    fun `option 3 and 4 combined`() {
        assertThat(Program("3,0,4,0,99").run(23)).isEqualTo(23)
    }

    @Test
    fun `multiply with mix of positional and immediate parameter modes`() {
        val program = Program("1002,4,3,4,33")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("1002,4,3,4,99")
    }

    @Test
    fun `add with mix of positional and immediate parameter modes`() {
        val program = Program("1001,4,3,0,99")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("102,4,3,0,99")
    }

    @Test
    fun `immediate mode for an output operation`() {
        assertThat(Program("104,58,99").run()).isEqualTo(58)
    }

    @Test
    fun `should handle negatives`() {
        val program = Program("1101,100,-1,4,0")
        program.run()
        assertThat(program.showMeTheInstructions()).isEqualTo("1101,100,-1,4,99")
    }

//    @Test
//    fun `should tell if input is equal to 8`(){
//        val program = Program("3,9,8,9,10,9,4,9,99,-1,8")
//        assertThat(program.run(8)).`as`("return true when input is 8").isEqualTo(0)
//        assertThat(program.run(9)).`as`("return false when input is not 8").isEqualTo(1)
//    }

}
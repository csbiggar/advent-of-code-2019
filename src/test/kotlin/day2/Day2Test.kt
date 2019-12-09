package day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day2Test {

    @Test
    fun `option 1 (addition) simple example`(){
        assertThat(Program.run("1,0,0,0,99")).isEqualTo("2,0,0,0,99")
    }

    @Test
    fun `option 2 (multiplication) simple example`(){
        assertThat(Program.run("2,3,0,3,99")).isEqualTo("2,3,0,6,99")
    }

    @Test
    fun `option 2 (multiplication) slightly more complex example`(){
        assertThat(Program.run("2,4,4,5,99,0")).isEqualTo("2,4,4,5,99,9801")
    }

    @Test
    fun `example where termination is mutated into a command before it is reached`(){
        assertThat(Program.run("1,1,1,4,99,5,6,0,99")).isEqualTo("30,1,1,4,2,5,6,0,99")
    }

    @Test
    fun `mixed options example`(){
        assertThat(Program.run("1,9,10,3,2,3,11,0,99,30,40,50")).isEqualTo("3500,9,10,70,2,3,11,0,99,30,40,50")
    }
}
package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day4Test {

    @Test
    fun `numbers without doubles do not meet the criteria`() {
        val result = meetsCriteria(number = 1234)
        assertThat(result).isFalse()
    }

    @Test
    fun `numbers with doubles meet the criteria`() {
        val result = meetsCriteria(number = 1224)
        assertThat(result).isTrue()
    }

    @Test
    fun `descending numbers do not meet the criteria`() {
        val result = meetsCriteria(number = 4451)
        assertThat(result).isFalse()
    }

    @Test
    fun `repeating groups of three or more do not count as doubles`() {
        val result = meetsCriteria(number = 123444)
        assertThat(result).isFalse()
    }

    @Test
    fun `It's ok to have a triple+ group, as long as there is also an exact double`() {
        val result = meetsCriteria(number = 111122)
        assertThat(result).`as`("this should really work").isTrue()
    }

}
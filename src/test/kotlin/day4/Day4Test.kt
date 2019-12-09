package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day4Test {

    @Test
    fun `numbers without doubles do not meet the criteria`() {
        val result = meetsCriteria(number = 1234, min = 1111, max = 9999)
        assertThat(result).isFalse()
    }

    @Test
    fun `numbers with doubles meet the criteria`() {
        val result = meetsCriteria(number = 1224, min = 1111, max = 9999)
        assertThat(result).isTrue()
    }

    @Test
    fun `descending numbers do not meet the criteria`() {
        val result = meetsCriteria(number = 4451, min = 1111, max = 9999)
        assertThat(result).isFalse()
    }

    @Test
    fun `numbers less than min  do not meet the criteria`() {
        val result = meetsCriteria(number = 223, min = 1111, max = 9999)
        assertThat(result).isFalse()
    }

    @Test
    fun `numbers greater than max  do not meet the criteria`() {
        val result = meetsCriteria(number = 88999, min = 1111, max = 9999)
        assertThat(result).isFalse()
    }

    @Test
    fun someTest() {
        val result1 = meetsCriteria(number = 112233, min = 1, max = 9999999)
        assertThat(result1).isTrue()

        val result2 = meetsCriteria(number = 123444, min = 1, max = 99999999)
        assertThat(result2).isFalse()

        val result3 = meetsCriteria(number = 111122, min = 1, max = 99999999)
        assertThat(result3).`as`("this should really work").isTrue()
    }

}
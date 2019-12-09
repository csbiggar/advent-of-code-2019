package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day4Test {

    @Test
    fun `numbers without doubles do not meet the criteria`() {
        val result = meetsCriteria(number = "1234", min = "1111", max = "9999")
        assertThat(result).isFalse()
    }

    @Test
    fun `numbers with doubles do not meet the criteria`() {
        val result = meetsCriteria(number = "1224", min = "1111", max = "9999")
        assertThat(result).isTrue()
    }

    @Test
    fun `descending numbers do not meet the criteria`() {
        val result = meetsCriteria(number = "4451", min = "1111", max = "9999")
        assertThat(result).isFalse()
    }

    @Test
    fun `numbers less than min  do not meet the criteria`() {
        val result = meetsCriteria(number = "2222", min = "2223", max = "9999")
        assertThat(result).isFalse()
    }

    @Test
    fun `numbers greater than max  do not meet the criteria`() {
        val result = meetsCriteria(number = "2223", min = "1111", max = "2222")
        assertThat(result).isFalse()
    }

}
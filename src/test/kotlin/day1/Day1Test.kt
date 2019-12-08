package day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day1Test {

    @ParameterizedTest(name = "a module of mass {0} needs {1} fuels")
    @CsvSource(
        "12,2",
        "14,2",
        "1969,654",
        "100756,33583"
    )
    fun `should calculate fuel`(mass: Int, requiredFuel: Int) {
        assertThat(calculateFuel(mass)).isEqualTo(requiredFuel)
    }

    @Test
    fun `should find total fuel for multiple modules`() {
        val moduleMasses1 = listOf(12, 14)
        assertThat(totalFuelRequired(moduleMasses1)).isEqualTo(4)

        val moduleMasses2 = listOf(12, 14, 1969)
        assertThat(totalFuelRequired(moduleMasses2)).isEqualTo(658)
    }



}
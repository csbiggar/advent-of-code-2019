package day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class Day6Test {

    @Test
    fun `total orbits, 2 configs`() {
        assertThat(countOrbits("COM)B,B)C")).isEqualTo(3)
    }

    @Test
    fun `total orbits, 3 configs`() {
        assertThat(countOrbits("COM)B,B)C,C)D")).isEqualTo(6)
    }

    @Test
    fun `total orbits, 4 configs`() {
        assertThat(countOrbits("COM)B,B)C,C)D,D)E")).isEqualTo(10)
    }

    @Test
    fun `total orbits, 1 config`() {
        assertThat(countOrbits("COM)B")).isEqualTo(1)
    }

    @Test
    fun `longer example`() {
        assertThat(countOrbits("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L")).isEqualTo(42)
    }

}

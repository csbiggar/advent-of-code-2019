package day6

import org.assertj.core.api.Assertions.assertThat
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
    fun `total orbits longer example`() {
        assertThat(countOrbits("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L")).isEqualTo(42)
    }

    @Test
    fun `no hops to santa`() {
        assertThat(transfersRequired("I)YOU,I)SAN")).isEqualTo(0)
    }

    @Test
    fun `one hop to santa`() {
        assertThat(transfersRequired("I)K,K)YOU,I)SAN")).isEqualTo(1)
    }

    @Test
    fun `two hops to santa`() {
        assertThat(transfersRequired("I)J,J)K,K)YOU,I)SAN")).isEqualTo(2)
    }

    @Test
    fun `two hops to santa the other way`() {
        assertThat(transfersRequired("J)I,K)J,K)YOU,I)SAN")).isEqualTo(2)
    }

    @Test
    fun `one hop to santa the other way`() {
        assertThat(transfersRequired("K)I,K)YOU,I)SAN")).isEqualTo(1)
    }

    @Test
    fun `you to santa`() {
        assertThat(transfersRequired("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L,K)YOU,I)SAN")).isEqualTo(4)
    }

}





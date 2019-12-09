package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day3Test {

    @Test
    fun `example 1`() {
        val wire1 = "R8,U5,L5,D3"
        val wire2 = "U7,R6,D4,L4"

        assertThat(closestIntersectionDistance(wire1, wire2)).isEqualTo(6)
        assertThat(fewestStepsToIntersection(wire1, wire2)).`as`("fewest steps").isEqualTo(30)
    }

    @Test
    fun `example 2`() {
        val wire1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
        val wire2 = "U62,R66,U55,R34,D71,R55,D58,R83"

        assertThat(closestIntersectionDistance(wire1, wire2)).isEqualTo(159)
        assertThat(fewestStepsToIntersection(wire1, wire2)).`as`("fewest steps").isEqualTo(610)
    }

    @Test
    fun `example 3`() {
        val wire1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
        val wire2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"

        assertThat(closestIntersectionDistance(wire1, wire2)).isEqualTo(135)
        assertThat(fewestStepsToIntersection(wire1, wire2)).`as`("fewest steps").isEqualTo(410)
    }

    @Test
    fun `should convert wire route into coordinates`() {
        assertThat(wireToCoordinates("R2")).isEqualTo(
            mapOf(
                1 to Coordinate(1, 0),
                2 to Coordinate(2, 0)
            )
        )

        assertThat(wireToCoordinates("U2")).isEqualTo(
            mapOf(
                1 to Coordinate(0, 1),
                2 to Coordinate(0, 2)
            )
        )

        assertThat(wireToCoordinates("D2")).isEqualTo(
            mapOf(
                1 to Coordinate(0, -1),
                2 to Coordinate(0, -2)
            )
        )

        assertThat(wireToCoordinates("L2")).isEqualTo(
            mapOf(
                1 to Coordinate(-1, 0),
                2 to Coordinate(-2, 0)
            )
        )

        assertThat(wireToCoordinates("R2,U1,L4")).isEqualTo(
            mapOf(
                1 to Coordinate(1, 0),
                2 to Coordinate(2, 0),
                3 to Coordinate(2, 1),
                4 to Coordinate(1, 1),
                5 to Coordinate(0, 1),
                6 to Coordinate(-1, 1),
                7 to Coordinate(-2, 1)
            )
        )
    }

}
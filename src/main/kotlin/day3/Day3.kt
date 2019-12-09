package day3

import FileReader
import kotlin.math.absoluteValue

fun main() {
    val (wire1, wire2) = FileReader.readLines("day3/wire-coordinates.txt")

    val result = closestIntersectionDistance(wire1, wire2)

    println(result)
}


fun closestIntersectionDistance(wire1: String, wire2: String): Int {
    val wire1Coordinates = wireToCoordinates(wire1)
    val wire2Coordinates = wireToCoordinates(wire2)

    val minDistanceCoordinate = wire1Coordinates
        .intersect(wire2Coordinates)
        .minBy { it.distance() }
        ?: throw IllegalStateException("No intersection found")

    return minDistanceCoordinate.distance()
}

fun wireToCoordinates(wire: String): List<Coordinate> {
    var currentCoordinate = Coordinate(0, 0)
    val wireCoordinates = mutableListOf<Coordinate>()

    wire.split(",")
        .map { s ->
            Instruction(Direction.valueOf(s.take(1)), s.drop(1).toInt())
        }.forEach { instruction ->
            when (instruction.direction) {
                Direction.R -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates.add(currentCoordinate.moveX(moveBy))
                    }
                }
                Direction.U -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates.add(currentCoordinate.moveY(moveBy))
                    }
                }
                Direction.D -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates.add(currentCoordinate.moveY(moveBy.negate()))
                    }
                }
                Direction.L -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates.add(currentCoordinate.moveX(moveBy.negate()))
                    }
                }
            }
            currentCoordinate = wireCoordinates.last()
        }

    return wireCoordinates
}

data class Coordinate(val x: Int, val y: Int) {
    fun moveX(number: Int) = this.copy(x = x + number)
    fun moveY(number: Int) = this.copy(y = y + number)
    fun distance() = x.absoluteValue + y.absoluteValue
}

data class Instruction(val direction: Direction, val numberOfMoves: Int)

enum class Direction {
    L, R, U, D
}

private fun Int.negate() = this * -1
package day3

import FileReader
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

fun main() {
    val (wire1, wire2) = FileReader.readLines("day3/wire-coordinates.txt")

    val closestDistanceTime = measureTimeMillis {
        val closestDistance = closestIntersectionDistance(wire1, wire2)
        println("Closest distance: $closestDistance (expect 1674)")
    }
    println("    ...completed in $closestDistanceTime ms")

    val fewestStepsTime = measureTimeMillis {
        val fewestSteps = fewestStepsToIntersection(wire1, wire2)
        println("Fewest steps: $fewestSteps (expect 14012)")
    }
    println("    ...completed in $fewestStepsTime ms")
}


fun closestIntersectionDistance(wire1: String, wire2: String): Int {
    val wire1Coordinates = wireToCoordinates(wire1)
    val wire2Coordinates = wireToCoordinates(wire2)

    val minDistanceCoordinate = wire1Coordinates
        .filter { wire1Coord ->
            wire2Coordinates.any { wire1Coord.samePositionAs(it) }
        }
        .minBy { it.distance() }
        ?: throw IllegalStateException("No intersection found")

    return minDistanceCoordinate.distance()
}

fun fewestStepsToIntersection(wire1: String, wire2: String): Int {
    val wire1Coordinates = wireToCoordinates(wire1)
    val wire2Coordinates = wireToCoordinates(wire2)

    return wire1Coordinates
        .flatMap { wire1Coord ->
            wire2Coordinates
                .withSamePositionAs(wire1Coord)
                .map { wire2Coord -> wire2Coord.numberOfSteps + wire1Coord.numberOfSteps }
        }
        .min()
        ?: throw IllegalStateException("No intersection found")
}

fun wireToCoordinates(wire: String): List<Coordinate> {
    var currentCoordinate = Coordinate(0, 0, 0)
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

data class Coordinate(val x: Int, val y: Int, val numberOfSteps: Int) {
    fun moveX(number: Int) = this.copy(x = x + number, numberOfSteps = numberOfSteps + number.absoluteValue)
    fun moveY(number: Int) = this.copy(y = y + number, numberOfSteps = numberOfSteps + number.absoluteValue)
    fun distance() = x.absoluteValue + y.absoluteValue
    fun samePositionAs(otherCoordinate: Coordinate) = x == otherCoordinate.x && y == otherCoordinate.y
}

data class Instruction(val direction: Direction, val numberOfMoves: Int)

enum class Direction {
    L, R, U, D
}

private fun Int.negate() = this * -1

fun List<Coordinate>.withSamePositionAs(coordinate: Coordinate) = this.filter { it.samePositionAs(coordinate) }
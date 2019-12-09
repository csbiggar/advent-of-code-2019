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
    val intersectingCoordinates = findIntersectingCoordinates(wire1, wire2)

    return intersectingCoordinates
        .minBy { it.distance() }
        ?.distance()
        ?: throw IllegalStateException("No intersection found")
}

fun fewestStepsToIntersection(wire1: String, wire2: String): Int {
    val intersectingCoordinates = findIntersectingCoordinates(wire1, wire2)

    val wire1Intersections = wireToCoordinates(wire1).filterValues { it in intersectingCoordinates }
    val wire2Intersections = wireToCoordinates(wire2).filterValues { it in intersectingCoordinates }

    return wire1Intersections
        .flatMap { (wire1steps, wire1coord) ->
            wire2Intersections
                .filterValues { it == wire1coord }
                .map { (wire2steps, _) -> wire1steps + wire2steps  }
        }
        .min()
        ?: throw IllegalStateException("No intersection found")
}

private fun findIntersectingCoordinates(wire1: String, wire2: String): Set<Coordinate> {
    val wire1Coordinates = wireToCoordinates(wire1).values.toList()
    val wire2Coordinates = wireToCoordinates(wire2).values.toList()
    return wire1Coordinates.intersect(wire2Coordinates)
}

typealias NumberOfSteps = Int

fun wireToCoordinates(wire: String): Map<NumberOfSteps, Coordinate> {
    var currentCoordinate = Coordinate(0, 0)
    var numberOfSteps = 0
    val wireCoordinates = mutableMapOf<NumberOfSteps, Coordinate>()

    wire.split(",")
        .map { s ->
            Instruction(Direction.valueOf(s.take(1)), s.drop(1).toInt())
        }.forEach { instruction ->
            when (instruction.direction) {
                Direction.R -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates[++numberOfSteps] = currentCoordinate.moveX(moveBy)
                    }
                }
                Direction.U -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates[++numberOfSteps] = currentCoordinate.moveY(moveBy)
                    }
                }
                Direction.D -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates[++numberOfSteps] = currentCoordinate.moveY(moveBy.negate())
                    }
                }
                Direction.L -> {
                    (1..instruction.numberOfMoves).forEach { moveBy ->
                        wireCoordinates[++numberOfSteps] = currentCoordinate.moveX(moveBy.negate())
                    }
                }
            }
            currentCoordinate = wireCoordinates[numberOfSteps]!!
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
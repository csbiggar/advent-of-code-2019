package day6

import FileReader
import day6.SpaceObject.Companion.SANTA
import day6.SpaceObject.Companion.YOU

fun main() {
    val orbitsSpec = FileReader.readLines("day6/orbits.txt").joinToString(",")
    println(countOrbits(orbitsSpec))
    println(transfersRequired(orbitsSpec))
}


fun countOrbits(orbitsSpec: String): Int {
    val allOrbits = mapOrbits(orbitsSpec)

    val distinctSpaceObjects = (allOrbits.map { it.orbitedBy } + allOrbits.map { it.orbitedObject }).distinct()

    return descendents(distinctSpaceObjects, allOrbits).count() - 1
}

private fun mapOrbits(orbitsSpec: String): List<Orbit> {
    return orbitsSpec
        .split(",")
        .map {
            Orbit(
                orbitedBy = SpaceObject(it.substringAfter(")")),
                orbitedObject = SpaceObject(it.substringBefore(")"))
            )
        }
}

private fun findDirectOrbits(spaceObject: SpaceObject, allOrbits: List<Orbit>): List<SpaceObject> {
    return allOrbits
        .filter { it.orbitedBy == spaceObject }
        .map { it.orbitedObject }
}

private fun findDirectParents(spaceObject: SpaceObject, allOrbits: List<Orbit>): List<SpaceObject> {
    return allOrbits
        .filter { it.orbitedObject == spaceObject }
        .map { it.orbitedBy }
}

private fun descendents(spaceObjects: List<SpaceObject>, allOrbits: List<Orbit>): List<SpaceObject> {
    val result = spaceObjects.flatMap { findDirectOrbits(it, allOrbits) }
    return if (result.isEmpty()) spaceObjects
    else result + descendents(result, allOrbits)

}

data class Orbit(val orbitedBy: SpaceObject, val orbitedObject: SpaceObject)
data class SpaceObject(val name: String) {
    companion object {
        val SANTA = SpaceObject("SAN")
        val YOU = SpaceObject("YOU")
    }
}

fun transfersRequired(orbitsSpec: String): Int {
    val allOrbits = mapOrbits(orbitsSpec)

    val santaIsOrbiting = allOrbits.first { it.orbitedBy == SANTA }.orbitedObject

    return findTransfers(listOf(YOU), santaIsOrbiting, allOrbits)
}

private fun findTransfers(
    spaceObjects: List<SpaceObject>,
    santaIsOrbiting: SpaceObject,
    allOrbits: List<Orbit>,
    transferCount: Int = 0
): Int {
    val orbits = spaceObjects.flatMap { findDirectOrbits(it, allOrbits) }
    val orbitedBy = spaceObjects.flatMap { findDirectParents(it, allOrbits) }

    return if (santaIsOrbiting in orbits || santaIsOrbiting in orbitedBy) {
        transferCount
    } else {
        findTransfers(orbits + orbitedBy, santaIsOrbiting, allOrbits, transferCount + 1)
    }
}
package day6

import FileReader
import day6.SpaceObject.Companion.SANTA
import day6.SpaceObject.Companion.YOU

fun main() {
    val orbitsSpec = FileReader.readLines("day6/orbits.txt").joinToString(",")
    println(MyUniverse(orbitsSpec).countOrbits())
    println(MyUniverse(orbitsSpec).countHopsFromYouToSanta())
}

// So I don't have to refactor the tests ...
fun countOrbits(orbitsSpec: String): Int {
    return MyUniverse(orbitsSpec).countOrbits()
}

fun transfersRequired(orbitsSpec: String): Int {
    return MyUniverse(orbitsSpec).countHopsFromYouToSanta()
}

data class Orbit(val orbitedBy: SpaceObject, val orbitedObject: SpaceObject)
data class SpaceObject(val name: String) {
    companion object {
        val SANTA = SpaceObject("SAN")
        val YOU = SpaceObject("YOU")
    }
}

class MyUniverse(orbitsSpec: String) {

    private val allOrbits = mapOrbits(orbitsSpec)

    fun countHopsFromYouToSanta(): Int {
        val santasDescendents = descendents(listOf(SANTA), allOrbits)
        val yourDescendents = descendents(listOf(YOU), allOrbits)

        val result = santasDescendents.intersect(yourDescendents).first()

        return santasDescendents.indexOf(result) + yourDescendents.indexOf(result)
    }

    fun countOrbits(): Int {
        val distinctSpaceObjects = (allOrbits.map { it.orbitedBy } + allOrbits.map { it.orbitedObject }).distinct()

        return descendents(distinctSpaceObjects, allOrbits).count() - 1
    }

    private fun directOrbit(spaceObject: SpaceObject): SpaceObject? {
        return allOrbits
            .firstOrNull { it.orbitedBy == spaceObject }
            ?.orbitedObject
    }

    private fun descendents(spaceObjects: List<SpaceObject>, allOrbits: List<Orbit>): List<SpaceObject> {
        val result = spaceObjects.mapNotNull { directOrbit(it) }
        return if (result.isEmpty()) spaceObjects
        else result + descendents(result, allOrbits)
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
}


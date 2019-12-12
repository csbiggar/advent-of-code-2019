package day6

fun main() {
    val orbitsSpec = FileReader.readLines("day6/orbits.txt").joinToString(",")
    println(countOrbits(orbitsSpec))
}


fun countOrbits(orbitsSpec: String): Int {
    val allOrbits = orbitsSpec
        .split(",")
        .map {
            Orbit(
                orbiter = SpaceObject(it.substringAfter(")")),
                orbits = SpaceObject(it.substringBefore(")"))
            )
        }

    val distinctSpaceObjects = (allOrbits.map { it.orbiter } + allOrbits.map { it.orbits }).distinct()

    return descendents(distinctSpaceObjects, allOrbits).count() - 1
}


private fun findDirectOrbits(spaceObject: SpaceObject, allOrbits: List<Orbit>): List<SpaceObject> {
    return allOrbits
        .filter { it.orbiter == spaceObject }
        .map { it.orbits }
}

private fun descendents(spaceObjects: List<SpaceObject>, allOrbits: List<Orbit>): List<SpaceObject> {
    val result = spaceObjects.flatMap { findDirectOrbits(it, allOrbits) }
    return if (result.isEmpty()) spaceObjects
    else result + descendents(result, allOrbits)

}

data class Orbit(val orbiter: SpaceObject, val orbits: SpaceObject)
data class SpaceObject(val name: String)
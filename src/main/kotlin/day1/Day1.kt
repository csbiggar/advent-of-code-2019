package day1


tailrec fun calculateFuel(mass: Int, fuelRunningTotal: Int = 0): Int {
    val extraFuelMass = mass / 3 - 2

    return if (extraFuelMass <= 0) fuelRunningTotal
    else calculateFuel(extraFuelMass, fuelRunningTotal + extraFuelMass)
}

fun totalFuelRequired(masses: List<Int>) = masses.map { calculateFuel(it) }.sum()

fun main() {
    val moduleMasses = FileReader.readFileAsIntegers("module-masses.csv")
    println(totalFuelRequired(moduleMasses))
}

object FileReader {
    fun readFileAsIntegers(fileName: String) = this::class.java.getResourceAsStream(fileName)
        .bufferedReader()
        .readLines()
        .map { it.toInt() }
}
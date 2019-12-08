package day1


fun calculateFuel(mass: Int): Int = mass / 3 - 2

fun totalFuelRequired(masses: List<Int>) = masses.map { calculateFuel(it) }.sum()

fun main() {
    val moduleMasses = FileReader.asIntegers("module-masses.csv")

    println(totalFuelRequired(moduleMasses))
}

object FileReader {
    fun asIntegers(fileName: String) = this::class.java.getResourceAsStream(fileName)
        .bufferedReader()
        .readLines()
        .map { it.toInt() }

}
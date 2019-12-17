package day7

import FileReader
import com.marcinmoskala.math.permutations
import intcode.Program

fun main() {
    val programInput = FileReader.readFile("day7/amplifier-software.csv")
    val result = calculateMaxThrusterSignal(programInput)
    println("Max possible thruster signal: $result")
}

fun calculateThrusterSignal(programInput: String, phaseSequence: List<Int>): Int {
    var nextInput = 0

    for (phase in phaseSequence) {
        nextInput = Program(programInput).run(phase, nextInput)
            ?: throw IllegalStateException("What do you mean, there's no output?")
    }

    return nextInput
}

fun calculateMaxThrusterSignal(programInput: String): Int {
    val results = mutableListOf<Int>()

    for (phaseSequence in listOf(0, 1, 2, 3, 4).permutations()) {
        val thrusterSignal = calculateThrusterSignal(programInput, phaseSequence)
        results.add(thrusterSignal)
    }

    return results.max() ?: throw java.lang.IllegalStateException("There should be some results here...")
}

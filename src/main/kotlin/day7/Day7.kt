package day7

import FileReader
import com.marcinmoskala.math.permutations
import intcode.Program

fun main() {
    val programInput = FileReader.readFile("day7/amplifier-software.csv")
    val result = calculateMaxThrusterSignal(programInput, listOf(0, 1, 2, 3, 4))
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

fun calculateMaxThrusterSignal(programInput: String, phaseSettings: List<Int>): Int {
    return phaseSettings.permutations()
        .map { calculateThrusterSignal(programInput, it) }
        .max()
        ?: throw java.lang.IllegalStateException("There should be some results here...")
}

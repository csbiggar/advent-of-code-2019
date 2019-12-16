package day7

import intcode.Program


fun calculateThrustSignal(programInput: String, phaseSequence: List<Int>): Int {
    var nextInput = 0

    for (phase in phaseSequence) {
        nextInput = Program(programInput).run(phase, nextInput)
            ?: throw IllegalStateException("What do you mean, there's no output?")
    }

    return nextInput
}
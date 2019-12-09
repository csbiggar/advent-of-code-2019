package day2

import FileReader
import day2.Program.runWithNounAndVerbOverrides


fun main() {
    val input = FileReader.readFile("day2/gravity-assist-program.txt")

    val (noun, verb) = findNounAndVerbGiving(input, "19690720")

    println("noun $noun verb $verb. Answer: ${100 * noun + verb} ")
}

private fun findNounAndVerbGiving(input: String, requiredOutput: String): Pair<Int, Int> {
    (0..99).forEach { noun ->
        (0..99).forEach { verb ->
            val result = runWithNounAndVerbOverrides(noun, verb, input)
            if (result.substringBefore(",") == requiredOutput) return Pair(noun, verb)
        }
    }
    throw IllegalStateException("No combination produced the result")
}


object Program {

    fun runWithNounAndVerbOverrides(noun: Int, verb: Int, programInput: String): String {
        val program = programInput.split(",").map { it.toInt() }.toMutableList()
        program[1] = noun
        program[2] = verb
        return run(program.joinToString(","))
    }

    fun run(programInput: String): String {
        val output = programInput.split(",").map { it.toInt() }.toMutableList()
        var index = 0

        loop@ while (index + 4 < output.size) {

            val instruction = Instruction(
                operation = Operation.from(output[index]),
                firstIndex = output[index + 1],
                secondIndex = output[index + 2],
                resultIndex = output[index + 3]
            )

            when (instruction.operation) {
                Operation.ADD -> {
                    output[instruction.resultIndex] = output[instruction.firstIndex] + output[instruction.secondIndex]
                }
                Operation.MULTIPLY -> {
                    output[instruction.resultIndex] = output[instruction.firstIndex] * output[instruction.secondIndex]
                }
                Operation.TERMINATE -> break@loop;
            }

            index += 4
        }

        return output.joinToString(",")
    }
}


data class Instruction(val operation: Operation, val firstIndex: Int, val secondIndex: Int, val resultIndex: Int)

enum class Operation {
    ADD, MULTIPLY, TERMINATE;

    companion object {
        fun from(operation: Int): Operation {
            return when (operation) {
                1 -> ADD
                2 -> MULTIPLY
                99 -> TERMINATE
                else -> throw IllegalArgumentException("Whoops, operation $operation not recognised - something's gone wrong")
            }
        }
    }
}

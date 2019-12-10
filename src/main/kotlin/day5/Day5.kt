package day5

import FileReader

fun main() {
    val program = FileReader.readFile("day5/program.txt")
    println("done... ")
}

class Program(private val instructions: String) {

    fun run(input: Int? = null): String {
        val output = instructions.split(",").map { it.toInt() }.toMutableList()
        var index = 0

        loop@ while (index < output.size) {
            val instruction = InstructionFactory.from(output, index)

            when (instruction) {
                is Add -> {
                    output[instruction.resultIndex] = output[instruction.firstIndex] + output[instruction.secondIndex]
                }
                is Multiply -> {
                    output[instruction.resultIndex] = output[instruction.firstIndex] * output[instruction.secondIndex]
                }
                is Save -> {
                    output[instruction.resultIndex] =
                        input ?: throw IllegalArgumentException("Save instruction should come with an input")
                }
                is Terminate -> break@loop
            }

            index += instruction.numberOfValues
        }

        return output.joinToString(",")
    }
}

sealed class Instruction {
    abstract val numberOfValues: Int
}

class Add(val firstIndex: Int, val secondIndex: Int, val resultIndex: Int) : Instruction() {
    override val numberOfValues: Int = 4
}

class Multiply(val firstIndex: Int, val secondIndex: Int, val resultIndex: Int) : Instruction() {
    override val numberOfValues: Int = 4
}

class Save(val resultIndex: Int) : Instruction() {
    override val numberOfValues: Int = 2
}

object Terminate : Instruction() {
    override val numberOfValues: Int = 1
}

object InstructionFactory {

    fun from(instructions: List<Int>, index: Int): Instruction {
        return when (instructions[index]) {
            1 -> Add(
                firstIndex = instructions[index + 1],
                secondIndex = instructions[index + 2],
                resultIndex = instructions[index + 3]
            )
            2 -> Multiply(
                firstIndex = instructions[index + 1],
                secondIndex = instructions[index + 2],
                resultIndex = instructions[index + 3]
            )
            3 -> Save(resultIndex = instructions[index + 1])
            99 -> Terminate
            else -> throw IllegalArgumentException("Whoops, operation ${instructions[index]} not recognised - something's gone wrong")
        }
    }

}

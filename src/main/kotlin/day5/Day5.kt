package day5

import FileReader
import day5.ParameterMode.POSITION

fun main() {
    val program = FileReader.readFile("day5/program.txt")
    println("done... ")
}

class Program(initialInstructions: String) {

    private var instructions = initialInstructions.split(",").map { it.toInt() }.toMutableList()
    private var output: Int? = null

    fun showMeTheInstructions() = instructions.joinToString(",")

    fun run(input: Int? = null): Int? {
        var index = 0

        loop@ while (index < instructions.size) {
            val current = InstructionFactory.from(instructions, index)

            when (current) {
                is Add -> {
                    instructions[current.result.value] =
                        instructions[current.first.value] + instructions[current.second.value]
                }
                is Multiply -> {
                    instructions[current.result.value] =
                        instructions[current.first.value] * instructions[current.second.value]
                }
                is Save -> {
                    instructions[current.result.value] =
                        input ?: throw IllegalArgumentException("Save instruction should come with an input")
                }
                is Output -> {
                    output = instructions[current.result.value]
                }
                is Terminate -> break@loop
            }

            index += current.moveIndexOnBy
        }

        return output
    }
}

sealed class Instruction {
    abstract val moveIndexOnBy: Int
}

class Add(val first: Parameter, val second: Parameter, val result: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 4
}

class Multiply(val first: Parameter, val second: Parameter, val result: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 4
}

class Save(val result: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 2
}

class Output(val result: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 2
}

object Terminate : Instruction() {
    override val moveIndexOnBy: Int = 1
}

object InstructionFactory {

    fun from(instructions: List<Int>, index: Int): Instruction {
        return when (instructions[index]) {
            1 -> Add(
                first = Parameter(POSITION, instructions[index + 1]),
                second = Parameter(POSITION, instructions[index + 2]),
                result = Parameter(POSITION, instructions[index + 3])
            )
            2 -> Multiply(
                first = Parameter(POSITION, instructions[index + 1]),
                second = Parameter(POSITION, instructions[index + 2]),
                result = Parameter(POSITION, instructions[index + 3])
            )
            3 -> Save(result = Parameter(POSITION, instructions[index + 1]))
            4 -> Output(result = Parameter(POSITION, instructions[index + 1]))
            99 -> Terminate
            else -> throw IllegalArgumentException("Whoops, operation ${instructions[index]} not recognised - something's gone wrong")
        }
    }

}

data class Parameter(val mode: ParameterMode, val value: Int)

enum class ParameterMode {
    POSITION, IMMEDIATE
}

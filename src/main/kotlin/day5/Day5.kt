package day5

import FileReader

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
                    instructions[current.resultIndex] = instructions[current.firstIndex] + instructions[current.secondIndex]
                }
                is Multiply -> {
                    instructions[current.resultIndex] = instructions[current.firstIndex] * instructions[current.secondIndex]
                }
                is Save -> {
                    instructions[current.resultIndex] =
                        input ?: throw IllegalArgumentException("Save instruction should come with an input")
                }
                is Output -> {
                    output = instructions[current.resultIndex]
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

class Add(val firstIndex: Int, val secondIndex: Int, val resultIndex: Int) : Instruction() {
    override val moveIndexOnBy: Int = 4
}

class Multiply(val firstIndex: Int, val secondIndex: Int, val resultIndex: Int) : Instruction() {
    override val moveIndexOnBy: Int = 4
}

class Save(val resultIndex: Int) : Instruction() {
    override val moveIndexOnBy: Int = 2
}

class Output(val resultIndex: Int) : Instruction() {
    override val moveIndexOnBy: Int = 2
}

object Terminate : Instruction() {
    override val moveIndexOnBy: Int = 1
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
            4 -> Output(resultIndex = instructions[index + 1])
            99 -> Terminate
            else -> throw IllegalArgumentException("Whoops, operation ${instructions[index]} not recognised - something's gone wrong")
        }
    }

}

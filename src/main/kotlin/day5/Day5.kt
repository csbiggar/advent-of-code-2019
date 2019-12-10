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

        // Position: 0 1 2 34
        // Eg code : 0 1 0 02
        val code = instructions[index].toString().padStart(5, '0')
        val operator = listOf(
            code.takeLast(2).toInt(),
            code[2].toString().toInt(),
            code[1].toString().toInt(),
            code[0].toString().toInt()
        )

        return when (operator.first()) {
            1 -> Add(
                first = Parameter(ParameterMode.fromId(operator[1]), instructions[index + 1]),
                second = Parameter(ParameterMode.fromId(operator[2]), instructions[index + 2]),
                result = Parameter(ParameterMode.fromId(operator[3]), instructions[index + 3])
            )
            2 -> Multiply(
                first = Parameter(ParameterMode.fromId(operator[1]), instructions[index + 1]),
                second = Parameter(ParameterMode.fromId(operator[2]), instructions[index + 2]),
                result = Parameter(ParameterMode.fromId(operator[3]), instructions[index + 3])
            )
            3 -> Save(result = Parameter(ParameterMode.fromId(operator[1]), instructions[index + 1]))
            4 -> Output(result = Parameter(ParameterMode.fromId(operator[1]), instructions[index + 1]))
            99 -> Terminate
            else -> throw IllegalArgumentException("Whoops, operation ${instructions[index]} not recognised - something's gone wrong")
        }
    }

}

data class Parameter(val mode: ParameterMode, val value: Int)

enum class ParameterMode(private val id: Int) {
    POSITION(0), IMMEDIATE(1);

    companion object {
        fun fromId(id: Int) = ParameterMode.values().first { it.id == id }
    }
}
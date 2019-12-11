package day5

import FileReader
import day5.ParameterMode.IMMEDIATE
import day5.ParameterMode.POSITION

fun main() {
    val program = Program(FileReader.readFile("day5/program.txt"))
    val result = program.run(1)
    println("Result: $result (should be 13285749)")
}

class Program(initialInstructions: String) {

    private var instructions = initialInstructions.split(",").map { it.toInt() }.toMutableList()
    private var output: Int? = null

    fun showMeTheInstructions() = instructions.joinToString(",")

    fun run(input: Int? = null): Int? {
        var index = 0

        loop@ while (index < instructions.size) {
            val current = InstructionFactory.from(instructions, index)
            var moveIndexOnBy = current.moveIndexOnBy

            when (current) {
                is Add -> applyAdd(current)
                is Multiply -> applyMultiply(current)
                is Save -> save(current, input)
                is Output -> output = findOutput(current)
                is JumpIfTrue -> moveIndexOnBy = findNewPointerPosition(current)
                is JumpIfFalse -> moveIndexOnBy = findNewPointerPosition(current)
                is LessThan -> lessThan(current)
                is Equals -> areTheyEqual(current)
                is Terminate -> break@loop
            }

            index += moveIndexOnBy
        }

        return output
    }

    private fun findNewPointerPosition(jump: JumpIfTrue): Int {
        if (jump.first.value == 0) return jump.moveIndexOnBy
        return when (jump.second.mode) {
            POSITION -> instructions[jump.second.value]
            IMMEDIATE -> jump.second.value
        }
    }

    private fun findNewPointerPosition(jump: JumpIfFalse): Int {
        if (jump.first.value != 0) return jump.moveIndexOnBy
        return when (jump.second.mode) {
            POSITION -> instructions[jump.second.value]
            IMMEDIATE -> jump.second.value
        }
    }

    private fun save(instruction: Save, input: Int?) {
        instructions[instruction.result.value] =
            input ?: throw IllegalArgumentException("Save instruction should come with an input")
    }

    private fun findOutput(output: Output): Int {
        return when (output.result.mode) {
            POSITION -> instructions[output.result.value]
            IMMEDIATE -> output.result.value
        }
    }

    private fun applyMultiply(multiply: Multiply) {
        val x = when (multiply.first.mode) {
            POSITION -> instructions[multiply.first.value]
            IMMEDIATE -> multiply.first.value
        }

        val y = when (multiply.second.mode) {
            POSITION -> instructions[multiply.second.value]
            IMMEDIATE -> multiply.second.value
        }

        instructions[multiply.result.value] = x * y
    }

    private fun applyAdd(add: Add) {
        val x = when (add.first.mode) {
            POSITION -> instructions[add.first.value]
            IMMEDIATE -> add.first.value
        }

        val y = when (add.second.mode) {
            POSITION -> instructions[add.second.value]
            IMMEDIATE -> add.second.value
        }

        instructions[add.result.value] = x + y
    }

    private fun lessThan(lessThan: LessThan) {
        val x = when (lessThan.first.mode) {
            POSITION -> instructions[lessThan.first.value]
            IMMEDIATE -> lessThan.first.value
        }

        val y = when (lessThan.second.mode) {
            POSITION -> instructions[lessThan.second.value]
            IMMEDIATE -> lessThan.second.value
        }

        instructions[lessThan.result.value] = if (x < y) 0 else 1
    }

    private fun areTheyEqual(equals: Equals) {
        val x = when (equals.first.mode) {
            POSITION -> instructions[equals.first.value]
            IMMEDIATE -> equals.first.value
        }

        val y = when (equals.second.mode) {
            POSITION -> instructions[equals.second.value]
            IMMEDIATE -> equals.second.value
        }

        instructions[equals.result.value] = if (x == y) 0 else 1
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

class JumpIfTrue(val first: Parameter, val second: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 3
}

class JumpIfFalse(val first: Parameter, val second: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 3
}

class LessThan(val first: Parameter, val second: Parameter, val result: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 4
}

class Equals(val first: Parameter, val second: Parameter, val result: Parameter) : Instruction() {
    override val moveIndexOnBy: Int = 4
}

object Terminate : Instruction() {
    override val moveIndexOnBy: Int = 1
}

object InstructionFactory {

    fun from(instructions: List<Int>, index: Int): Instruction {

        // Position: 0 1 2 34
        // Eg code : 0 1 0 02
        val code = instructions[index].toString().padStart(5, '0')

        val first: Parameter by lazy { Parameter(ParameterMode.fromId(code.getAsInt(2)), instructions[index + 1]) }
        val second: Parameter by lazy { Parameter(ParameterMode.fromId(code.getAsInt(1)), instructions[index + 2]) }
        val third: Parameter by lazy { Parameter(ParameterMode.fromId(code.getAsInt(0)), instructions[index + 3]) }

        return when (code.takeLast(2).toInt()) {
            1 -> Add(first, second, third)
            2 -> Multiply(first, second, third)
            3 -> Save(first)
            4 -> Output(first)
            5 -> JumpIfTrue(first, second)
            6 -> JumpIfFalse(first, second)
            7 -> LessThan(first, second, third)
            8 -> Equals(first, second, third)
            99 -> Terminate
            else -> throw IllegalArgumentException("Whoops, operation ${instructions[index]} not recognised - something's gone wrong")
        }
    }
}

fun String.getAsInt(index: Int) = this[index].toString().toInt()

data class Parameter(val mode: ParameterMode, val value: Int)

enum class ParameterMode(private val id: Int) {
    POSITION(0), IMMEDIATE(1);

    companion object {
        fun fromId(id: Int) = values().first { it.id == id }
    }
}
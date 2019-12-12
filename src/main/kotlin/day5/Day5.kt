package day5

import FileReader
import day5.ParameterMode.IMMEDIATE
import day5.ParameterMode.POSITION

fun main() {
    val result1 = Program(FileReader.readFile("day5/program.txt")).run(1)
    println("Result: $result1 (should be 13285749)")

    val result2 = Program(FileReader.readFile("day5/program.txt")).run(5)
    println("Result: $result2 (should be ??)")
}

class Program(initialInstructions: String) {

    private var instructions = initialInstructions.split(",").map { it.toInt() }.toMutableList()
    private var output: Int? = null
    private var pointer = 0

    fun showMeTheInstructions() = instructions.joinToString(",")

    fun run(input: Int? = null): Int? {

        loop@ while (true) {
            val code = instructions[pointer].toString().padStart(5, '0')

            val operation = code.takeLast(2).toInt()
            val first by lazy {
                Parameter(ParameterMode.fromId(code.getAsInt(2)), instructions[pointer + 1])
            }
            val second by lazy {
                Parameter(ParameterMode.fromId(code.getAsInt(1)), instructions[pointer + 2])
            }
            val third by lazy {
                Parameter(ParameterMode.fromId(code.getAsInt(0)), instructions[pointer + 3])
            }

            when (operation) {
                1 -> applyAdd(first, second, third)
                2 -> applyMultiply(first, second, third)
                3 -> save(input, first)
                4 -> output(first)
                5 -> jumpIfTrue(first, second)
                6 -> jumpIfFalse(first, second)
                7 -> lessThan(first, second, third)
                8 -> areTheyEqual(first, second, third)
                99 -> break@loop
                else -> throw IllegalArgumentException("Whoops, operation $operation not recognised - something's gone wrong")
            }
        }

        return output
    }

    private fun jumpIfTrue(first: Parameter, second: Parameter) {
        val x = when (first.mode) {
            POSITION -> instructions[first.value]
            IMMEDIATE -> first.value
        }
        val y = when (second.mode) {
            POSITION -> instructions[second.value]
            IMMEDIATE -> second.value
        }
        if (x != 0) pointer = y else pointer += 3
    }

    private fun jumpIfFalse(first: Parameter, second: Parameter) {
        val x = when (first.mode) {
            POSITION -> instructions[first.value]
            IMMEDIATE -> first.value
        }
        val y = when (second.mode) {
            POSITION -> instructions[second.value]
            IMMEDIATE -> second.value
        }
        if (x == 0) pointer = y else pointer += 3
    }

    private fun save(input: Int?, parameter: Parameter) {
        instructions[parameter.value] =
            input ?: throw IllegalArgumentException("Save instruction should come with an input")
        pointer += 2
    }

    private fun output(parameter: Parameter) {
        pointer += 2
        output =  when (parameter.mode) {
            POSITION -> instructions[parameter.value]
            IMMEDIATE -> parameter.value
        }
    }

    private fun applyMultiply(first: Parameter, second: Parameter, result: Parameter) {
        val x = when (first.mode) {
            POSITION -> instructions[first.value]
            IMMEDIATE -> first.value
        }

        val y = when (second.mode) {
            POSITION -> instructions[second.value]
            IMMEDIATE -> second.value
        }

        instructions[result.value] = x * y
        pointer += 4
    }

    private fun applyAdd(first: Parameter, second: Parameter, third: Parameter) {
        val x = when (first.mode) {
            POSITION -> instructions[first.value]
            IMMEDIATE -> first.value
        }

        val y = when (second.mode) {
            POSITION -> instructions[second.value]
            IMMEDIATE -> second.value
        }

        instructions[third.value] = x + y
        pointer += 4
    }

    private fun lessThan(first: Parameter, second: Parameter, third: Parameter) {
        val x = when (first.mode) {
            POSITION -> instructions[first.value]
            IMMEDIATE -> first.value
        }

        val y = when (second.mode) {
            POSITION -> instructions[second.value]
            IMMEDIATE -> second.value
        }

        instructions[third.value] = if (x < y) 1 else 0
        pointer += 4
    }

    private fun areTheyEqual(first: Parameter, second: Parameter, third: Parameter) {
        val x = when (first.mode) {
            POSITION -> instructions[first.value]
            IMMEDIATE -> first.value
        }

        val y = when (second.mode) {
            POSITION -> instructions[second.value]
            IMMEDIATE -> second.value
        }

        instructions[third.value] = if (x == y) 1 else 0
        pointer += 4
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
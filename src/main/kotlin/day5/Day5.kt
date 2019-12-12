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
                1 -> add(first, second, third)
                2 -> multiply(first, second, third)
                3 -> save(input, first)
                4 -> output(first)
                5 -> jumpIfTrue(first, second)
                6 -> jumpIfFalse(first, second)
                7 -> lessThan(first, second, third)
                8 -> isEqual(first, second, third)
                99 -> break@loop
                else -> throw IllegalArgumentException("Whoops, operation $operation not recognised - something's gone wrong")
            }
        }

        return output
    }

    private fun jumpIfTrue(first: Parameter, second: Parameter) {
        if (first.valueOf() != 0) pointer = second.valueOf() else pointer += 3
    }

    private fun jumpIfFalse(first: Parameter, second: Parameter) {
        if (first.valueOf() == 0) pointer = second.valueOf() else pointer += 3
    }

    private fun save(input: Int?, position: Parameter) {
        instructions[position.asIndex()] =
            input ?: throw IllegalArgumentException("Save instruction should come with an input")
        pointer += 2
    }

    private fun output(position: Parameter) {
        output = position.valueOf()
        pointer += 2
    }

    private fun multiply(first: Parameter, second: Parameter, result: Parameter) {
        instructions[result.asIndex()] = first.valueOf() * second.valueOf()
        pointer += 4
    }

    private fun add(first: Parameter, second: Parameter, position: Parameter) {
        instructions[position.asIndex()] = first.valueOf() + second.valueOf()
        pointer += 4
    }

    private fun lessThan(first: Parameter, second: Parameter, position: Parameter) {
        instructions[position.asIndex()] = if (first.valueOf() < second.valueOf()) 1 else 0
        pointer += 4
    }

    private fun isEqual(first: Parameter, second: Parameter, third: Parameter) {
        instructions[third.asIndex()] = if (first.valueOf() == second.valueOf()) 1 else 0
        pointer += 4
    }

    private fun Parameter.valueOf(): Int {
        return when (mode) {
            POSITION -> instructions[value]
            IMMEDIATE -> value
        }
    }
}

data class Parameter(val mode: ParameterMode, val value: Int) {
    fun asIndex() = value
}

fun String.getAsInt(index: Int) = this[index].toString().toInt()

enum class ParameterMode(private val id: Int) {
    POSITION(0), IMMEDIATE(1);

    companion object {
        fun fromId(id: Int) = values().first { it.id == id }
    }
}
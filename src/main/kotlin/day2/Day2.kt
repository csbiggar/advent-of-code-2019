package day2


fun main() {
    val programInput = FileReader.readFile("day2/gravity-assist-program.txt").split(",").toMutableList()
    programInput[1] = "12"
    programInput[2] = "2"

    val result = Program.run(programInput.joinToString(","))

    println(result)
}

object Program {

    fun run(program: String): String {
        val output = program.split(",").map { it.toInt() }.toMutableList()
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

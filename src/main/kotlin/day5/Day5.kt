package day5

import FileReader
import intcode.Program

fun main() {
    val result1 = Program(FileReader.readFile("day5/program.txt")).run(1)
    println("Result: $result1 (should be 13285749)")

    val result2 = Program(FileReader.readFile("day5/program.txt")).run(5)
    println("Result: $result2 (should be 5000972)")
}
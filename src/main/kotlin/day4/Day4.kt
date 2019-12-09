package day4

fun main() {
    val result = (240298..784956)
        .filter { meetsCriteria(it) }
        .count()

    println(result)
}

fun meetsCriteria(number: Int, min: Int = 240298, max: Int = 784956): Boolean {
    return containsDouble(number)
            && isAscending(number)
            && number in min..max
}

private fun isAscending(number: Int): Boolean {
    val digits = number.toString().toList().map { it.toInt() }
    val lastIndex = digits.size - 1

    (1..lastIndex).forEach { index ->
        if (digits[index] < digits[index - 1]) return false
    }
    return true
}

private fun containsDouble(number: Int): Boolean {
    return number
        .toString()
        .windowed(2, 1)
        .filter { it.length == 2 }
        .any { neighbours -> neighbours[0] == neighbours[1] }
}


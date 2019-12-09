package day4

fun main() {
    val result = (240298..784956)
        .filter { meetsCriteria(it) }
        .count()

    println(result)
}

fun meetsCriteria(number: Int): Boolean {
    return containsExactDouble(number)
            && isAscending(number)
}

private fun isAscending(number: Int): Boolean {
    val digits = number.toString().toList().map { it.toInt() }
    val lastIndex = digits.size - 1

    (1..lastIndex).forEach { index ->
        if (digits[index] < digits[index - 1]) return false
    }
    return true
}

private fun containsExactDouble(number: Int): Boolean {
    val digits = number.toString().toList().map { it.toInt() }
    val lastIndex = digits.size - 1

    (0..lastIndex).forEach { index ->
        if (pairOfDigitsMatch(digits, index)
            && digitBeforePairIsDifferent(digits, index)
            && digitAfterPairIsDifferent(digits, index)
        ) return true
    }
    return false
}

private fun digitAfterPairIsDifferent(digits: List<Int>, index: Int): Boolean {
    val lastIndex = digits.size - 1
    val indexBeyondNext = index + 2
    return indexBeyondNext > lastIndex || digits[indexBeyondNext] != digits[index]
}

private fun digitBeforePairIsDifferent(digits: List<Int>, index: Int): Boolean {
    val previousIndex = index - 1
    return previousIndex < 0 || digits[previousIndex] != digits[index]
}

private fun pairOfDigitsMatch(digits: List<Int>, index: Int): Boolean {
    val lastIndex = digits.size - 1
    return index < lastIndex && digits[index] == digits[index + 1]
}

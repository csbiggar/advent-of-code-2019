object FileReader {
    fun readLinesAsIntegers(fileName: String) = this::class.java.getResourceAsStream(fileName)
        .bufferedReader()
        .readLines()
        .map { it.toInt() }

    fun readFile(fileName: String) = this::class.java.getResourceAsStream(fileName)
        .bufferedReader()
        .readLine()

    fun readLines(fileName: String) = this::class.java.getResourceAsStream(fileName)
        .bufferedReader()
        .readLines()
}
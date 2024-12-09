package aoc

fun main() {
    Day04().run()
}

class Day04 : Base<List<String>, Int>() {
    override val filename: String = "day_04.txt"
    override val part1Result: Int = 2504
    override val part2Result: Int = 1923

    override fun readInput(filename: String): List<String> = getFileFromResource(filename).readLines()

    override fun part1(input: List<String>): Int {
        val word = "XMAS"
        var count = 0

        for(row in input.indices) {
            for (col in input[row].indices) {
                count += checkIndex(row, col, input, word)
            }
        }
        return count
    }

    private fun checkIndex(row: Int, col: Int, input: List<String>, word: String): Int {
        var count = 0
        if (input[row][col] == word[0]) {
            if (checkForward(row, col, input, word)) { count++ }
            if (checkBackward(row, col, input, word)) { count++ }
            if (checkUp(row, col, input, word)) { count++ }
            if (checkDown(row, col, input, word)) { count++}

            if (checkNE(row, col, input, word)) { count++}
            if (checkNW(row, col, input, word)) { count++}
            if (checkSE(row, col, input, word)) { count++}
            if (checkSW(row, col, input, word)) { count++}
        }
        return count
    }

    private fun checkForward(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r to c+1}
    private fun checkBackward(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r to c-1}
    private fun checkUp(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r-1 to c}
    private fun checkDown(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r+1 to c}

    private fun checkNE(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r-1 to c+1}
    private fun checkNW(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r-1 to c-1}
    private fun checkSE(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r+1 to c+1}
    private fun checkSW(row: Int, col: Int, input: List<String>, word: String) = checkDirection(row, col, input, word) {r,c -> r+1 to c-1}

    private fun checkDirection(row: Int, col: Int, input: List<String>, word: String, mutate: (row: Int, col: Int) -> Pair<Int, Int>): Boolean {
        if (word.isEmpty()) return true
        if (row < 0 || row > input.lastIndex) return false
        if (col < 0 || col > input[row].lastIndex) return false
        if (input[row][col] == word[0]) {
            val sub = word.substring(1)
            val (newRow, newCol) = mutate(row, col)
            return checkDirection(newRow, newCol, input, sub, mutate)
        }
        return false
    }

    override fun part2(input: List<String>): Int {
        var count = 0

        for(row in 1..<input.lastIndex) {
            for (col in 1..<input[row].lastIndex) {
                if (input[row][col] == 'A') {
                    val mas1 = "${input[row-1][col-1]}A${input[row+1][col+1]}"
                    val mas2 = "${input[row-1][col+1]}A${input[row+1][col-1]}"
                    if ((mas1 == "MAS" || mas1.reversed() == "MAS") && (mas2 == "MAS" || mas2.reversed() == "MAS")) {
                        count++
                    }
                }
            }
        }
        return count
    }
}


package aoc

fun main() {
    Day09().run()
}

class Day09 : Base<String, Long>() {
    override val filename = "day_09.txt"
    override val part1Result = 6332189866718
    override val part2Result = -1L
    private val FREE_SPACE = -1

    override fun part1(input: String): Long {
        val diskMap = processDiskMap(input)
        val defrag = compressFiles(diskMap)
        return checksum(defrag)
    }

    override fun part2(input: String): Long {
        // Not too different, except need to find how long the orig file is, and when searching for index need to find contiguous blank area.
        return 0
    }

    private fun processDiskMap(input: String): List<Int> {
        val list = mutableListOf<Int>()
        input.forEachIndexed { idx, ch ->
            val value = Character.getNumericValue(ch)
            if (idx.isEven()) {
                val id = idx / 2
                for (i in 0..< value) {
                    list.add(id)
                }
            } else {
                for (i in 0..< value) {
                    list.add(FREE_SPACE)
                }
            }
        }
        return list.toList()
    }

    private fun compressFiles(diskmap: List<Int>): List<Int> {
        val defrag = diskmap.toMutableList()
        var lastEmptyIndex = -1

        for (i in diskmap.lastIndex downTo 0) {
            val value = diskmap[i]

            // If free then don't try to move it
            if (value == FREE_SPACE) {
                continue
            }

            val nextEmptyIndex = findNextEmptyIndex(lastEmptyIndex, diskmap)

            // If our next empty index is greater than our current index then we've finished
            if (nextEmptyIndex > i) {
                break
            }
            defrag[i] = FREE_SPACE
            defrag[nextEmptyIndex] = value
            lastEmptyIndex = nextEmptyIndex
        }
        return defrag.toList()
    }

    private fun findNextEmptyIndex(lastIndex: Int, list: List<Int>): Int {
        val sub = list.subList(lastIndex + 1, list.lastIndex)
        return lastIndex + 1 + sub.indexOfFirst { it == FREE_SPACE }
    }

    private fun diskMapToString(diskmap: List<Int>): String {
        return diskmap.joinToString("")  { if (it == FREE_SPACE) "." else it.toString() }
    }

    private fun checksum(diskmap: List<Int>) = diskmap.mapIndexed { idx, value -> if (value == FREE_SPACE) 0 else idx * value }.sumOf { it.toLong() }

    override fun readInput(filename: String) = getFileFromResource(filename).readText()

}


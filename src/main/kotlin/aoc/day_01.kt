package aoc

import kotlin.math.abs


fun main() {
    day_01().run()
}

class day_01 {

    fun run() {
        val inputFilename = "day_01.txt"
        val (list1, list2) = readLists(inputFilename)
        val totalDistance = getTotalDistance(list1, list2)
        val similarityScore = getSimilarityScore(list1, list2)

        println("Total Distance: $totalDistance")
        println("Similarity Score: $similarityScore")
    }


    private fun getTotalDistance(list1: List<Int>, list2: List<Int>): Int {
        var total = 0
        for (i in 0..list1.lastIndex) {
            total += abs(list1[i] - list2[i])
        }
        return total
    }

    private fun getSimilarityScore(list1: List<Int>, list2: List<Int>): Int {
        // brute force cuz i dont have time
        // but we could easily break out of shorter loop once i2 was bigger than i1 if these were for loops
        // we could similarly keep track so we didn't have to start at 0 each time.dxs
        var total = 0
        list1.forEach { i1 ->
            var count = 0
            list2.forEach { i2 ->
                if (i2 == i1) {
                    count++
                }
            }
            total += i1 * count
        }
        return total
    }

    private fun readLists(file: String): Pair<List<Int>, List<Int>> {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        getFileFromResource(file).forEachLine {
            val split = it.split("   ")
            list1.add(split[0].toInt())
            list2.add(split[1].toInt())
        }
        list1.sort()
        list2.sort()
        return list1 to list2
    }


}





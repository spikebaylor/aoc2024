package aoc

import kotlin.math.abs

fun main() {
    val inputFilename = "day_01.txt"

    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    getFileFromResource(inputFilename).forEachLine {
        val split = it.split("   ")
        list1.add(split[0].toInt())
        list2.add(split[1].toInt())
    }
    list1.sort()
    list2.sort()
    var total = 0
    for (i in 0..list1.lastIndex) {
        total += abs(list1[i] - list2[i])
    }
    println(total)
}



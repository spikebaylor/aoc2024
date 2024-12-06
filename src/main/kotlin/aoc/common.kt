package aoc

import java.io.File

fun Any.getFileFromResource(file: String): File {
    return File(this::class.java.classLoader.getResource(file)!!.toURI())
}

abstract class Base<T> {
    abstract val filename: String
    abstract val part1Result: Int
    abstract val part2Result: Int

    abstract fun part1(input: T): Int
    abstract fun part2(input: T): Int
    abstract fun readInput(filename: String): T

    fun run() {
        var input = readInput(filename)

        var start = System.currentTimeMillis()
        val part1 = part1(input)
        var stop = System.currentTimeMillis()
        val part1Time = stop - start

        // re-reading just in case one of the part1s needs/wants/accidentally changes the input
        input = readInput(filename)
        start = System.currentTimeMillis()
        val part2 = part2(input)
        stop = System.currentTimeMillis()
        val part2Time = stop - start
        println("Part 1: $part1  [$part1Time ms]")
        println("Part 2: $part2  [$part2Time ms]")
        if(part1Result != part1) { println("ERROR Part1 result: $part1 does not match expected result $part1Result") }
        if(part2Result != part2) { println("ERROR Part2 result: $part2 does not match expected result $part2Result") }
    }

}
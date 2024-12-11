package aoc

fun main() {
    Day11().run()
}

class Day11 : StringBase("day_11.txt", part1Result = 186424) {
    override fun part1(input: String): Long {
        val original = process(input)
        var step = original.toList()
        repeat(25) { step = blink(step)}
        return step.size.toLong()
    }

    override fun part2(input: String): Long {
        return 0
/*        val original = process(input)
        val blinks = 75
        return original.sumOf { println("Blinking $it"); blink2(it, blinks) }*/

        // even my slightly better recursive answer is too large.  Actually my recursive answer is slower than the other one :'(

        // OLD
        // I think this is going to need to be some sort of divide and conquer / recursive algo.  the size VERY quickly gets too large.
        // my original part1 but running 75 times got OOM.  Even taking a single number and blinking 75 times
        // turned into 34694541 stones in 40 blinks.
        // actually.. the only number that matters in the end is the count.  so yeah i think the idea would be to make
        // a recursive algorithm where it takes in the stone and the number of blinks, and returns the number of stones that are created.
        // less is kept in memory and should be faster.
    }

    private fun blink2(stone: Long, blinks: Int): Long {
        if (blinks == 0) return 0
        if (blinks==70) println("blinks: $blinks")


        val result = blink(listOf(stone))
        return result.sumOf { blink2(it, blinks - 1) }
    }

    private fun blink(input: List<Long>): List<Long> {
        val output = mutableListOf<Long>()
        input.forEach {
            when {
                it == 0L -> output.add(1)
                it.toString().length.isEven() -> {
                    val (first, second) = splitStone(it)
                    output.add(first)
                    output.add(second)
                }
                else -> output.add(it * 2024)
            }
        }
        return output
    }

    private fun splitStone(input: Long): Pair<Long, Long> {
        val str = input.toString()
        val idx = (str.length / 2)
        val first = str.substring(0, idx)
        val second = str.substring(idx)
        return first.toLong() to second.toLong()
    }

    private fun process(input: String): List<Long> = input.split(" ").map { it.toLong() }
}
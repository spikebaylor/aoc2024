package aoc

fun main() {
    Day05().run()
}

data class Rule(val before: Int, val after: Int)
typealias Update = List<Int>
data class Day05Input(val ruleSet: List<Rule>, val updateSet: List<Update>)

class Day05 : Base<Day05Input, Int>() {
    override val filename = "day_05.txt"
    override val part1Result = 7307
    override val part2Result = 4713

    override fun readInput(filename: String): Day05Input {
        val lines = getFileFromResource(filename).readLines()
        val ruleRegex = "([0-9]*)\\|([0-9]*)".toRegex()
        val ruleSet = mutableListOf<Rule>()
        val updateSet = mutableListOf<Update>()
        lines.forEach { line ->
            if (ruleRegex.matches(line)) {
                ruleRegex.find(line)?.let {
                    val groups = it.groupValues
                    ruleSet.add(Rule(groups[1].toInt(), groups[2].toInt()))
                }
            } else if (line.isNotBlank()) {
                val update = line.split(",").map { it.toInt() }
                updateSet.add(update)
            }
        }
        return Day05Input(ruleSet, updateSet)
    }

    override fun part1(input: Day05Input): Int {
        val ruleMap = processRuleSet(input.ruleSet)
        return input.updateSet.sumOf { if (isUpdateInOrder(it, ruleMap)) getMiddle(it) else 0 }
    }

    private fun isUpdateInOrder(update: Update, ruleMap: Map<Int, List<Int>>): Boolean {
        val processed = processUpdate(update)
        return update.mapIndexed { index, i ->
            val mustBeAfter = ruleMap[i]
            val pass = mustBeAfter?.all {
                val rIndex = processed[it]
                var good = true
                if (rIndex != null) {
                    good = rIndex > index
                }
                good
            } ?: true

            pass
        }.all { it }
    }

    private fun getMiddle(update: Update): Int {
        val midIndex = (update.size / 2)
        return update[midIndex]
    }

    private fun processUpdate(update: Update): Map<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        update.forEachIndexed { index, i ->  map[i] = index}
        return map
    }

    private fun processRuleSet(ruleSet: List<Rule>): Map<Int, List<Int>> {
        val map = mutableMapOf<Int, List<Int>>()
        ruleSet.forEach { rule ->
            map.computeIfPresent(rule.before) { _, v -> v.toMutableList().apply { add(rule.after) } }
            map.putIfAbsent(rule.before, listOf(rule.after))
        }
        return map
    }

    override fun part2(input: Day05Input): Int {
        val ruleMap = processRuleSet(input.ruleSet)
        var total = 0
        input.updateSet.forEach { update ->
            if (!isUpdateInOrder(update, ruleMap)) {
                val fixed = fixUpdate(update, ruleMap)
                total += getMiddle(fixed)
            }
        }
        return total
    }

    private fun fixUpdate(update: Update, ruleMap: Map<Int, List<Int>>): Update {
        return update.sortedWith { o1, o2 ->
            when {
                ruleMap[o1]?.contains(o2) == true -> -1
                ruleMap[o2]?.contains(o1) == true -> 1
                else -> 0
            }
        }
    }


}
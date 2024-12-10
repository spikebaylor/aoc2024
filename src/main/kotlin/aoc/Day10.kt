package aoc

fun main() {
    Day10().run()
}

class Day10 : LinesBase("day_10.txt") {
    override fun part1(input: List<String>): Long {
        val topo = processTopo(input)
        var total = 0L
        topo.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, _ ->
                if (topo[rowIdx][colIdx] == 0) {
                    val set = walkTrail(rowIdx, colIdx, 0, topo)
                    total += set.size

                }
            }
        }
        return total
    }

    private fun processTopo(input: List<String>): List<List<Int>> {
        return input.map { row ->
            row.toCharArray().map { Character.getNumericValue(it)}.toList()
        }
    }

    private fun walkTrail(row: Int, col: Int, height: Int, topo: List<List<Int>>): Set<Pair<Int, Int>> {
        if (row < 0 || row > topo.lastIndex || col < 0 || col > topo[0].lastIndex) return emptySet()
        if (topo[row][col] != height) return emptySet()
        if (height == 9) {
            return setOf(row to col)
        }
        val endSet = mutableSetOf<Pair<Int, Int>>()
        endSet.addAll(walkTrail(row+1, col, height+1, topo))
        endSet.addAll(walkTrail(row, col+1, height+1, topo))
        endSet.addAll(walkTrail(row, col-1, height+1, topo))
        endSet.addAll(walkTrail(row-1, col, height+1, topo))
        return endSet
    }

    override fun part2(input: List<String>): Long {
        return 0
    }


}
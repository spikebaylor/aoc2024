package aoc

fun main() {
    Day06().run()
}

class Day06 : Base<List<CharArray>, Int>() {
    override val filename: String = "day_06.txt"
    override val part1Result: Int = 5318
    override val part2Result: Int = -1

    override fun readInput(filename: String): List<CharArray> {
        return getFileFromResource(filename).readLines().map { it.toCharArray() }
    }

    override fun part1(map: List<CharArray>): Int {
        var count = 0
        var current = findStartingLocation(map)
        var direction: Direction = Direction.NORTH
        while(true) {
            if (map[current.row][current.col] != 'X') {
                map[current.row][current.col] = 'X'
                count++
            }
            val next = nextLocation(current, direction)
            if (isOutside(next, map)) {
                break
            }
            if (map[next.row][next.col] == '#') {
                direction = direction.turnRight()
            }
            current = nextLocation(current, direction)
        }
        return count
    }

    fun isOutside(loc: Location, map: List<CharArray>): Boolean {
        return loc.row < 0 || loc.row > map.lastIndex || loc.col < 0 || loc.col > map[0].lastIndex
    }

    fun findStartingLocation(map: List<CharArray>): Location {
        map.forEachIndexed { row, contents ->
            val col = contents.indexOf('^')
            if (col > -1) {
                return Location(row, col)
            }
        }
        return Location(0, 0)
    }

    fun nextLocation(current: Location, direction: Direction): Location {
        return Location(current.row + direction.rowMove, current.col + direction.colMove)
    }

    override fun part2(map: List<CharArray>): Int {
        var count = 0
        var current = findStartingLocation(map)
        var direction: Direction = Direction.NORTH

        while(true) {
            val next = nextLocation(current, direction)
            if (isOutside(next, map)) { break }
            if (map[next.row][next.col] == '#') {
                direction = direction.turnRight()
            }
            current = nextLocation(current, direction)
        }
        return count
    }

    fun printMap(map: List<CharArray>) {
        println("\n\n===== MAP ===== ")
        map.forEach { println(it) }
    }
}

data class Location(val row: Int, val col: Int)
sealed class Direction(val rowMove: Int, val colMove: Int) {
    data object NORTH : Direction(-1, 0)
    data object SOUTH : Direction(1, 0)
    data object EAST : Direction(0, 1)
    data object WEST : Direction(0, -1)
}

fun Direction.turnRight() = when (this) {
    Direction.NORTH -> Direction.EAST
    Direction.EAST -> Direction.SOUTH
    Direction.SOUTH -> Direction.WEST
    Direction.WEST -> Direction.NORTH
}
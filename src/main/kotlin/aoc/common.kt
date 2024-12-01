package aoc

import java.io.File

fun Any.getFileFromResource(file: String): File {
    return File(this::class.java.classLoader.getResource(file)!!.toURI())
}

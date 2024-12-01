package aoc

import java.io.File

fun getFileFromResource(file: String): File {
    // Cuz i need a class loader that works
    class Blah()
    return File(Blah()::class.java.classLoader.getResource(file)!!.toURI())
}

package com.plm.junglejumble.utils

fun generatePairs(count: Int): List<Pair<Int, Int>> {
    require(count % 2 == 0) { "Count must be even to form pairs." }

    val numbers = (0 until count).shuffled()
    return numbers.chunked(2).map { Pair(it[0], it[1]) }
}
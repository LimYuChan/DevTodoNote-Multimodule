package com.note.core.domain.util

import java.util.*

object IntegerUtils {
    fun randomToRange(min: Int = 1000, max: Int = 10000): Int {
        return Random().nextInt(max - min) + min
    }
}
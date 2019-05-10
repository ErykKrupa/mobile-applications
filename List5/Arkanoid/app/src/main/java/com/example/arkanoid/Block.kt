package com.example.arkanoid

import android.graphics.Paint
import android.graphics.Rect
import kotlin.random.Random

open class Block(val rect: Rect, private val platform: Boolean) {
    var color = Paint()
    var visible = true
        set(visible) {
            if (!platform) {
                color.alpha = if (visible) 255 else 0
                field = visible
            }
        }

    init {
        val random = Random
        color.setARGB(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }


}
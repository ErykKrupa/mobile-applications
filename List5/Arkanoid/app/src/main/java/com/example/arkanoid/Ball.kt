package com.example.arkanoid

import android.graphics.Paint
import java.lang.Math.abs

class Ball(
    var x: Float,
    var y: Float,
    val diameter: Float,
    var deltaX: Float,
    var deltaY: Float,
    val color: Paint
) {

    fun isOnBlock(block: Block): Boolean {
        return if (check(block)) {
            block.visible = false
            true
        } else {
            false
        }
    }

    private fun check(block: Block): Boolean {
        if (!block.visible) {
            return false
        }
        if (block.rect.top <= y + diameter && y + diameter <= block.rect.bottom && block.rect.left <= x && x + diameter <= block.rect.right) {
            deltaY = -abs(deltaY) // hit from top side
            return true
        } else if (block.rect.left <= x && x <= block.rect.right && block.rect.top <= y && y + diameter <= block.rect.bottom) {
            deltaX = abs(deltaX) // hit from right side
            return true
        } else if (block.rect.top <= y && y <= block.rect.bottom && block.rect.left <= x && x + diameter <= block.rect.right) {
            deltaY = abs(deltaY) // hit from bottom side
            return true
        } else if (block.rect.left <= x + diameter && x + diameter <= block.rect.right && block.rect.top <= y && y + diameter <= block.rect.bottom) {
            deltaX = -abs(deltaX) // hit from left side
            return true
        } else if (block.rect.left <= x + diameter && x + diameter <= block.rect.right && block.rect.top <= y + diameter && y + diameter <= block.rect.bottom) {
            deltaX = -abs(deltaX) // hit from top left corner
            deltaY = -abs(deltaY)
            return true
        } else if (block.rect.left <= x && x <= block.rect.right && block.rect.top <= y + diameter && y + diameter <= block.rect.bottom) {
            deltaX = abs(deltaX) // hit from top right corner
            deltaY = -abs(deltaY)
            return true
        } else if (block.rect.left <= x && x <= block.rect.right && block.rect.top <= y && y <= block.rect.bottom) {
            deltaX = abs(deltaX) // hit from bottom right corner
            deltaY = abs(deltaY)
            return true
        } else if (block.rect.left <= x + diameter && x + diameter <= block.rect.right && block.rect.top <= y && y <= block.rect.bottom) {
            deltaX = -abs(deltaX) // hit from bottom left corner
            deltaY = abs(deltaY)
            return true
        }
        return false
    }
}
package com.example.arkanoid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Math.abs

class GameView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {

    private var grey = Paint()
    private val ball: Ball by lazy { Ball((0.5 * width).toFloat(), (0.8 * height).toFloat(), 40f, 10f, -10f, grey) }

    private val blocksColumns = 8
    private val blocksRows = 15
    private val blockWidth by lazy { width / blocksColumns }
    private val blockHeight by lazy { height / (2 * blocksRows) }
    private val blocks: Array<Array<Block>> by lazy {
        Array(blocksRows) { i ->
            Array(blocksColumns) { j ->
                Block(
                    Rect(
                        j * blockWidth, i * blockHeight,
                        (j + 1) * blockWidth, (i + 1) * blockHeight
                    ), false
                )
            }
        }
    }

    interface GameScoreListener {
        fun onGameEnd(score: Int)
    }

    private var gameScoreListener: GameScoreListener? = null

    fun setGameScoreListener(listener: GameScoreListener, highScore: String) {
        gameScoreListener = listener
        this.highScore = highScore
    }

    private val platform by lazy {
        Block(
            Rect(
                (0.42 * width).toInt(),
                (0.82 * height).toInt(),
                (0.58 * width).toInt(),
                (0.85 * height).toInt()
            ), true
        )
    }

    private val thread: GameThread
    private var score = 0
    private val maxScore = blocksColumns * blocksRows
    private var highScore = ""

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        grey.setARGB(255, 128, 128, 128)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.interrupt()
        thread.join()
        gameScoreListener?.onGameEnd(score)

    }

    fun update() {
        ball.x += ball.deltaX
        ball.y += ball.deltaY
        if (ball.x <= 0) {
            ball.deltaX = abs(ball.deltaX)
        } else if (width <= ball.x + ball.diameter) {
            ball.deltaX = -abs(ball.deltaX)
        }
        if (ball.y <= 0) {
            ball.deltaY = abs(ball.deltaY)
        } else if (height <= ball.y + ball.diameter) {
            thread.interrupt()
            gameScoreListener?.onGameEnd(score)
        }
        for (i in 0 until blocksRows) {
            for (j in 0 until blocksColumns) {
                if (ball.isOnBlock(blocks[i][j])) {
                    score++
                }
            }
        }
        ball.isOnBlock(platform)
        if (score == maxScore) {
            thread.interrupt()
            gameScoreListener?.onGameEnd(score)
        }
    }

    private val textPaint = Paint().apply {
        setARGB(255, 255, 255, 255)
        textSize = 50f
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return
        canvas.drawOval(RectF(ball.x, ball.y, ball.x + ball.diameter, ball.y + ball.diameter), ball.color)
        for (i in 0 until blocksRows) {
            for (j in 0 until blocksColumns) {
                canvas.drawRect(blocks[i][j].rect, blocks[i][j].color)
            }
        }
        canvas.drawRect(platform.rect, platform.color)
        canvas.drawText("Score: $score/$maxScore", width * 0.15f, height * 0.95f, textPaint)
        canvas.drawText("High score: $highScore", width * 0.60f, height * 0.95f, textPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val platformWidth = (platform.rect.right - platform.rect.left) / 2
        if (0 < event.x - platformWidth && event.x + platformWidth < width) {
            platform.rect.left = (event.x - (platformWidth)).toInt()
            platform.rect.right = (event.x + (platformWidth)).toInt()
            ball.isOnBlock(platform)
        }
        return true
//        return super.onTouchEvent(event)
    }
}
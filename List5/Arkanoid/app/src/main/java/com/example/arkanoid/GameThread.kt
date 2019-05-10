package com.example.arkanoid

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import java.lang.Exception
import java.util.concurrent.TimeUnit

class GameThread(
    private val surfaceHolder: SurfaceHolder,
    private val gameView: GameView
) : Thread() {
    private var running = false
    private var canvas: Canvas? = null
    private val targetFPS = 60

    fun setRunning(running: Boolean) {
        this.running = running
    }

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        val targetTime = (1000 / targetFPS).toLong()

        while (!interrupted()) {
            startTime = System.nanoTime()
            canvas = null
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(canvas)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        Log.e("Game", "xd", e)
//                        e.printStackTrace()
                    }
                } else {
                    interrupt()
                }
            }

            timeMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
            waitTime = targetTime - timeMillis

            try {
                if (waitTime > 0) {
                    sleep(waitTime)
                }
            } catch (e: InterruptedException) {
                interrupt()
            }
        }
    }
}
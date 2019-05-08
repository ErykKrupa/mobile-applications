package com.example.tic_tac_toe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalStateException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var turn = FieldType.CROSS
    private var turnCount = 1
    private var picture = if(Random.nextInt(2) == 0) R.drawable.cross1 else R.drawable.cross2
    private var gameIsRunning = false
    private var botIsActive = false
    private var isBotTurn = false
    private var bot = Bot()
    companion object {
        var fields = Array(5) { Array(5) { FieldType.EMPTY } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("PLUGIN_WARNING")
    private fun run() {
        turn = FieldType.CROSS
        turnCount = 1
        fields = Array(5) {Array(5) {FieldType.EMPTY} }
        picture = if(Random.nextInt(2) == 0) R.drawable.cross1 else R.drawable.cross2
        gameIsRunning = true
        setContentView(R.layout.activity_main)
        findViewById<ImageView>(imageViewTurn.id).setImageResource(picture)
        findViewById<Button>(buttonWithBot.id).setText(R.string.restartWithBot)
        findViewById<Button>(buttonWithoutBot.id).setText(R.string.restartWithoutBot)
    }

    @Suppress("UNUSED_PARAMETER")
    fun runWithoutBot(view: View) {
        run()
        botIsActive = false
    }

    @Suppress("UNUSED_PARAMETER")
    fun runWithBot(view: View) {
        run()
        botIsActive = true
    }

    fun draw(view: View) {
        if (!gameIsRunning || (botIsActive && turn == FieldType.CIRCLE && !isBotTurn)) return
        isBotTurn = false
        val posX = Integer.parseInt(resources.getResourceEntryName(view.id).substring(5, 6))
        val posY = Integer.parseInt(resources.getResourceEntryName(view.id).substring(6, 7))
        if (fields[posX][posY] != FieldType.EMPTY) return
        fields[posX][posY] = turn
        findViewById<ImageButton>(view.id).setImageResource(picture)
        val message = if (turn == FieldType.CROSS) R.string.crossWinsMessage else R.string.circleWinsMessage
        for (i in 0..4) {
            if (fields[posX][i] != turn) {
                break
            }
            if (i == 4) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                gameIsRunning = false
            }
        }
        for (i in 0..4) {
            if (fields[i][posY] != turn) {
                break
            }
            if (i == 4) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                gameIsRunning = false
            }
        }
        if (posX == posY) {
            for (i in 0..4) {
                if (fields[i][i] != turn) {
                    break
                }
                if (i == 4) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    gameIsRunning = false
                }
            }
        }
        if (posX + posY == 4) {
            for (i in 0..4) {
                if (fields[i][4 - i] != turn) {
                    break
                }
                if (i == 4) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    gameIsRunning = false
                }
            }
        }
        if(turnCount == 25 && gameIsRunning) {
            Toast.makeText(this, R.string.nobodyWinsMessage, Toast.LENGTH_LONG).show()
            findViewById<ImageView>(imageViewTurn.id).setImageResource(R.drawable.empty)
            gameIsRunning = false
        }
        if(gameIsRunning) {
            turn = if (turn == FieldType.CROSS) FieldType.CIRCLE else FieldType.CROSS
            picture = when (turn) {
                FieldType.CROSS -> if (Random.nextInt(2) == 0) R.drawable.cross1 else R.drawable.cross2
                FieldType.CIRCLE -> if (Random.nextInt(2) == 0) R.drawable.circle1 else R.drawable.circle2
                FieldType.EMPTY -> throw IllegalStateException()
            }
            findViewById<ImageView>(imageViewTurn.id).setImageResource(picture)
            turnCount++
            if (turn == FieldType.CIRCLE && botIsActive) {
                Handler().postDelayed({
                        isBotTurn = true
                        draw(findViewById(resources.getIdentifier(bot.getNameOfChosenField(), "id", packageName)))
                    }, 100)

            }
        }
    }
}

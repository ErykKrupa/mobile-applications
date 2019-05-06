package com.example.rockpaperscissors

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var playerScore = 0
    private var opponentScore = 0
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.playerScore).text = "$playerScore"
        findViewById<TextView>(R.id.opponentScore).text = "$opponentScore"
    }

    fun rock(view: View) {
        play(Type.ROCK)
    }

    fun paper(view: View) {
        play(Type.PAPER)
    }

    fun scissors(view: View) {
        play(Type.SCISSORS)
    }

    private fun play(playerType: Type) {
        val opponentType =
            when (random.nextInt(3)) {
                0 -> Type.ROCK
                1 -> Type.PAPER
                else -> Type.SCISSORS
            }
        setImage(playerType, findViewById(R.id.playerImage))
        setImage(opponentType, findViewById(R.id.opponentImage))
        compare(playerType, opponentType)
    }

    private fun setImage(type: Type, imageView: ImageView) {
        when (type) {
            Type.ROCK -> imageView.setImageResource(R.drawable.bigrock)
            Type.PAPER -> imageView.setImageResource(R.drawable.bigpaper)
            Type.SCISSORS -> imageView.setImageResource(R.drawable.bigscissors)
        }
    }

    private fun compare(playerType: Type, opponentType: Type) {
        if (playerType == opponentType) {
        } else if ((playerType == Type.ROCK && opponentType == Type.SCISSORS)
            || (playerType == Type.SCISSORS && opponentType == Type.PAPER)
            || (playerType == Type.PAPER && opponentType == Type.ROCK)
        ) {
            playerScore++
            findViewById<TextView>(R.id.playerScore).text = "$playerScore"
        } else {
            opponentScore++
            findViewById<TextView>(R.id.opponentScore).text = "$opponentScore"
        }
    }
}

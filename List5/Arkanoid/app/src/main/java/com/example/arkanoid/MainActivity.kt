package com.example.arkanoid

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GameView.GameScoreListener {
    private var prefs = "Arkanoid Preferences"

    override fun onGameEnd(score: Int) {
        val sharedPreferences = getSharedPreferences(prefs, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        if (score > getSharedPreferences(prefs, Context.MODE_PRIVATE).getString("highScore", "")!!.toInt()) {
            editor.putString("highScore", "$score")
        }
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val highScore = getSharedPreferences(prefs, Context.MODE_PRIVATE).getString("highScore", "")
        game_view.setGameScoreListener(this, highScore!!)
    }
}

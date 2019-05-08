package com.example.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var word = charArrayOf(' ')
    private var leftLetters = linkedSetOf<Char>()
    private var showedWord = charArrayOf(' ')
    private var isGameRunning = false
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reset()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putCharArray("word", word)
        outState?.putCharArray("leftLetters", leftLetters.toCharArray())
        outState?.putCharArray("showedWord", showedWord)
        outState?.putBoolean("isGameRunning", isGameRunning)
        outState?.putInt("count", count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        word = savedInstanceState?.getCharArray("word")!!
        leftLetters = savedInstanceState.getCharArray("leftLetters")!!.toCollection(LinkedHashSet())
        showedWord = savedInstanceState.getCharArray("showedWord")!!
        isGameRunning = savedInstanceState.getBoolean("isGameRunning")
        count = savedInstanceState.getInt("count")
        imageButton.setImageResource(resources.getIdentifier("hangman$count", "drawable", packageName))
        setShowedWord()
    }

    private fun setShowedWord() {
        var showedWordString = ""
        for(i in showedWord) {
            showedWordString += i
        }
        wordTextView.text = showedWordString.toUpperCase()
    }

    private fun reset() {
        val words = resources.getStringArray(R.array.words)
        word = words[Random.nextInt(words.size)].toCharArray()
        leftLetters = linkedSetOf()
        for (i in 0 until word.size) {
            leftLetters.add(word[i])
        }
        showedWord = CharArray(2 * word.size - 1) { i -> if (i % 2 == 0) '_' else ' '}
        setShowedWord()
        imageButton.setImageResource(resources.getIdentifier("hangman0", "drawable", packageName))
        count = 0
        isGameRunning = true
    }

    @Suppress("UNUSED_PARAMETER")
    fun reset (view: View) {
        reset()
    }

    @Suppress("UNUSED_PARAMETER")
    fun put(view: View) {
        if (!isGameRunning || letterEditText.text.toString() == "") return
        val letter = letterEditText.text.toString().single().toLowerCase()
        letterEditText.setText("")
        if(letter in word) {
            leftLetters.remove(letter)
            for(i in 0 until word.size) {
                if(word[i] == letter) {
                    showedWord[2 * i] = letter
                }
            }
            setShowedWord()
            if(leftLetters.isEmpty()) {
                Toast.makeText(this, "You've won!", Toast.LENGTH_LONG).show()
                isGameRunning = false
            }
        } else {
            count++
            imageButton.setImageResource(resources.getIdentifier("hangman$count", "drawable", packageName))
            if(count == 12) {
                for(i in 0 until word.size) {
                    showedWord[2 * i] = word[i]
                }
                setShowedWord()
                Toast.makeText(this, "You are hanged!", Toast.LENGTH_LONG).show()
                isGameRunning = false
            }
        }
    }

}
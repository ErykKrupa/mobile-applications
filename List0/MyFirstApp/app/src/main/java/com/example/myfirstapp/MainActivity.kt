package com.example.myfirstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private var score = 0
    private var leftNumber = 0
    private var rightNumber = 0
    private val random = Random()
    private val digits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        roll()
    }

    fun leftClick(view: View) {
        check(true)
    }

    fun rightClick(view: View) {
        check(false)
    }

    private fun check(leftWasChosen: Boolean) {
        if (leftNumber >= rightNumber && leftWasChosen || rightNumber >= leftNumber && !leftWasChosen) {
            score++
            Toast.makeText(this, "Great!", Toast.LENGTH_SHORT).show()
        } else {
            score--
            Toast.makeText(this, "Pff...", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.scoreTextView).text = "$score"
        roll()
    }

    private fun roll() {
        leftNumber = random.nextInt(1001) + 30
        do {
            rightNumber = leftNumber + random.nextInt(61) - 30
        } while (leftNumber == rightNumber)
        findViewById<TextView>(R.id.leftNumber).text = convertDecimalToOther(leftNumber, random.nextInt(15) + 2)
        findViewById<TextView>(R.id.rightNumber).text = convertDecimalToOther(rightNumber, random.nextInt(15) + 2)
    }

    private fun convertDecimalToOther(decimalNumber: Int, systemBase: Int): String {
        var number = decimalNumber
        val convertedNumber: MutableList<Char> = mutableListOf()
        var rest: Int
        while (number != 0) {
            rest = number % systemBase
            convertedNumber.add(0, digits[rest])
            number -= rest
            number /= systemBase
        }
        return String(convertedNumber.toCharArray()) + "($systemBase)"
    }
}

package com.example.tic_tac_toe

class Bot {
    private var crosses = 0
    private var circles = 0

    fun getNameOfChosenField(): String {
        var bestPoints = -100
        var bestX: Int? = null
        var bestY: Int? = null
        for (i in 0..4) {
            for (j in 0..4) {
                if (MainActivity.fields[i][j] == FieldType.EMPTY) {
                    val points = countAllPoints(i, j)
                    if (points > bestPoints) {
                        bestPoints = points
                        bestX = i
                        bestY = j
                    }
                }
            }
        }
        return "field$bestX$bestY"
    }

    private fun clean() {
        crosses = 0
        circles = 0
    }

    private fun countAllPoints(x: Int, y: Int): Int {
        var points = 0
        for (i in 0..4) {
            clean()
            for (j in 0..4) {
                if(countCirclesAndCrosses(x, y, i, j)) {
                    break
                }
            }
            if (circles == 5) {
                return 1000
            } else if (crosses == 4) {
                return 500
            }
            points += (circles * circles) - 2 * crosses
            clean()
            for (j in 0..4) {
                if(countCirclesAndCrosses(x, y, j, i)) {
                    break
                }
            }
            points += (circles * circles) - 2 * crosses
        }
        clean()
        for (i in 0..4) {
            if(countCirclesAndCrosses(x, y, i, i)) {
                break
            }
        }
        points += (circles * circles) - 2 * crosses
        clean()
        for (i in 0..4) {
            if(countCirclesAndCrosses(x, y, i, 4 - i)) {
                break
            }
        }
        points += (circles * circles) - 2 * crosses
        return points
    }

    private fun countCirclesAndCrosses(x: Int, y: Int, i: Int, j: Int): Boolean {
        if (MainActivity.fields[i][j] == FieldType.CIRCLE || i == x && j == y) {
            if (crosses > 0) {
                clean()
                return true
            }
            circles++
        } else if (MainActivity.fields[i][j] == FieldType.CROSS) {
            if (circles > 0) {
                clean()
                return true
            }
            crosses++
        }
        return false
    }
}

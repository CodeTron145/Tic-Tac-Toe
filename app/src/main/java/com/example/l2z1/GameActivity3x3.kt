package com.example.l2z1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.l2z1.databinding.ThreeBoardBinding


class GameActivity3x3 : AppCompatActivity() {

    private lateinit var threeBoardBinding: ThreeBoardBinding

    private var player1Turn: Boolean = true
    private var boardSize: Int = 3
    private var roundCount: Int = 0
    private var winner: String = ""

    private var score: Array<Array<Int>> = Array(boardSize) { Array(boardSize) { 0 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        threeBoardBinding = ThreeBoardBinding.inflate(layoutInflater)

        setContentView(threeBoardBinding.root)
    }

    fun turn(v: View) {
        val b = v as Button
        val pos = resources.getResourceEntryName(b.id).takeLast(2).toInt()
        val r = pos / boardSize
        val c = pos % boardSize
        if (b.text.toString() != "") return
        if (player1Turn) {
            b.text = "X"
            score[r][c] = 1
        } else {
            b.text = "O"
            score[r][c] = -1
        }

        roundCount++
        checkBoard()
    }

    private fun checkBoard() {
        if (checkWin())
            if (player1Turn) {
                win(1)
                winner = "X"
            } else {
                win(2)
                winner = "O"
            }
        else if (roundCount == boardSize * boardSize) {
            Toast.makeText(applicationContext, "Tie", Toast.LENGTH_SHORT).show()
            winner = "Tie"
        }
        else {
            player1Turn = !player1Turn
        }
    }

    private fun checkWin(): Boolean {
        var r: Int
        var c: Int
        var d1 = 0
        var d2 = 0

        for (i in score.indices) {
            r = 0
            c = 0
            for (j in score.indices) {
                if (score[i][j] != 0) {
                    if (j == i)
                        d1 += score[i][j]
                    if (j + i == score.size - 1)
                        d2 += score[i][j]
                    r += score[i][j]
                }
                if (score[j][i] != 0)
                    c += score[j][i]
                if (r == score.size || c == score.size || d1 == score.size || d2 == score.size) return true
                if (r == -score.size || c == -score.size || d1 == -score.size || d2 == -score.size) return true
            }
        }
        return false
    }

    private fun disableButtons() {
        val layoutButtons = threeBoardBinding.buttonsLayout.touchables

        for (v in layoutButtons)
            if (v is Button)
                v.isClickable = false
    }

    private fun win(p: Int) {
        disableButtons()
        Toast.makeText(applicationContext, "Player $p won!", Toast.LENGTH_SHORT).show()
    }


    fun end(v: View) {
        val main = Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, winner) }
        startActivity(main)
    }
}
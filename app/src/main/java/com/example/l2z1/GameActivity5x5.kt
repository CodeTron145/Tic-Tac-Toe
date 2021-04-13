package com.example.l2z1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.l2z1.databinding.FiveBoardBinding
import com.example.l2z1.databinding.ThreeBoardBinding

class GameActivity5x5 : AppCompatActivity() {

    private lateinit var fiveBoardBinding: FiveBoardBinding

    private var player1Turn: Boolean = true
    private var boardSize: Int = 5
    private var roundCount: Int = 0
    private var winner: String = ""

    var score: Array<Array<Int>> = Array(boardSize) { Array(boardSize) { 0 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fiveBoardBinding = FiveBoardBinding.inflate(layoutInflater)

        setContentView(fiveBoardBinding.root)
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
            winner = if (player1Turn) {
                win(1)
                "X"
            } else {
                win(2)
                "O"
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
        val layoutButtons = fiveBoardBinding.buttonsLayout.touchables

        for (v in layoutButtons)
            if (v is Button)
                v.isClickable = false
    }

    private fun win(p: Int) {
        disableButtons()
        Toast.makeText(applicationContext, "Player $p won!", Toast.LENGTH_SHORT).show()
    }


    fun end(v: View) {
        val main2 = Intent(this, MainActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, winner) }
        startActivity(main2)
    }
}
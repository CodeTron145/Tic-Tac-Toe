package com.example.l2z1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.Layout

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.l2z1.databinding.ActivityMainBinding
import com.example.l2z1.databinding.FiveBoardBinding
import com.example.l2z1.databinding.ThreeBoardBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var winner: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        winner = intent.getStringExtra(EXTRA_MESSAGE).toString()

        if (winner == "X" || winner == "O"){
            binding.infoView.text = "Last winner: $winner"
        } else
            binding.infoView.text = ""

        setContentView(binding.root)
    }

    fun createNewGame(view: View) {
        val game: Intent
        when (view.id) {
            binding.button3x3.id -> {
                game = Intent(this, GameActivity3x3::class.java)
                startActivity(game)
            }
            binding.button5x5.id -> {
                game = Intent(this, GameActivity5x5::class.java)
                startActivity(game)
            }
        }
    }
}

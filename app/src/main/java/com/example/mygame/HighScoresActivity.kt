package com.example.mygame

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.mygame.gameplay.Companion.HIGH_SCORE_KEY

class HighScoresActivity : AppCompatActivity() {
    private lateinit var highScoresTextView: TextView

    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        highScoresTextView = findViewById(R.id.highScoresTextView)
        prefs = getSharedPreferences(gameplay.PREFS_FILE_NAME, Context.MODE_PRIVATE)

        // Fetch and display high scores data
        val highScore = getHighestScore()
        if (highScore > 0) {
            highScoresTextView.text = "Highest Score: $highScore"
        } else {
            highScoresTextView.text = "No High Score Yet!"
        }


    }
    private fun getHighestScore(): Int {
        val prefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(gameplay.HIGH_SCORE_KEY, 0) // Retrieve the top score directly
    }
    companion object {
        const val PREFS_FILE_NAME = "GamePrefs"
    }
}
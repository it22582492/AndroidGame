package com.example.mygame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.util.Random

class gameplay : AppCompatActivity() {
    private lateinit var colorTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var timerTextView: TextView

    private lateinit var redButton: Button
    private lateinit var greenButton: Button
    private lateinit var blueButton: Button
    private lateinit var yellowButton: Button

    private lateinit var viewModel: GameViewModel
    private lateinit var prefs: SharedPreferences

    private val colorNames = arrayOf("Red", "Green", "Blue", "Yellow")
    private val textColors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
    private lateinit var timer: CountDownTimer
    private var timeLeftInMillis: Long = 20000
    private val INITIAL_TIME_LIMIT: Long = 20000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplay)

        redButton=findViewById(R.id.redButton)
        greenButton=findViewById(R.id.greenButton)
        colorTextView=findViewById(R.id.colorTextView)
        scoreTextView=findViewById(R.id.scoreTextView)
        blueButton=findViewById(R.id.blueButton)
        yellowButton=findViewById(R.id.yellowButton)
        timerTextView=findViewById(R.id.timerTextView)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        prefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

        redButton.setOnClickListener { checkColor("Red") }
        greenButton.setOnClickListener { checkColor("Green") }
        blueButton.setOnClickListener { checkColor("Blue") }
        yellowButton.setOnClickListener { checkColor("Yellow") }

        updateScore()
        updateColorText()
        startTimer()
    }
    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 2000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                // Update timer display if needed
                updateTimerDisplay()
            }

            override fun onFinish() {
                // Handle game over due to time limit reached
                handleGameOver()
            }
        }.start()
    }


    private fun checkColor(color: String) {
        val colorText = colorTextView.text.toString()
        if (colorText.equals(color, ignoreCase = true)) {
            viewModel.increaseScore()
            updateScore()
            updateColorText()
        } else {
            // Incorrect color tapped, handle accordingly
            handleIncorrectColor()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun updateScore() {
        val currentScore = viewModel.score
        scoreTextView.text = "Score: $currentScore"

        val highScore = getHighestScore()
        if (currentScore > highScore) {
            saveHighScore(currentScore)

            Toast.makeText(this, "New High Score!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateColorText() {
        // Update color text randomly
        // Generate a random index to select a color
        val randomIndex = Random().nextInt(colorNames.size)
        val colorName = colorNames[randomIndex]
        val textColor = textColors[randomIndex]

        // Update color text and set text color
        colorTextView.text = colorName
        colorTextView.setTextColor(textColor)
    }
    @SuppressLint("SetTextI18n")
    private fun updateTimerDisplay() {
        val secondsRemaining = (timeLeftInMillis / 1000).toInt()
        timerTextView.text = "Time: $secondsRemaining s"
    }
    private fun handleIncorrectColor() {
        // Display a toast or perform any other action
        Toast.makeText(this, "Incorrect Color! Try again.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        resetTimer()
        finish()
    }
    private fun handleGameOver() {
        // Handle game over due to time limit reached
        Toast.makeText(this, "Time's up! Game over.", Toast.LENGTH_SHORT).show()
        // Implement game over actions like resetting the game or navigating back to the main menu
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }
    private fun saveHighScore(score: Int) {
        val currentHighScore = getHighestScore()
        // Update the highest score if the new score is higher
        if (score > currentHighScore) {
            val editor = prefs.edit()
            editor.putInt(HIGH_SCORE_KEY, score)
            editor.apply()
        }
    }
    private fun getHighestScore(): Int {
        val prefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(HIGH_SCORE_KEY, 0) // Retrieve the top score directly
    }
    private fun resetTimer() {
        timer.cancel() // Cancel the current timer
        timeLeftInMillis = INITIAL_TIME_LIMIT // Reset the time limit to the initial value

    }
    companion object {
         const val PREFS_FILE_NAME = "GamePrefs"
         const val HIGH_SCORE_KEY = "HighScore"

    }
}
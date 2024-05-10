package com.example.mygame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var startGameButton: Button
    private lateinit var viewHighScoresButton: Button
    private lateinit var howtoplay: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startGameButton=findViewById(R.id.startGameButton)
        viewHighScoresButton=findViewById(R.id.viewHighScoresButton)
        howtoplay=findViewById(R.id.howtoplay)

        startGameButton.setOnClickListener {
            startActivity(Intent(this, gameplay::class.java))
        }

        viewHighScoresButton.setOnClickListener {

            val intent = Intent(this, HighScoresActivity::class.java)
            startActivity(intent)

        }
        howtoplay.setOnClickListener {

            val intent = Intent(this, About::class.java)
            startActivity(intent)

        }
    }

}
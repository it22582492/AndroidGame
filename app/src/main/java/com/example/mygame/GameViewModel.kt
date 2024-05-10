package com.example.mygame

import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel(){
    var score:Int = 0

    fun increaseScore() {
        score++
    }
    fun resetScore() {
        score = 0
    }
}
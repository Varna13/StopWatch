package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isRunning: Boolean = false
    private var timerSeconds: Int = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object: Runnable{
        override fun run() {
            timerSeconds++
            val hours = timerSeconds / 3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.tvTimer.text = time

            handler.postDelayed(this, 1000)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener { startTimer() }
        binding.btnStop.setOnClickListener { stopTimer() }
        binding.btnReset.setOnClickListener { resetTimer() }
    }

    private fun startTimer(){
        if (!isRunning){
            handler.postDelayed(runnable, 1000)
            isRunning = true

            binding.btnStart.isEnabled = false
            binding.btnStop.isEnabled = true
            binding.btnReset.isEnabled = true
        }
    }

    private fun stopTimer(){
        if (isRunning){
            handler.removeCallbacks(runnable)
            isRunning = false

            binding.btnStart.isEnabled = true
            binding.btnStart.text = "Resume"
            binding.btnStop.isEnabled = false
            binding.btnReset.isEnabled = true
        }
    }

    private fun resetTimer(){

        stopTimer()
        timerSeconds = 0
        binding.tvTimer.text = "00:00:00"

            binding.btnStart.isEnabled = true
            binding.btnStart.text = "start"
            binding.btnReset.isEnabled = false

    }
}
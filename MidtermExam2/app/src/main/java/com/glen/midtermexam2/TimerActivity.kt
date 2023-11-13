
package com.glen.midtermexam2

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TimerActivity : BaseActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var timerEditText: EditText
    private lateinit var startButton: Button
    private lateinit var mediaPlayer: MediaPlayer

    private var countdownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        supportActionBar?.title = "Timer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        timerTextView = findViewById(R.id.timerTextView)
        timerEditText = findViewById(R.id.timerEditText)
        startButton = findViewById(R.id.startButton)

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

        startButton.setOnClickListener {
            if (isTimerRunning) {
                stopTimer()
            } else {
                startTimer()
            }
        }
    }

    private fun startTimer() {
        val timeInMillis = timerEditText.text.toString().toLong() * 1000
        countdownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                updateTimerText(0)
                playSound()
                isTimerRunning = false
                startButton.text = "Start"
            }
        }

        countdownTimer?.start()
        isTimerRunning = true
        startButton.text = "Stop"
    }

    private fun stopTimer() {
        countdownTimer?.cancel()
        isTimerRunning = false
        startButton.text = "Start"
        updateTimerText(0)
    }

    private fun updateTimerText(timeInMillis: Long) {
        val seconds = timeInMillis / 1000
        timerTextView.text = getString(R.string.timer_format, seconds)
    }

    private fun playSound() {
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        countdownTimer?.cancel()
    }
}

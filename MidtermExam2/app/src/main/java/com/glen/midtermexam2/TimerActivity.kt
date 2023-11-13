package com.glen.midtermexam2

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TimerActivity : BaseActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var hoursPicker: NumberPicker
    private lateinit var minutesPicker: NumberPicker
    private lateinit var secondsPicker: NumberPicker
    private lateinit var startButton: Button
    private lateinit var mediaPlayer: MediaPlayer

    private var countdownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var timeRemaining: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        supportActionBar?.title = "Timer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        timerTextView = findViewById(R.id.timerTextView)
        hoursPicker = findViewById(R.id.hoursPicker)
        minutesPicker = findViewById(R.id.minutesPicker)
        secondsPicker = findViewById(R.id.secondsPicker)
        startButton = findViewById(R.id.startButton)

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

        setupPickers()

        startButton.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }
    }

    private fun setupPickers() {
        hoursPicker.minValue = 0
        hoursPicker.maxValue = 23

        minutesPicker.minValue = 0
        minutesPicker.maxValue = 59

        secondsPicker.minValue = 0
        secondsPicker.maxValue = 59
    }

    private fun startTimer() {
        val hoursInMillis = hoursPicker.value.toLong() * 60 * 60 * 1000
        val minutesInMillis = minutesPicker.value.toLong() * 60 * 1000
        val secondsInMillis = secondsPicker.value.toLong() * 1000

        val timeInMillis = hoursInMillis + minutesInMillis + secondsInMillis

        countdownTimer = object : CountDownTimer(if (timeRemaining > 0) timeRemaining else timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
                timeRemaining = millisUntilFinished
            }

            override fun onFinish() {
                updateTimerText(0)
                playSound()
                isTimerRunning = false
                startButton.text = "Start"
                timeRemaining = 0
            }
        }

        countdownTimer?.start()
        isTimerRunning = true
        startButton.text = "Pause"
    }


    private fun pauseTimer() {
        countdownTimer?.cancel()
        isTimerRunning = false
        startButton.text = "Resume"
    }

    private fun stopTimer() {
        countdownTimer?.cancel()
        isTimerRunning = false
        startButton.text = "Start"
        updateTimerText(0)
        timeRemaining = 0
    }

    private fun updateTimerText(timeInMillis: Long) {
        val hours = timeInMillis / (60 * 60 * 1000)
        val minutes = (timeInMillis % (60 * 60 * 1000)) / (60 * 1000)
        val seconds = (timeInMillis % (60 * 1000)) / 1000

        timerTextView.text = getString(R.string.timer_format, hours, minutes, seconds)
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

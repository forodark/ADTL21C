package com.glen.midtermexam2

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class TimerActivity : BaseActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var hoursPicker: NumberPicker
    private lateinit var minutesPicker: NumberPicker
    private lateinit var secondsPicker: NumberPicker
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var pauseButton: Button
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
        stopButton = findViewById(R.id.stopButton)
        pauseButton = findViewById(R.id.pauseButton)

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

        setupPickers()

        startButton.setOnClickListener {
            startTimer()
        }

        pauseButton.setOnClickListener {
            pauseTimer()
        }

        stopButton.setOnClickListener {
            stopTimer()
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

        if (hoursPicker.value == 0 && minutesPicker.value == 0 && secondsPicker.value == 0) {
            // You can display a message or take appropriate action when selectors are all 0
            // For example, show a Toast message
            Toast.makeText(this, "Cannot start timer at 0s", Toast.LENGTH_SHORT).show()
            return
        }

        val hoursInMillis = hoursPicker.value.toLong() * 60 * 60 * 1000
        val minutesInMillis = minutesPicker.value.toLong() * 60 * 1000
        val secondsInMillis = secondsPicker.value.toLong() * 1000

        val timeInMillis = hoursInMillis + minutesInMillis + secondsInMillis

        stopButton.visibility = View.VISIBLE
        startButton.visibility = View.GONE
        pauseButton.visibility = View.VISIBLE

        countdownTimer = object : CountDownTimer(if (timeRemaining > 0) timeRemaining else timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
                timeRemaining = millisUntilFinished
            }

            override fun onFinish() {
                updateTimerText(0)
                playSound()
                isTimerRunning = false
                    startButton.visibility = View.VISIBLE
                    pauseButton.visibility = View.GONE
//                startButton.setCompoundDrawablesWithIntrinsicBounds(startIcon, null, null, null)
//                startButton.text = "Start"
                timeRemaining = 0
                setPickersEnabled(true) // Enable pickers when the timer finishes
            }
        }

        countdownTimer?.start()
        isTimerRunning = true
//        startButton.setCompoundDrawablesWithIntrinsicBounds(pauseIcon, null, null, null)
//        startButton.text = "Pause"
        setPickersEnabled(false) // Disable pickers when the timer starts
    }


    private fun pauseTimer() {
        countdownTimer?.cancel()
        isTimerRunning = false
        startButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
//        startButton.setCompoundDrawablesWithIntrinsicBounds(startIcon, null, null, null)
//        startButton.text = "Resume"
    }

    private fun stopTimer() {
        countdownTimer?.cancel()
        isTimerRunning = false
        stopButton.visibility = View.INVISIBLE
        startButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
//        startButton.setCompoundDrawablesWithIntrinsicBounds(startIcon, null, null, null)
//        startButton.text = "Start"
        updateTimerText(0)
        timeRemaining = 0
        setPickersEnabled(true) // Enable pickers when the timer is stopped
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

    private fun setPickersEnabled(enabled: Boolean) {
        hoursPicker.isEnabled = enabled
        minutesPicker.isEnabled = enabled
        secondsPicker.isEnabled = enabled
    }

}

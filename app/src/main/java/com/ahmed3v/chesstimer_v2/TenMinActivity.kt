package com.ahmed3v.chesstimer_v2

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import com.ahmed3v.chesstimer_v2.databinding.ActivityMainBinding.inflate

class TenMinActivity : AppCompatActivity() {

    private var firstCounter: Int = 600
    private var secondCounter: Int = 600

    private lateinit var firstTimer: CountDownTimer
    private lateinit var secondTimer: CountDownTimer

    private lateinit var firstPlayerButton: Button
    private lateinit var secondPlayerButton: Button

    private lateinit var firstPlayerTextCounter: TextView
    private lateinit var secondPlayerTextCounter: TextView

    private lateinit var restartButton: Button
    private lateinit var pauseButton: Button


    private lateinit var firstPlayerButtonSound: MediaPlayer
    private lateinit var secondPlayerButtonSound: MediaPlayer
    private lateinit var clockFinished: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clock_activity)

        firstPlayerButton = findViewById(R.id.first_player_button)
        secondPlayerButton = findViewById(R.id.second_player_button)
        pauseButton = findViewById(R.id.pause_button)
        restartButton = findViewById(R.id.restart_button)

        firstPlayerTextCounter = findViewById(R.id.first_player_text_counter)
        secondPlayerTextCounter = findViewById(R.id.second_player_text_counter)

        firstPlayerButtonSound = MediaPlayer.create(applicationContext, R.raw.chess_clock_switch1)
        secondPlayerButtonSound = MediaPlayer.create(applicationContext, R.raw.chess_clock_switch2)
        clockFinished = MediaPlayer.create(applicationContext, R.raw.chess_clock_time_ended)

        restartButton.isEnabled = false
        pauseButton.isEnabled = false

        //set up the first player
        firstPlayerTextCounter.setText(R.string.ten_min_timer)
        firstPlayerTextCounter.setTextColor(resources.getColor(R.color.black))
        firstPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))

        //set up the second player
        secondPlayerTextCounter.setText(R.string.ten_min_timer)
        secondPlayerTextCounter.setTextColor(resources.getColor(R.color.black))
        secondPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))


        secondPlayerButton.setOnClickListener {

            secondPlayerButton.isEnabled = false
            restartButton.isEnabled = true
            pauseButton.isEnabled = true

            reversTimerTwo(secondCounter, firstPlayerTextCounter)

            secondPlayerButtonSound.start()
        }

        firstPlayerButton.setOnClickListener {

            firstPlayerButton.isEnabled = false
            restartButton.isEnabled = true
            pauseButton.isEnabled = true

            reversFirstTimer(firstCounter, secondPlayerTextCounter)

            firstPlayerButtonSound.start()
        }

        pauseButton.setOnClickListener {

                v ->
            updateUIState(v)

            //when the [secondPlayerButton] gets Enabled the timer paused
            if (secondPlayerButton.isEnabled)
                firstTimer.cancel()

            //when the [firstPlayerButton] gets Enabled the timer paused
            else if (firstPlayerButton.isEnabled)
                secondTimer.cancel()
        }

        //set up the alert dialog
        val builder = AlertDialog.Builder(this)

        val inflater = LayoutInflater.from(this)
        val view: View = inflater.inflate(R.layout.reset_dialog, null)

        val resetButton = view.findViewById<Button>(R.id.reset_dialog_button)
        val cancelButton = view.findViewById<Button>(R.id.cancel_dialog_button)

        builder.setView(view)
        val dialog = builder.create()

        resetButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cancelButton.setOnClickListener { dialog.dismiss() }


        restartButton.setOnClickListener {

                v ->
            updateUIState(v)

            //when the [secondPlayerButton] gets Enabled the timer restart
            if (secondPlayerButton.isEnabled)
                firstTimer.cancel()

            //when the [firstPlayerButton] gets Enabled the timer restart
            else if (firstPlayerButton.isEnabled)
                secondTimer.cancel()



            dialog.show()
        }

    }

    //function to set up and control the first timer
    private fun reversFirstTimer(Seconds: Int, secondPlayerTextCounter: TextView) {

        secondPlayerButton.isEnabled = true

        firstTimer = object : CountDownTimer((Seconds * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {

                if (firstPlayerButton.isEnabled) {

                    secondPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))
                    secondPlayerTextCounter.setTextColor(resources.getColor(R.color.black))
                    cancel()

                } else {

                    var seconds = (millisUntilFinished / 1000).toInt()
                    firstCounter = seconds
                    val minutes = seconds / 60
                    seconds %= 60

                    val sec = String.format("%02d", seconds)
                    val min = String.format("%02d", minutes)
                    val time = "$min:$sec"

                    secondPlayerButton.setBackgroundColor(resources.getColor(R.color.purple_dark))
                    secondPlayerTextCounter.setTextColor(Color.WHITE)
                    secondPlayerTextCounter.text = time
                }
            }

            override fun onFinish() {
                secondPlayerTextCounter.text = getString(R.string.stop_the_timer)
                clockFinished.start()
                secondPlayerButton.setBackgroundColor(resources.getColor(R.color.finish_button_background))
                secondPlayerButton.isEnabled = false
            }
        }
        firstTimer.start()
    }

    //function to set up and control the second timer
    private fun reversTimerTwo(Seconds: Int, playerOneTextCounter: TextView) {

        firstPlayerButton.isEnabled = true

        secondTimer = object : CountDownTimer((Seconds * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {

                if (secondPlayerButton.isEnabled) {

                    firstPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))
                    playerOneTextCounter.setTextColor(resources.getColor(R.color.black))
                    cancel()

                } else {

                    var seconds = (millisUntilFinished / 1000).toInt()
                    secondCounter = seconds
                    val minutes = seconds / 60
                    seconds %= 60

                    val sec = String.format("%02d", seconds)
                    val min = String.format("%02d", minutes)
                    val time = "$min:$sec"

                    firstPlayerButton.setBackgroundColor(resources.getColor(R.color.purple_dark))
                    playerOneTextCounter.setTextColor(Color.WHITE)
                    playerOneTextCounter.text = time
                }
            }

            override fun onFinish() {
                firstPlayerTextCounter.text = getString(R.string.stop_the_timer)
                clockFinished.start()
                firstPlayerButton.setBackgroundColor(resources.getColor(R.color.finish_button_background))
                firstPlayerButton.isEnabled = false
            }
        }
        secondTimer.start()
    }

    private fun updateUIState(v: View) {

        when (v.id) {

            R.id.pause_button -> {
                secondPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))
                secondPlayerTextCounter.setTextColor(resources.getColor(R.color.black))

                firstPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))
                firstPlayerTextCounter.setTextColor(resources.getColor(R.color.black))
            }

            R.id.restart_button -> {
                secondPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))
                secondPlayerTextCounter.setTextColor(resources.getColor(R.color.black))

                firstPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))
                firstPlayerTextCounter.setTextColor(resources.getColor(R.color.black))
            }
        }
    }
}
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
import com.ahmed3v.chesstimer_v2.databinding.ActivityOneMinBinding

class OneMinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOneMinBinding

    private var firstTimer: CountDownTimer? = null
    private var secondTimer:CountDownTimer? = null
    private var firstCounter: Int = 60
    private var secondCounter: Int = 60

    private var builder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var inflater: LayoutInflater? = null
    
    private val firstPlayerButtonSound: MediaPlayer? = null
    private val secondPlayerButtonSound: MediaPlayer? = null
    private val clockFinished: MediaPlayer? = null
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set up binding
        binding = ActivityOneMinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restartButton.setEnabled(false)
        binding.pauseButton.setEnabled(false)

        //set up the first player
        binding.firstPlayerTextCounter.setText(R.string.start_the_timer)
        binding.firstPlayerTextCounter.setTextColor(resources.getColor(R.color.black))
        binding.firstPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))

        //set up the second player
        binding.secondPlayerTextCounter.setText(R.string.start_the_timer)
        binding.secondPlayerTextCounter.setTextColor(resources.getColor(R.color.black))
        binding.secondPlayerButton.setBackgroundColor(resources.getColor(R.color.paused_button_background))


        binding.secondPlayerButton.setOnClickListener {

            binding.secondPlayerButton.setEnabled(false)
            binding.restartButton.setEnabled(true)
            binding.pauseButton.setEnabled(true)
            reversTimerTwo(secondCounter, binding.firstPlayerTextCounter)
            secondPlayerButtonSound!!.start()
        }

        binding.firstPlayerButton.setOnClickListener {

            binding.firstPlayerButton.setEnabled(false)
            binding.restartButton.setEnabled(true)
            binding.pauseButton.setEnabled(true)
            reversTimerOne(firstCounter, binding.secondPlayerTextCounter)
            firstPlayerButtonSound!!.start()
        }

        binding.pauseButton.setOnClickListener {

                v -> updateUIState(v)

            //when the [secondPlayerButton] gets Enabled the timer paused
            if (binding.secondPlayerButton.isEnabled())
                firstTimer!!.cancel()

            //when the [firstPlayerButton] gets Enabled the timer paused
            else if (binding.firstPlayerButton.isEnabled())
                secondTimer!!.cancel()
        }

        binding.restartButton.setOnClickListener {

                v -> updateUIState(v)

            //when the [secondPlayerButton] gets Enabled the timer restart
            if (binding.secondPlayerButton.isEnabled())
                firstTimer!!.cancel()

            //when the [firstPlayerButton] gets Enabled the timer restart
            else if (binding.firstPlayerButton.isEnabled())
                secondTimer!!.cancel()
        }

            //here we stopped editing !!!!!
            builder = AlertDialog.Builder(this)
            inflater = LayoutInflater.from(this)

            val view: View = inflater.inflate(R.layout.reset_dialog, null)
            val noButton = view.findViewById<Button>(R.id.noButton)
            val yesButton = view.findViewById<Button>(R.id.yesButton)

            builder.setView(view)
            dialog = builder.create()
            dialog.show()

            yesButton.setOnClickListener {
                val intent = Intent(this@OneMinActivity, MainActivity::class.java)
                startActivity(intent)
            }
            noButton.setOnClickListener { dialog.dismiss() }
        })

    }

    private fun reversTimerOne(Seconds: Int, playerTwoTextCounter: TextView) {
        playerTwoButton.setEnabled(true)
        timerOne = object : CountDownTimer((Seconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (playerOneButton.isEnabled()) {
                    playerTwoButton.setBackgroundDrawable(resources.getDrawable(R.drawable.paused_button_background))
                    playerTwoTextCounter.setTextColor(resources.getColor(R.color.paused_background_text_timer))
                    cancel()
                } else {
                    var seconds = (millisUntilFinished / 1000).toInt()
                    counterOne = seconds
                    val minutes = seconds / 60
                    seconds = seconds % 60
                    playerTwoButton.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button))
                    playerTwoTextCounter.setTextColor(Color.WHITE)
                    playerTwoTextCounter.text =
                        String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
                }
            }

            override fun onFinish() {
                playerTwoButton.setBackgroundDrawable(resources.getDrawable(R.drawable.finish_button_background))
                playerTwoButton.setText(R.string.stop_the_timer)
                clockFinished!!.start()
                playerTwoButton.setEnabled(false)
            }
        }
        timerOne.start()
    }

    private fun reversTimerTwo(Seconds: Int, playerOneTextCounter: TextView) {
        playerOneButton.setEnabled(true)
        timerTwo = object : CountDownTimer((Seconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (playerTwoButton.isEnabled()) {
                    playerOneButton.setBackgroundDrawable(resources.getDrawable(R.drawable.paused_button_background))
                    playerOneTextCounter.setTextColor(resources.getColor(R.color.paused_background_text_timer))
                    cancel()
                } else {
                    var seconds = (millisUntilFinished / 1000).toInt()
                    counterTwo = seconds
                    val minutes = seconds / 60
                    seconds = seconds % 60
                    playerOneButton.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button))
                    playerOneTextCounter.setTextColor(Color.WHITE)
                    playerOneTextCounter.text =
                        String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
                }
            }

            override fun onFinish() {
                playerOneButton.setBackgroundDrawable(resources.getDrawable(R.drawable.finish_button_background))
                playerTwoButton.setText(R.string.stop_the_timer)
                clockFinished!!.start()
                playerOneButton.setEnabled(false)
            }
        }
        timerTwo.start()
    }

    private fun updateUIState(v: View) {
        when (v.id) {
            R.id.pauseButton -> {
                playerTwoButton.setBackgroundDrawable(resources.getDrawable(R.drawable.paused_button_background))
                playerTwoTextCounter.setTextColor(resources.getColor(R.color.paused_background_text_timer))
                playerOneButton.setBackgroundDrawable(resources.getDrawable(R.drawable.paused_button_background))
                playerOneTextCounter.setTextColor(resources.getColor(R.color.paused_background_text_timer))
            }
            R.id.restartButton -> {
                playerTwoButton.setBackgroundDrawable(resources.getDrawable(R.drawable.paused_button_background))
                playerTwoTextCounter.setTextColor(resources.getColor(R.color.paused_background_text_timer))
                playerOneButton.setBackgroundDrawable(resources.getDrawable(R.drawable.paused_button_background))
                playerOneTextCounter.setTextColor(resources.getColor(R.color.paused_background_text_timer))
            }
        }
    }
    }
}
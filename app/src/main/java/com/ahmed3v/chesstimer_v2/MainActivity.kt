package com.ahmed3v.chesstimer_v2

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.ahmed3v.chesstimer_v2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.oneMinButton.setOnClickListener {

            val intent = Intent(this, OneMinActivity::class.java)
            startActivity(intent)
        }

        binding.fiveMinButton.setOnClickListener {

            val intent = Intent(this, FiveMinActivity::class.java)
            startActivity(intent)
        }

        binding.tenMinButton.setOnClickListener {

            val intent = Intent(this, TenMinActivity::class.java)
            startActivity(intent)
        }

//        binding.fifteenMinButton.setOnClickListener {
//
//            val intent = Intent(this, FifteenMinActivity::class.java)
//            startActivity(intent)
//        }
//
//
//        binding.twentyMinButton.setOnClickListener {
//
//            val intent = Intent(this, TwentyMinActivity::class.java)
//            startActivity(intent)
//        }
//
//
//        binding.thirtyMinButton.setOnClickListener {
//
//            val intent = Intent(this, ThirtyMinActivity::class.java)
//            startActivity(intent)
//        }
    }
}
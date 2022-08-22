package com.ahmed3v.chesstimer_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmed3v.chesstimer_v2.databinding.ActivityMainBinding
import com.ahmed3v.chesstimer_v2.databinding.ActivityOneMinBinding

class OneMinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOneMinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOneMinBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
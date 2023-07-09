package com.mad.assignment1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mad.assignment1.R
import com.mad.assignment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var counter = 0
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.counterTextView.text = resources.getString(R.string.click_counter,counter)
    }

    override fun onResume() {
        super.onResume()
        binding.clickMeButton.setOnClickListener{
            counter++
            binding.counterTextView.text = resources.getString(R.string.click_counter,counter)
        }
    }

    override fun onPause() {
        super.onPause()
        counter = 0
        binding.counterTextView.text = counter.toString()
    }

}
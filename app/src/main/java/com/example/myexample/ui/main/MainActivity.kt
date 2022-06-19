package com.example.myexample.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.myexample.R
import com.example.myexample.databinding.ActivityMainBinding
import com.example.myexample.ui.animationprofile.view.AnimationFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.showFragmentButton.setOnClickListener {
            val fragment = AnimationFragment()
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.bottom_in,
                    R.anim.bottom_out,
                    R.anim.bottom_in,
                    R.anim.bottom_out
                )
                .add(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}

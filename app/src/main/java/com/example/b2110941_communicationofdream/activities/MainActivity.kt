package com.example.b2110941_communicationofdream.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.b2110941_communicationofdream.R
import com.example.b2110941_communicationofdream.databinding.ActivityMainBinding
import com.example.b2110941_communicationofdream.fragments.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout,LoginFragment())
            .commit()
    }
}
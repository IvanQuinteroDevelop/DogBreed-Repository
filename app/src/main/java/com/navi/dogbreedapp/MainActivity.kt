package com.navi.dogbreedapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.navi.dogbreedapp.databinding.ActivityMainBinding
import com.navi.dogbreedapp.doglist.DogListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        manageClicks(binding)
    }

    private fun manageClicks(binding: ActivityMainBinding) {
        binding.apply {
            dogListButton.setOnClickListener {
                goToActivity(DogListActivity())
            }
        }
    }

    private fun goToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}
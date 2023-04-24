package com.navi.dogbreedapp.dogdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.navi.dogbreedapp.DogModel
import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DOG_KEY = "dog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog: DogModel? = intent.extras?.getParcelable(DOG_KEY)

        binding.apply {
            this.dog = dog
            dogAge.text = getString(R.string.dog_expectancy_format, dog?.lifeExpectancy)
            dogIndex.text = getString(R.string.dog_index_format, dog?.index.toString())
            adoptButton.setOnClickListener { finish() }
        }
    }
}
package com.navi.dogbreedapp.dogdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.navi.dogbreedapp.model.DogModel
import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.databinding.ActivityDogDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            dogImage.load(dog?.imageUrl)
            adoptButton.setOnClickListener { finish() }
        }
    }
}
 package com.navi.dogbreedapp.doglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.navi.dogbreedapp.DogModel
import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.databinding.ActivityDogListBinding

 class DogListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dog_list)

        val dogList = getFakeDogs()
        val recycler = binding.dogRecycler
    }

    private fun getFakeDogs(): MutableList<DogModel> {
        val dogList = mutableListOf<DogModel>()
        dogList.add(
            DogModel(
                1, 1, "Chihuahua", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            DogModel(
                2, 1, "Labrador", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            DogModel(
                3, 1, "Retriever", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            DogModel(
                4, 1, "San Bernardo", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            DogModel(
                5, 1, "Husky", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            DogModel(
                6, 1, "Xoloscuincle", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        return dogList
    }
}
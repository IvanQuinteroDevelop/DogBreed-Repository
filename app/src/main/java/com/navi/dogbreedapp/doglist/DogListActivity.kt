 package com.navi.dogbreedapp.doglist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.navi.dogbreedapp.databinding.ActivityDogListBinding

 class DogListActivity : AppCompatActivity() {

     private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.dogRecycler
        recycler.layoutManager = LinearLayoutManager(this)
        val dogAdapter = DogAdapter()

        recycler.adapter = dogAdapter
        dogListViewModel.dogList.observe(this) {dogList ->
            dogAdapter.submitList(dogList)
        }
    }
}
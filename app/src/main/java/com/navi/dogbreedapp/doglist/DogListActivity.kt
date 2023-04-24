 package com.navi.dogbreedapp.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.navi.dogbreedapp.databinding.ActivityDogListBinding
import com.navi.dogbreedapp.dogdetail.DogDetailActivity
import com.navi.dogbreedapp.dogdetail.DogDetailActivity.Companion.DOG_KEY

 class DogListActivity : AppCompatActivity() {

     private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.dogRecycler
        recycler.layoutManager = LinearLayoutManager(this)

        val dogAdapter = DogAdapter {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        recycler.adapter = dogAdapter
        dogListViewModel.dogList.observe(this) {dogList ->
            dogAdapter.submitList(dogList)
        }
    }
}
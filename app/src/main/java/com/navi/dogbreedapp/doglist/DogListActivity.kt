package com.navi.dogbreedapp.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
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

        val loader = binding.loader

        val dogAdapter = DogAdapter {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        recycler.adapter = dogAdapter
        dogListViewModel.dogList.observe(this) { dogList ->
            dogAdapter.submitList(dogList)
        }
        dogListViewModel.status.observe(this) { status ->
            when (status) {
                ApiResponseStatus.LOADING -> {
                    loader.visibility = View.VISIBLE
                }
                ApiResponseStatus.SUCCESS -> {
                    loader.visibility = View.GONE
                }
                ApiResponseStatus.ERROR -> {
                    loader.visibility = View.GONE
                    Toast.makeText(this, "Error to download data", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show()
                    loader.visibility = View.GONE
                }
            }
        }
    }
}
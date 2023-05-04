package com.navi.dogbreedapp.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.databinding.ActivityDogListBinding
import com.navi.dogbreedapp.dogdetail.DogDetailActivity
import com.navi.dogbreedapp.dogdetail.DogDetailActivity.Companion.DOG_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.dogRecycler
        recycler.layoutManager = GridLayoutManager(this, 3)

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
                is ApiResponseStatus.Loading -> loader.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loader.visibility = View.GONE
                is ApiResponseStatus.Error -> {
                    loader.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
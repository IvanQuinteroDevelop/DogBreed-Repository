package com.navi.dogbreedapp.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.navi.dogbreedapp.dogdetail.DogDetailComposeActivity
import com.navi.dogbreedapp.doglist.ui.theme.DogBreedAppTheme
import com.navi.dogbreedapp.model.DogModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListComposeActivity : ComponentActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()
    private var dogList: MutableList<DogModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    dogListViewModel.dogList.observe(this) { list ->
                        dogList = list as MutableList<DogModel>
                    }
                    DogListScreen(dogs = dogList, dogListViewModel.status.value) {
                        val intent = Intent(this, DogDetailComposeActivity::class.java)
                        intent.putExtra(DogDetailComposeActivity.DOG_KEY, it)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
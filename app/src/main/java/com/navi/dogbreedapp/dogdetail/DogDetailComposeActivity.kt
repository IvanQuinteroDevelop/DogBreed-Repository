package com.navi.dogbreedapp.dogdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.navi.dogbreedapp.dogdetail.ui.theme.DogBreedAppTheme
import com.navi.dogbreedapp.model.DogModel

class DogDetailComposeActivity : ComponentActivity() {

    companion object {
        const val DOG_KEY = "dog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog: DogModel? = intent.extras?.getParcelable(DOG_KEY)
        setContent {
            DogBreedAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DogDetailScreen(dog = dog!!)
                }
            }
        }
    }
}

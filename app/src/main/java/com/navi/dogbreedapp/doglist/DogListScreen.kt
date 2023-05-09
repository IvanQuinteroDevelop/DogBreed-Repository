package com.navi.dogbreedapp.doglist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.navi.dogbreedapp.api.responses.ApiResponseStatus
import com.navi.dogbreedapp.model.DogModel

@Composable
fun DogListScreen(
    dogs: List<DogModel>,
    status: ApiResponseStatus<Any>? = null,
    onDogClicked: (DogModel) -> Unit
) {

    if (status is ApiResponseStatus.Loading) {
        LoadingScreen()
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(dogs) { dog ->
                Column(
                    Modifier
                        .padding(12.dp)
                        .clickable { onDogClicked(dog) }) {
                    Image(
                        painter = rememberAsyncImagePainter(dog.imageUrl),
                        contentDescription = dog.name,
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .height(170.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = dog.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


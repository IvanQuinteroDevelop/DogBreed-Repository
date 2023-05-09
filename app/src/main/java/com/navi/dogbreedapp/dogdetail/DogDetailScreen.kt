package com.navi.dogbreedapp.dogdetail

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.model.DogModel

@Composable
fun DogDetailScreen(dog: DogModel) {
    val activity = LocalContext.current as Activity
    Box(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = dog.name,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp)
        )

        Body(Modifier.align(Alignment.Center), dog)
        FloatingActionButton(
            onClick = { activity.finish() },
            content = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check button"
                )
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun Body(modifier: Modifier, dog: DogModel) {
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(dog.imageUrl),
            contentDescription = dog.name,
            modifier = Modifier
                .padding(bottom = 92.dp)
                .align(Alignment.TopCenter)
                .zIndex(1f)
        )
        Card(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(top = 92.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            content = {
                Column(
                    Modifier
                        .padding(top = 60.dp, start = 18.dp, end = 18.dp, bottom = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.dog_expectancy_format, dog.lifeExpectancy),
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = dog.temperament,
                        fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column() {
                            Text(
                                text = "Weight",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )

                            Text(
                                text = stringResource(
                                    id = R.string.dog_male_format,
                                    dog.weightMale
                                ), fontSize = 13.sp
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.dog_female_format,
                                    dog.weightFemale
                                ), fontSize = 13.sp
                            )
                        }
                        Divider(
                            Modifier
                                .height(58.dp)
                                .width(1.dp)
                        )
                        Column() {
                            Text(
                                text = "Height",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.dog_male_format,
                                    dog.heightMale
                                ) + " cm", fontSize = 13.sp
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.dog_female_format,
                                    dog.heightFemale
                                ) + " cm", fontSize = 13.sp
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.dog_index_format, dog.index),
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            })
    }
}
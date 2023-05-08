package com.navi.dogbreedapp.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.navi.dogbreedapp.R

@Preview(showSystemUi = true)
@Composable
fun MainScreen() {
    Box(Modifier.fillMaxSize()) {
        Row(Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(bottom = 32.dp), horizontalArrangement = Arrangement.SpaceAround) {
            FloatingActionButton(onClick = {}, content = {
                Icon(imageVector = Icons.Default.List, contentDescription = "List Button")
            })
            FloatingActionButton(onClick = {}, content = {
                Icon( painter = painterResource(R.drawable.ic_camera), contentDescription = "Camera Button")
            })
        }
    }
}
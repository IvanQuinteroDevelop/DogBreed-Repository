package com.navi.dogbreedapp.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.navi.dogbreedapp.R
import com.navi.dogbreedapp.dogdetail.DogDetailComposeActivity
import com.navi.dogbreedapp.doglist.DogListComposeActivity
import com.navi.dogbreedapp.main.ui.theme.DogBreedAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private val mainViewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                mainViewModel.acceptPermission()
            } else Toast.makeText(
                this,
                getString(R.string.you_need_camera_permission),
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
        requestCameraPermission()
        setContent {
            DogBreedAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    cameraExecutor = Executors.newSingleThreadExecutor()
                    CameraView(
                        executor = cameraExecutor,
                        mainViewModel = mainViewModel,
                        onListButton = { goToActivity(DogListComposeActivity()) },
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) cameraExecutor.shutdown()
    }

    private fun observeLiveData() {

        mainViewModel.dog.observe(this) { dog ->
            if (dog != null) {
                val extra = Bundle()
                extra.putParcelable(DogDetailComposeActivity.DOG_KEY, dog)
                goToActivity(DogDetailComposeActivity(), extra)
            }
        }
    }


    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                mainViewModel.acceptPermission()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.title_dialog_camera_permission))
                    .setMessage(getString(R.string.message_dialog_camera_permission))
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requestPermissionLauncher.launch(
                            android.Manifest.permission.CAMERA
                        )
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun goToActivity(activity: Activity, extra: Bundle? = null) {
        val intent = Intent(this, activity::class.java)
        if (extra != null) intent.putExtras(extra)
        startActivity(intent)
    }

}

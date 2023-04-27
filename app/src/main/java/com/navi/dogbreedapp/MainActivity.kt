package com.navi.dogbreedapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.navi.dogbreedapp.databinding.ActivityMainBinding
import com.navi.dogbreedapp.doglist.DogListActivity

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // nothing to do
            } else Toast.makeText(
                this,
                getString(R.string.you_need_camera_permission),
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        manageClicks(binding)
        requestCameraPermission()
    }

    private fun manageClicks(binding: ActivityMainBinding) {
        binding.apply {
            dogListButton.setOnClickListener {
                goToActivity(DogListActivity())
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // nothing to do
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
                    .setNegativeButton(android.R.string.cancel) {_, _ -> }
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun goToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}
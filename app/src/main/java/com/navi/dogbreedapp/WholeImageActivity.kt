package com.navi.dogbreedapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.navi.dogbreedapp.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWholeImageBinding

    companion object {
        const val IMAGE_URI_KEY = "image_uri"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.extras?.getString(IMAGE_URI_KEY) ?: ""
        val uriPath = Uri.parse(imageUri).path

        if (uriPath.isNullOrEmpty()) {
            Toast.makeText(this, "Error showing image, no uri passed", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.wholeImageView.load(File(uriPath))
    }
}
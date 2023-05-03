package com.navi.dogbreedapp.utils

import android.annotation.SuppressLint
import android.graphics.*
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream

object Utils {

    @SuppressLint("UnsafeOptInUsageError")
    fun convertImageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {

        val image = imageProxy.image ?: return null

        val yBuffer = image.planes[0].buffer // Y
        val uBuffer = image.planes[1].buffer // U
        val vBuffer = image.planes[2].buffer // V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        uBuffer.get(nv21, ySize + vSize, uSize)
        vBuffer.get(nv21, ySize, vSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}
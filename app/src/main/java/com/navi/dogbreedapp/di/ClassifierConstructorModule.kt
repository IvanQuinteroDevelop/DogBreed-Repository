package com.navi.dogbreedapp.di

import android.content.Context
import com.navi.dogbreedapp.utils.LABEL_PATH
import com.navi.dogbreedapp.utils.MODEL_PATH
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer

@Module
@InstallIn(SingletonComponent::class)
object ClassifierConstructorModule {

    @Provides
    fun providesClassifierModel(@ApplicationContext context: Context): MappedByteBuffer = FileUtil.loadMappedFile(context, MODEL_PATH)

    @Provides
    fun providesClassifierLabel(@ApplicationContext context: Context): List<String> = FileUtil.loadLabels(context, LABEL_PATH)
}
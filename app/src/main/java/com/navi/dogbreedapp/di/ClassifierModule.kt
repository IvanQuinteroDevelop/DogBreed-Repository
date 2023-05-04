package com.navi.dogbreedapp.di

import com.navi.dogbreedapp.interfaces.ClassifierTasks
import com.navi.dogbreedapp.machinelearning.ClassifierRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierModule {

    @Binds
    abstract fun bindClassifierTasks(classifierRepository: ClassifierRepository): ClassifierTasks
}
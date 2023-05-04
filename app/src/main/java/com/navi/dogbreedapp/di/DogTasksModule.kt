package com.navi.dogbreedapp.di

import com.navi.dogbreedapp.doglist.DogRepository
import com.navi.dogbreedapp.interfaces.DogTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class )
abstract class DogTasksModule {

    @Binds
    abstract fun bindDogRepositoryTasks(dogRepository: DogRepository ): DogTasks
}
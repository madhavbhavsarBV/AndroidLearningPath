package com.base.hilt.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

//    @Provides
//    @ViewModelScoped
//    fun authRepository(apolloClient: ApolloClient) = AuthRepository(apolloClient)


}
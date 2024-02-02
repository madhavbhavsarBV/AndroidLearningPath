package com.base.hilt.di

import com.apollographql.apollo3.ApolloClient
import com.base.hilt.domain.repository.AuthRepository
import com.base.hilt.network.ApiInterface
import com.base.hilt.network.apolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

//    @Provides
//    @ViewModelScoped
//    fun authRepository(apolloClient: ApolloClient) = AuthRepository(apolloClient)


}
package com.base.hilt.di

import com.apollographql.apollo3.ApolloClient
import com.base.hilt.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
//    @Provides
//    @ViewModelScoped
//    fun provideHomeRepository(
//        apiInterface: ApiInterface,
//        chaInterface: ChatApiInterface,
//        apolloClient: ApolloClient
//    ) = UserRepository(apiInterface, chaInterface, apolloClient)
}
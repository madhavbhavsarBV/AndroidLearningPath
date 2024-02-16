package com.base.hilt.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ConfigurationQuery
import com.base.hilt.ForgotPasswordMutation
import com.base.hilt.LoginMutation
import com.base.hilt.LogoutMutation
import com.base.hilt.ResendSmsOtpMutation
import com.base.hilt.SignupMutation
import com.base.hilt.UserDataQuery
import com.base.hilt.VerifySmsOtpMutation
import com.base.hilt.base.BaseRepository
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.model.ConfigInput
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ForgotPasswordInput
import com.base.hilt.type.LoginInput
import com.base.hilt.type.OtpInput
import com.base.hilt.type.ResendSmsOtpInput
import com.base.hilt.type.SignUpInput
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton


//@Singleton
class AuthRepository @Inject constructor (
    val apolloClient: ApolloClient
) :BaseRepository(){

    suspend fun onLoginApi(input: LoginInput): ResponseHandler<ApolloResponse<LoginMutation.Data>> {
        return graphQlApiCall {
            apolloClient.mutation(LoginMutation(input)).execute()
        }
    }

    suspend fun onForgotPasswordApi(input: ForgotPasswordInput): ResponseHandler<ApolloResponse<ForgotPasswordMutation.Data>> {
        return graphQlApiCall {
            apolloClient.mutation(ForgotPasswordMutation(input)).execute()
        }
    }
    suspend fun onSignUpApi(input: SignUpInput): ResponseHandler<ApolloResponse<SignupMutation.Data>> {
        return graphQlApiCall {
            apolloClient.mutation(SignupMutation(input)).execute()
        }
    }
    suspend fun onVerifyOtpApi(input: OtpInput): ResponseHandler<ApolloResponse<VerifySmsOtpMutation.Data>> {
        return graphQlApiCall {
            apolloClient.mutation(VerifySmsOtpMutation(input)).execute()
        }
    }
    suspend fun onResendOtp(input: ResendSmsOtpInput): ResponseHandler<ApolloResponse<ResendSmsOtpMutation.Data>> {
        return graphQlApiCall {
            apolloClient.mutation(ResendSmsOtpMutation(input)).execute()
        }
    }

    suspend fun onConfigApi(input: ConfigInput): ResponseHandler<ApolloResponse<ConfigurationQuery.Data>> {
        return graphQlApiCall {
            apolloClient.query(ConfigurationQuery(device_type=input.device_type, version_code =input.version_code, app_version =input.app_version)).execute()
        }
    }


    suspend fun onLogoutApi(): ResponseHandler<ApolloResponse<LogoutMutation.Data>> {
        return graphQlApiCall {
            apolloClient.mutation(LogoutMutation()).execute()
        }
    }
    suspend fun userData(): ResponseHandler<ApolloResponse<UserDataQuery.Data>> {
        return graphQlApiCall {
            apolloClient.query(UserDataQuery()).execute()
        }
    }

}
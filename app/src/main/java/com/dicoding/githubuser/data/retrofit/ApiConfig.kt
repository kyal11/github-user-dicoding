package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class ApiConfig {

    companion object {
//        private const val BASE_URL = "https://api.github.com/"
        private const val BASE_URL = BuildConfig.BASE_URL
        private const val apiToken = BuildConfig.API_KEY

        fun getApiService() :ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val tokenInterceptor = Interceptor { chain ->
                val requestWithToken: Request = chain.request().newBuilder()
                    .header("Authorization", "Bearer $apiToken")
                    .build()

                chain.proceed(requestWithToken)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(tokenInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return  retrofit.create(ApiService::class.java)
        }
    }

}
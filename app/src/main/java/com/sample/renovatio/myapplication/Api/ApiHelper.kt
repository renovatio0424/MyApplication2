package com.sample.renovatio.myapplication.Api

import android.content.Context
import com.sample.renovatio.myapplication.Api.ApiHelper.baseURL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    private val baseURL = "https://static.wippy.io"

    fun getClient(context: Context): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
    }
}
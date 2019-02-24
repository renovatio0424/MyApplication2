package com.sample.renovatio.myapplication.Api

import com.sample.renovatio.myapplication.Model.ProfileDTO
import io.reactivex.Single
import retrofit2.http.GET

interface WippyService {
    companion object {
        const val testApiUrl = "/r/app_bind.json"
    }

    @GET(testApiUrl)
    fun getTestUserProfile(): Single<ProfileDTO>
}
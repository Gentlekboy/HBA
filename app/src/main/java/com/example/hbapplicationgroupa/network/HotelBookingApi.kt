package com.example.hbapplicationgroupa.network

import com.example.hbapplicationgroupa.models.adminModule.userByIdModel.UserById
import com.example.hbapplicationgroupa.models.adminModule.userByIdModel.UserByIdData
import retrofit2.Response
import retrofit2.http.*


interface HotelBookingApi {

    @POST("v1/Authentication/add-user")
    suspend fun addUserData(@Body userByIdData: UserByIdData): Response<UserByIdData>


    @GET("v1/Authentication/add-user")
    suspend fun addUser(@Body userById: UserById): Response<UserById>
}
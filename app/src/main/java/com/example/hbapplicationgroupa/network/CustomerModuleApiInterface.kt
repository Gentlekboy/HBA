package com.example.hbapplicationgroupa.network

import com.example.hbapplicationgroupa.model.customermodule.addcustomerratingsbyhotelid.RatingsByHotelIdResponseModel
import com.example.hbapplicationgroupa.model.customermodule.addcustomerreviewbyhotelid.ReviewByHotelIdResponseModel
import com.example.hbapplicationgroupa.model.customermodule.getcustomerbookingbyuserid.BookingByUserIdResponseModel
import com.example.hbapplicationgroupa.model.customermodule.getcustomerwishlistbypagenumber.WishlistByPageNumberResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CustomerModuleApiInterface {
    //TODO: Need clarity on this API
//    @POST("api/v1/Customer/create-booking")
//    suspend fun addCustomerBookingByHotelId()

    @GET("api/v1/Customer/get-bookings/{userId}/{pageNumber}&{pageSize}")
    suspend fun getCustomerBookingsByUserId(
        @Path("userId") userId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int
    ): Response<BookingByUserIdResponseModel>

    @POST("api/v1/Customer/add-review/{hotelId}")
    suspend fun addCustomerrReviewByHotelId(
        @Path("hotelId")
        hotelId: String
    ): Response<ReviewByHotelIdResponseModel>

    @POST("api/v1/Customer/add-ratings/{hotelId}/{rating}")
    suspend fun addCustomerRatingsByHotelId(
        @Path("rating") rating: Int,
        @Path("hotelId") hotelId: String
    ): Response<RatingsByHotelIdResponseModel>

    @GET("api/v1/Customer/{userId}/wishlist/?page={pageNumber}&{pageSize}")
    suspend fun getCustomerWishListByPageNumber(
        @Path("userId") userId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int
    ): Response<WishlistByPageNumberResponseModel>

//    @PATCH("Customer/update-review/{hotelId}")
//    suspend fun updateCustomerReviewByHotelId(@Path("hotelId") hotelId: String): Response<WorkOnThis>
}
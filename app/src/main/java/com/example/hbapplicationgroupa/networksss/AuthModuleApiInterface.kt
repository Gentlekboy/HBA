package com.example.hbapplicationgroupa.networksss

import com.example.hbapplicationgroupa.model.authmodule.adduser.AddUserModel
import com.example.hbapplicationgroupa.model.authmodule.adduser.AddUserResponseModel
import com.example.hbapplicationgroupa.model.authmodule.confirmemail.ConfirmEmailModel
import com.example.hbapplicationgroupa.model.authmodule.confirmemail.ConfirmEmailResponseModel
import com.example.hbapplicationgroupa.model.authmodule.forgotpassword.ForgotPasswordResponseModel
import com.example.hbapplicationgroupa.model.authmodule.loginuser.LoginUserModel
import com.example.hbapplicationgroupa.model.authmodule.loginuser.LoginUserResponseModel
import com.example.hbapplicationgroupa.model.authmodule.resetpassword.ResetPasswordModel
import com.example.hbapplicationgroupa.model.authmodule.resetpassword.ResetPasswordResponseModel
import com.example.hbapplicationgroupa.model.authmodule.updatepassword.UpdatePasswordModel
import com.example.hbapplicationgroupa.model.authmodule.updatepassword.UpdatePasswordResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

/**
 * Set up interface for network calls.
 * Annotation @POST makes request to add new data to the API,
 * @GET requests data from API
 * @PATCH and @PUT updates data in API.
 * @PATCH modifies while @PUT replaces.
 */
interface AuthModuleApiInterface {
    @POST("api/v1/Authentication/add-user")
    suspend fun addUser(
        @Body
        addUserModel: AddUserModel
    ): Response<AddUserResponseModel>

    @GET("api/v1/Authentication/login")
    suspend fun loginUser(
        loginUserModel: LoginUserModel
    ): Response<LoginUserResponseModel>

    @PATCH("api/v1/Authentication/update-password")
    suspend fun updatePassword(
        updatePasswordModel: UpdatePasswordModel
    ): Response<UpdatePasswordResponseModel>

    @POST("api/Auth/forgot-password")
    suspend fun forgotPassword(
        EmailAddress: String
    ): Response<ForgotPasswordResponseModel>

    @POST("api/v1/Authentication/forgot-password")
    suspend fun resetPassword(
        resetPasswordModel: ResetPasswordModel
    ): Response<ResetPasswordResponseModel>

    @POST("api/v1/Authentication/confirm")
    suspend fun confirmEmail(
        confirmEmailModel: ConfirmEmailModel
    ): Response<ConfirmEmailResponseModel>

//    @POST("api/Auth/forgot-password")
//    suspend fun frogetPassword(email:String):Response<ForgotPasswordResponseModel>{
//
//
//    }
}
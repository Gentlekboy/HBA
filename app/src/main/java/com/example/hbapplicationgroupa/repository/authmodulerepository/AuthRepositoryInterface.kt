package com.example.hbapplicationgroupa.repository.authmodulerepository

import com.example.hbapplicationgroupa.model.authmodule.adduser.AddUserModel
import com.example.hbapplicationgroupa.model.authmodule.adduser.AddUserResponseModel
import com.example.hbapplicationgroupa.model.authmodule.comfirmpassword.ConfirmEmailResponse
import com.example.hbapplicationgroupa.model.authmodule.confirmemail.ConfirmEmailModel
import com.example.hbapplicationgroupa.model.authmodule.forgotpassword.ForgotPasswordResponseModel
import com.example.hbapplicationgroupa.model.authmodule.loginuser.LoginUserModel
import com.example.hbapplicationgroupa.model.authmodule.loginuser.LoginUserResponseModel
import com.example.hbapplicationgroupa.model.authmodule.refreshToken.RefreshTokenResponseModel
import com.example.hbapplicationgroupa.model.authmodule.resetpassword.ResetPasswordModel
import com.example.hbapplicationgroupa.model.authmodule.resetpassword.ResetPasswordResponseModel
import com.example.hbapplicationgroupa.model.authmodule.updatepassword.UpdatePasswordModel
import com.example.hbapplicationgroupa.model.authmodule.updatepassword.UpdatePasswordResponseModel
import retrofit2.Response

interface AuthRepositoryInterface {
    suspend fun addUser(addUserModel: AddUserModel): Response<AddUserResponseModel>
    suspend fun loginUser(loginUserModel: LoginUserModel): Response<LoginUserResponseModel>?
    suspend fun updatePassword(updatePasswordModel: UpdatePasswordModel): Response<UpdatePasswordResponseModel>
    suspend fun forgotPassword(EmailAddress: String): Response<ForgotPasswordResponseModel>
    suspend fun resetPassword(resetPasswordModel: ResetPasswordModel): Response<ResetPasswordResponseModel>
    suspend fun confirmEmail(confirmEmailModel: ConfirmEmailModel): Response<ConfirmEmailResponse>
    suspend fun refreshToken(token: String, userId: String, refreshToken: String): Response<RefreshTokenResponseModel>
}
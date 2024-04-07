package com.example.b2110941_communicationofdream.apis

import com.example.b2110941_communicationofdream.models.UserResponse
import com.example.b2110941_communicationofdream.models.RequestAddWish
import com.example.b2110941_communicationofdream.models.RequestDeleteWish
import com.example.b2110941_communicationofdream.models.RequestRegisterOrLogin
import com.example.b2110941_communicationofdream.models.RequestUpdateWish
import com.example.b2110941_communicationofdream.models.ResponseMessage
import com.example.b2110941_communicationofdream.models.Wish
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

class Constants{
    companion object{
        private  const val BASE_URL = "http://158.178.227.41:6868/api/"
        fun getInstance(): ApiService{ //Tham chiếu đến API
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build().create(ApiService::class.java)
        }
    }
}

interface ApiService {
    @GET("wishes") //Phương thức
    suspend fun getWishList(): Response<List<Wish>>

    @POST("wishes")
    suspend fun addWish(@Body addWish : RequestAddWish):
            Response<ResponseMessage>

    @PATCH("wishes")
    suspend fun updateWish(@Body updateWish : RequestUpdateWish):
            Response<ResponseMessage>

    @HTTP(method = "DELETE", path = "wishes", hasBody = true)
    suspend fun deleteWish(@Body deleteWish: RequestDeleteWish):
            Response<ResponseMessage>

    @POST("users/register")
    suspend fun registerUser(@Body request: RequestRegisterOrLogin):
            Response<UserResponse>

    @POST("users/login")
    suspend fun loginUser(@Body request: RequestRegisterOrLogin):
            Response<UserResponse>


}
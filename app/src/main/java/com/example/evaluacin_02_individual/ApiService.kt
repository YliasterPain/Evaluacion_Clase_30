package com.example.evaluacin_02_individual

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
//crea el metodo para acceder a nuestra api
interface ApiService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<DogsResponse>
}



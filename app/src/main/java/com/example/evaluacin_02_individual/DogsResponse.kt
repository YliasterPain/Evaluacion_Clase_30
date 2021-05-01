package com.example.evaluacin_02_individual

import com.google.gson.annotations.SerializedName
//clase para leer json
data class DogsResponse (
    @SerializedName("status") var status:String, //propiedad @"" para cambiar nombre de var y no de error por el nombre de la api
    @SerializedName("message") var images:List<String>
)
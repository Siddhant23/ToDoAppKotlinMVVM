package com.eko.sidtestandroid.model.api

import com.eko.sidtestandroid.model.data.TODO
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("todos")
     fun getTodoList(): Call<List<TODO>>

}
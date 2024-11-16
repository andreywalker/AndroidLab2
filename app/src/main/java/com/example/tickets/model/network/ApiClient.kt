package com.example.tickets.model.network


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


private const val BASE_URL = "https://api.api-ninjas.com/v1/"

object RetrofitClient {
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}
object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}
interface ApiService {
    @GET("dogs")
    fun getDogs(
        @Header("X-Api-Key") apiKey: String="/FpfK5wuf8diHH85k3j07g==h14YZQjuUBWS5gqf",
        @Query("barking") barking: Int? = 2 // Optional query parameter for dog name
    ): Call<List<Dog>>
}
data class Dog(
    val name: String,
    val id: String,
    val trainability: String?,
    val protectiveness: String?,
    val min_life_expectancy: String?,
    val energy: String?,
    val image_link: String?
)

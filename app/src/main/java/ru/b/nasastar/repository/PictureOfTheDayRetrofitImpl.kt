package ru.b.nasastar.repository

import android.util.Log
import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.b.nasastar.BuildConfig
import ru.b.nasastar.viewmodel.PictureOfTheDayViewModel

private val nasaBaseUrl = "https://api.nasa.gov/"


val pictureOfTheDay = Retrofit.Builder()
    .baseUrl(nasaBaseUrl)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .build().create(PictureOfTheDayAPI::class.java)

class PictureOfTheDayRetrofitImpl : PictureDetailed {
    override fun getPictureDetails(date: String, callback: PictureOfTheDayViewModel.Callback) {

        pictureOfTheDay.getPictureOfTheDay(BuildConfig.NASA_API_KEY, date)
            .enqueue(object : Callback<PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.onResponse(it)
                        }
                    } else {
                        Log.d("@@@", "Ошибка ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    Log.d("@@@", "Ошибка: не получили ответ!")
                }
            })
    }

    override fun getEPIC(
        apiKey: String,
        epicCallback: Callback<List<EarthEpicServerResponseData>>
    ) {
        pictureOfTheDay.getEPIC(apiKey).enqueue(epicCallback)
    }
}

package ru.b.nasastar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.b.nasastar.repository.PictureOfTheDayResponseData
import ru.b.nasastar.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.b.nasastar.BuildConfig

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayAppState> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayAppState> {
        return liveData
    }

    fun sendRequest(date: String) {
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        if (BuildConfig.NASA_API_KEY != null) {
            pictureOfTheDayRetrofitImpl.getRetrofit().getPictureOfTheDay(BuildConfig.NASA_API_KEY, date)
                .enqueue(callback)
        }

    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureOfTheDayAppState.Success(it))
                }
            } else {
                response.body()?.let {
                    liveData.postValue(PictureOfTheDayAppState.Error(Throwable()))
                    Log.d("@@@", "Ошибка ${response.message()}")
                }
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveData.postValue(PictureOfTheDayAppState.Error(Throwable()))
            Log.d("@@@", "Ошибка: не получили ответ!")
        }

    }

}
package ru.b.nasastar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.model.data.EarthEpicServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.b.nasastar.BuildConfig
import ru.b.nasastar.repository.PictureDetailed
import ru.b.nasastar.repository.PictureOfTheDayResponseData
import ru.b.nasastar.repository.PictureOfTheDayRetrofitImpl

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayAppState> = MutableLiveData(),
    private var repository: PictureDetailed = PictureOfTheDayRetrofitImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<PictureOfTheDayAppState> {
        return liveData
    }

    fun sendRequest(date: String) {
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        if (BuildConfig.NASA_API_KEY != null) {
            repository.getPictureDetails(date, object : Callback {
                override fun onResponse(pictureOfTheDayResponseData: PictureOfTheDayResponseData) {
                    liveData.postValue(PictureOfTheDayAppState.Success(pictureOfTheDayResponseData))
                }

                override fun onFail() {
                    Log.d("@@@", "Ошибка: не получили ответ!")
                }

            })
        }

    }

    fun getEpic() {
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayAppState.Error(Throwable())
        } else {
            repository.getEPIC(apiKey, epicCallback)
        }
    }

    interface Callback {
        fun onResponse(date: PictureOfTheDayResponseData)
        fun onFail()
    }

    private val epicCallback = object : retrofit2.Callback<List<EarthEpicServerResponseData>> {

        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveData.postValue(PictureOfTheDayAppState.SuccessEarthEpic(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveData.postValue(PictureOfTheDayAppState.Error(Throwable()))
                } else {
                    liveData.postValue(PictureOfTheDayAppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveData.postValue(PictureOfTheDayAppState.Error(t))
        }
    }

}
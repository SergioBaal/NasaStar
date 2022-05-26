package ru.b.nasastar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    interface Callback {
        fun onResponse(date: PictureOfTheDayResponseData)
        fun onFail()
    }
}
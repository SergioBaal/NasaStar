package ru.b.nasastar.viewmodel

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import ru.b.nasastar.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayAppState {
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData) :
        PictureOfTheDayAppState()

    data class SuccessEarthEpic(val serverResponseData: List<EarthEpicServerResponseData>) :
        PictureOfTheDayAppState()

    data class Error(val error: Throwable) : PictureOfTheDayAppState()
    data class Loading(val progress: Int?) : PictureOfTheDayAppState()
}


package ru.b.nasastar.repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import retrofit2.Callback
import ru.b.nasastar.viewmodel.PictureOfTheDayViewModel


interface PictureDetailed {
    fun getPictureDetails(date: String, callback: PictureOfTheDayViewModel.Callback)
    fun getEPIC(dare: String, epicCallback: Callback<List<EarthEpicServerResponseData>>)
}
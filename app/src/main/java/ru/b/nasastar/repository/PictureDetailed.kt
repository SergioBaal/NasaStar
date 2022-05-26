package ru.b.nasastar.repository

import ru.b.nasastar.viewmodel.PictureOfTheDayViewModel


interface PictureDetailed {
    fun getPictureDetails(date: String, callback: PictureOfTheDayViewModel.Callback)
}
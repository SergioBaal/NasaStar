package ru.b.nasastar.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ru.b.nasastar.repository.PictureOfTheDayResponseData

class EquilateralImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}

fun convertDtoToString(pictureOfTheDayResponseData: PictureOfTheDayResponseData): String {
    return pictureOfTheDayResponseData.date
}



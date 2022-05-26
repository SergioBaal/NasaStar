package ru.b.nasastar.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.b.nasastar.view.picture.PictureOfTheDayFragment
import ru.b.nasastar.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,
                PictureOfTheDayFragment.newInstance()).commit()
        }
    }
}
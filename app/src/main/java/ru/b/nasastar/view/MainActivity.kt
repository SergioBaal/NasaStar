package ru.b.nasastar.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.b.nasastar.R
import ru.b.nasastar.view.picture.PictureOfTheDayFragment

const val THEME_ONE = 1
const val THEME_TWO = 2
const val THEME_THREE = 3

class MainActivity : AppCompatActivity() {
    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                PictureOfTheDayFragment.newInstance()
            ).commit()
        }
    }




    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            THEME_ONE -> R.style.MyGreenTheme
            THEME_TWO -> R.style.MyRedTheme
            THEME_THREE -> R.style.MyBlueTheme
            else -> 0
        }
    }

}
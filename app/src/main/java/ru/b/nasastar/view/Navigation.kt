package ru.b.nasastar.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.b.nasastar.R

class Navigation(private val fragmentManager: FragmentManager) {

    fun showFragment(fragment: Fragment, useBackstack: Boolean) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contentContainer, fragment)
        if (useBackstack)
            fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }

    fun showNavigationFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navigationContainer, fragment)
        fragmentTransaction.commit()
    }
}
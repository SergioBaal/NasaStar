package ru.b.nasastar.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.b.nasastar.R
import ru.b.nasastar.databinding.FragmentNavigationBinding
import ru.b.nasastar.view.picture.PictureOfTheDayFragment
import ru.b.nasastar.view.settings.SettingsFragment


class NavigationFragment : Fragment() {

    lateinit var navigation: Navigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = (context as MainActivity).navigation
    }

    private var _binding: FragmentNavigationBinding? = null
    private val binding: FragmentNavigationBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavigationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_photo ->
                    navigation.showFragment(PictureOfTheDayFragment.newInstance(), true)
                R.id.action_weather ->
                    navigation.showFragment(EarthFragment.newInstance(), true)
                R.id.action_settings ->
                    navigation.showFragment(SettingsFragment.newInstance(), true)
            }
            true
        }
        binding.navigationView.selectedItemId = R.id.action_photo
    }

    companion object {
        fun newInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }
}
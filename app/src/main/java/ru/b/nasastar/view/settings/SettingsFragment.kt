package ru.b.nasastar.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import ru.b.nasastar.R
import ru.b.nasastar.databinding.FragmentSettingsBinding
import ru.b.nasastar.view.*


class SettingsFragment : Fragment() {
    private lateinit var parentActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (parentActivity.getCurrentTheme()) {
            1 -> binding.settingsChipGroup.check(R.id.chipOne)
            2 -> binding.settingsChipGroup.check(R.id.chipTwo)
            3 -> binding.settingsChipGroup.check(R.id.chipThree)
        }
        binding.settingsChipGroup.setOnCheckedStateChangeListener { chipGroup: ChipGroup, mutableList: MutableList<Int> ->
            for (id in mutableList) {
                when (id) {
                    R.id.chipOne -> {
                        parentActivity.setCurrentTheme(THEME_ONE)
                        parentActivity.recreate()
                    }
                    R.id.chipTwo -> {
                        parentActivity.setCurrentTheme(THEME_TWO)
                        parentActivity.recreate()
                    }
                    R.id.chipThree -> {
                        parentActivity.setCurrentTheme(THEME_THREE)
                        parentActivity.recreate()
                    }
                }
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}



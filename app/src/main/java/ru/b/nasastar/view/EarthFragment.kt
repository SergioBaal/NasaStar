package ru.b.nasastar.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.b.nasastar.BuildConfig
import ru.b.nasastar.databinding.FragmentEarthBinding
import ru.b.nasastar.viewmodel.PictureOfTheDayAppState
import ru.b.nasastar.viewmodel.PictureOfTheDayViewModel

class EarthFragment : Fragment() {
    val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }
    private lateinit var parentActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getEpic()
    }

    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {}
            is PictureOfTheDayAppState.Loading -> {}
            is PictureOfTheDayAppState.Success -> {}

            is PictureOfTheDayAppState.SuccessEarthEpic -> {
                val strDate =
                    pictureOfTheDayAppState.serverResponseData.last().date.split(" ").first()
                val image = pictureOfTheDayAppState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-", "/", true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.photo.load(url)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}




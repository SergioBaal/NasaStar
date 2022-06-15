package ru.b.nasastar.view.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.google.android.material.tabs.TabLayout
import ru.b.nasastar.R
import ru.b.nasastar.databinding.FragmentPictureOfTheDayBinding
import ru.b.nasastar.viewmodel.PictureOfTheDayAppState
import ru.b.nasastar.viewmodel.PictureOfTheDayViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTheDayFragment : Fragment() {


    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initViewModel()
        // initBottomSheetBehavior()
        workWithTabs()
    }

    private fun initViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest(makeDate(0))
    }

//    private fun initBottomSheetBehavior() {
//        val bottomSheetBehavior = BottomSheetBehavior.from(binding.
//        lifeHack.bottomSheetContainer)
//        with(bottomSheetBehavior) {
//            state = BottomSheetBehavior.STATE_HALF_EXPANDED
//            isHideable = false
//            addBottomSheetCallback(object :
//                BottomSheetBehavior.BottomSheetCallback() {
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    when (newState) {
//                        BottomSheetBehavior.STATE_DRAGGING -> {}
//                        BottomSheetBehavior.STATE_COLLAPSED -> {}
//                        BottomSheetBehavior.STATE_EXPANDED -> {}
//                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
//                        BottomSheetBehavior.STATE_HIDDEN -> {}
//                        BottomSheetBehavior.STATE_SETTLING -> {}
//                    }
//                }
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    Log.d("@@@", "$slideOffset")
//                }
//            })
//        }
//    }

    private fun workWithTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.sendRequest(makeDate(0))
                    }
                    1 -> {
                        viewModel.sendRequest(makeDate(-1))
                    }
                    2 -> {
                        viewModel.sendRequest(makeDate(-2))
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun makeDate(minus: Int): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, minus)
        return dateFormat.format(cal.time)
    }

    var isOpen: Boolean = false
    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {}
            is PictureOfTheDayAppState.Loading -> {
                binding.imageView.load(R.drawable.progress_animation)
            }
            is PictureOfTheDayAppState.Success -> {
                binding.imageView.load(pictureOfTheDayAppState.pictureOfTheDayResponseData.hdurl) {
                    placeholder(R.drawable.progress_animation)
                }
                binding.imageView.setOnClickListener {
                    isOpen = !isOpen
                    val transitionCB = ChangeBounds()
                    val transitionImage = ChangeImageTransform()
                    transitionCB.duration=3000
                    transitionImage.duration=3000
                    val transitionSet = TransitionSet()
                    transitionSet.addTransition(transitionCB)
                    transitionSet.addTransition(transitionImage)
                    TransitionManager.beginDelayedTransition(binding.root,transitionSet)
                    binding.imageView.scaleType =if(!isOpen){ImageView.ScaleType.CENTER}else{ImageView.ScaleType.CENTER_INSIDE}
                    (binding.imageView.layoutParams as  FrameLayout.LayoutParams)
                    val params = (binding.imageView.layoutParams as  FrameLayout.LayoutParams)
                    params.height = if(isOpen){FrameLayout.LayoutParams.MATCH_PARENT}else{FrameLayout.LayoutParams.WRAP_CONTENT}
                    binding.imageView.layoutParams = params
                }
                binding.text.text =
                    pictureOfTheDayAppState.pictureOfTheDayResponseData.explanation.toString()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
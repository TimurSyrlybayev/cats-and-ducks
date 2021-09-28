package com.example.catsandducksapp.view.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.catsandducksapp.databinding.FragmentMainBinding
import com.example.catsandducksapp.viewmodel.MainViewModel
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var buttonForCatImages: Button
    private lateinit var buttonForDuckImages: Button
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var imageView: ImageView
    private lateinit var pleaseWaitPlaceholderText: TextView
    private var isImageFirstAppearance = true

    companion object {
        private const val DUCKS_BUTTON_TRANSLATIONX = 64f
        private const val CATS_BUTTON_TRANSLATIONX = -64f
        private const val BUTTONS_MARGIN = 76
        private const val IMAGE_VIEW_INITIAL_TRANSPARENCY = 0f
        private const val IMAGE_VIEW_FINAL_TRANSPARENCY = 1f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonForCatImages = buttonCats
            buttonForDuckImages = buttonDucks
            mainLayout = mainFragmentLayout
            imageView = imageFrame
            pleaseWaitPlaceholderText = pleaseWaitPlaceholder
        }

        buttonForCatImages.setOnClickListener {
            val transformationHeight = (mainLayout.height / 2) - BUTTONS_MARGIN
            if (isImageFirstAppearance && imageView.drawable == null) {
                animate(transformationHeight)
                isImageFirstAppearance = false
            }
            viewModel.getImage(buttonForCatImages.id).observe(
                viewLifecycleOwner,
                Observer { responseString ->
                    Picasso
                        .get()
                        .load(
                            responseString
                                .replaceAfterLast("webp", "")
                                .substringAfterLast('"')
                        )
                        .fit()
                        .centerInside()
                        .into(binding.imageFrame)
                    pleaseWaitPlaceholderText.visibility = View.GONE
                }
            )
        }

        buttonForDuckImages.setOnClickListener {
            val transformationHeight = (mainLayout.height / 2) - BUTTONS_MARGIN
            if (isImageFirstAppearance && imageView.drawable == null) {
                animate(transformationHeight)
                isImageFirstAppearance = false
            }
            viewModel.getImage(buttonForDuckImages.id).observe(
                viewLifecycleOwner,
                Observer { responseString ->
                    Picasso
                        .get()
                        .load(
                            responseString
                                .substringBeforeLast('"')
                                .substringAfterLast('"')
                        )
                        .fit()
                        .centerInside()
                        .into(binding.imageFrame)
                    pleaseWaitPlaceholderText.visibility = View.GONE
                }
            )
        }
    }

    private fun animate(transformationHeight: Int) {
        ObjectAnimator.ofFloat(
            buttonForCatImages,
            "translationY",
            transformationHeight.toFloat()
        ).apply {
            duration = 1000
            start()
        }
        ObjectAnimator.ofFloat(
            buttonForDuckImages,
            "translationY",
            transformationHeight.toFloat()
        ).apply {
            duration = 1000
            start()
        }
        ObjectAnimator.ofFloat(
            buttonForCatImages,
            "translationX",
            CATS_BUTTON_TRANSLATIONX
        ).apply {
            duration = 1000
            start()
        }
        ObjectAnimator.ofFloat(
            buttonForDuckImages,
            "translationX",
            DUCKS_BUTTON_TRANSLATIONX
        ).apply {
            duration = 1000
            start()
        }

        ObjectAnimator.ofFloat(
            imageView,
            "alpha",
            IMAGE_VIEW_INITIAL_TRANSPARENCY,
            IMAGE_VIEW_FINAL_TRANSPARENCY
        ).apply {
            duration = 1000
            start()
        }

        object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                pleaseWaitPlaceholderText.visibility = View.VISIBLE
            }
        }.start()
    }
}
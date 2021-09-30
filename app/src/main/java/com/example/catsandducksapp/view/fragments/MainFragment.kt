package com.example.catsandducksapp.view.fragments

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.catsandducksapp.DoubleClickListener
import com.example.catsandducksapp.data.model.ImageItem
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
    private var currentImageUrl: String? = null

    companion object {
        private const val ANIMATION_DURATION = 1000L
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

        binding.apply {
            buttonForCatImages = buttonCats
            buttonForDuckImages = buttonDucks
            mainLayout = mainFragmentLayout
            imageView = imageFrame
        }

        if (savedInstanceState?.get("url") != null) {

            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    animate(350)
                }
                Configuration.ORIENTATION_PORTRAIT -> {
                    animate(670)
                }
            }

            Picasso
                .get()
                .load(
                    savedInstanceState.getString("url")
                )
                .fit()
                .centerInside()
                .into(binding.imageFrame)
            currentImageUrl = savedInstanceState.getString("url")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
                if (currentImageUrl != null) {
                    val imageItem = ImageItem(
                        0,
                        currentImageUrl!!
                    )
                    viewModel.saveImage(imageItem)
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Image was saved to favourites.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        buttonForCatImages.setOnClickListener {
            val transformationHeight = (mainLayout.height / 2) - BUTTONS_MARGIN
            if (imageView.drawable == null) {
                animate(transformationHeight)
            }
            viewModel.getImage(buttonForCatImages.id).observe(
                viewLifecycleOwner,
                Observer { responseString ->
                    val url = responseString
                        .replaceAfterLast("webp", "")
                        .substringAfterLast('"')
                    Picasso
                        .get()
                        .load(
                            url
                        )
                        .fit()
                        .centerInside()
                        .into(binding.imageFrame)
                    currentImageUrl = url
                }
            )
        }

        buttonForDuckImages.setOnClickListener {
            val transformationHeight = (mainLayout.height / 2) - BUTTONS_MARGIN
            if (imageView.drawable == null) {
                animate(transformationHeight)
            }
            viewModel.getImage(buttonForDuckImages.id).observe(
                viewLifecycleOwner,
                Observer { responseString ->
                    val url = responseString
                        .substringBeforeLast('"')
                        .substringAfterLast('"')
                    Picasso
                        .get()
                        .load(
                            url
                        )
                        .fit()
                        .centerInside()
                        .into(binding.imageFrame)
                    currentImageUrl = url
                }
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (currentImageUrl != null) {
            outState.putString("url", currentImageUrl)
        }
        super.onSaveInstanceState(outState)
    }

    private fun animate(transformationHeight: Int) {
        ObjectAnimator.ofFloat(
            buttonForCatImages,
            "translationY",
            transformationHeight.toFloat()
        ).apply {
            duration = ANIMATION_DURATION
            start()
        }
        ObjectAnimator.ofFloat(
            buttonForDuckImages,
            "translationY",
            transformationHeight.toFloat()
        ).apply {
            duration = ANIMATION_DURATION
            start()
        }
        ObjectAnimator.ofFloat(
            buttonForCatImages,
            "translationX",
            CATS_BUTTON_TRANSLATIONX
        ).apply {
            duration = ANIMATION_DURATION
            start()
        }
        ObjectAnimator.ofFloat(
            buttonForDuckImages,
            "translationX",
            DUCKS_BUTTON_TRANSLATIONX
        ).apply {
            duration = ANIMATION_DURATION
            start()
        }

        ObjectAnimator.ofFloat(
            imageView,
            "alpha",
            IMAGE_VIEW_INITIAL_TRANSPARENCY,
            IMAGE_VIEW_FINAL_TRANSPARENCY
        ).apply {
            duration = ANIMATION_DURATION
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
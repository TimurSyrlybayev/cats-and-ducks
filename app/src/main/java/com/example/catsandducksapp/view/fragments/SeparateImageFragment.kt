package com.example.catsandducksapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catsandducksapp.databinding.FragmentFavouritesBinding
import com.example.catsandducksapp.databinding.FragmentSeparateImageBinding
import com.squareup.picasso.Picasso

class SeparateImageFragment : Fragment() {

    private var _binding: FragmentSeparateImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeparateImageBinding.inflate(inflater, container, false)

        val safeArgs = SeparateImageFragmentArgs.fromBundle(requireArguments())

        Picasso
            .get()
            .load(
                safeArgs.imageUrl
            )
            .fit()
            .centerInside()
            .into(binding.separateImageContainer)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
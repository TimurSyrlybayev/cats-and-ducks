package com.example.catsandducksapp.view.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catsandducksapp.R
import com.example.catsandducksapp.data.model.ImageItem
import com.example.catsandducksapp.databinding.FragmentFavouritesBinding
import com.example.catsandducksapp.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerAdapter? = null
    private var list: MutableList<ImageItem>? = null
    private var viewModel: MainViewModel? = null

    companion object {
        private const val ANIMATION_DURATION = 1000L
        private const val IMAGE_VIEW_INITIAL_SCALE = 1f
        private const val IMAGE_VIEW_FINAL_SCALE = 1.5f
        private const val IMAGE_VIEW_FINAL_TRANSPARENCY = 0f
        private const val IMAGE_VIEW_INITIAL_TRANSPARENCY = 1f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerContainer
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel!!.getSavedImages().observe(
            viewLifecycleOwner,
            Observer {
                updateRecycler(it)
            }
        )

        binding.clearListButton.setOnClickListener {
            viewModel!!.clearAllImages()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateRecycler(l: MutableList<ImageItem>) {
        list = l
        adapter = RecyclerAdapter(list as ArrayList<ImageItem>)
        recyclerView.adapter = adapter
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }

    private inner class ImageHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var imageItemInHolder: ImageItem
        private var imageContainer: ImageView
        private var imageTimeText: TextView

        init {
            itemView.findViewById<ImageView>(R.id.deleteIcon).setOnClickListener(this)
            itemView.findViewById<ImageView>(R.id.imageContainer).setOnClickListener(this)
            imageContainer = itemView.findViewById(R.id.imageContainer)
            imageTimeText = itemView.findViewById(R.id.imageTime)
        }

        fun bind(item: ImageItem) {
            this.imageItemInHolder = item
            Picasso
                .get()
                .load(
                    imageItemInHolder.imageUrl
                )
                .fit()
                .centerCrop()
                .into(imageContainer)
            imageTimeText.text = item.imageTime
        }

        override fun onClick(v: View?) {
            when (v?.tag) {
                "unlike" -> {
                    if (absoluteAdapterPosition != -1) {

                        animate()
                        object : CountDownTimer(ANIMATION_DURATION, ANIMATION_DURATION) {
                            override fun onTick(millisUntilFinished: Long) {}

                            override fun onFinish() {
                                list!!.removeAt(absoluteAdapterPosition)
                                recyclerView.adapter!!.notifyItemRemoved(absoluteAdapterPosition)
                                viewModel?.deleteImage(imageItemInHolder)
                            }
                        }.start()

                    }
                }
                "preview" -> {
                    findNavController()
                        .safeNavigate(
                            FavouritesFragmentDirections.actionFavouritesFragmentToSeparateImageFragment(imageItemInHolder.imageUrl)
                        )
                }
            }

        }

        private fun animate() {
            val deleteIcon = itemView.findViewById<ImageView>(R.id.deleteIcon)
            ObjectAnimator.ofFloat(
                deleteIcon,
                "scaleX",
                IMAGE_VIEW_INITIAL_SCALE,
                IMAGE_VIEW_FINAL_SCALE
            ).apply {
                duration = ANIMATION_DURATION
                start()
            }
            ObjectAnimator.ofFloat(
                deleteIcon,
                "scaleY",
                IMAGE_VIEW_INITIAL_SCALE,
                IMAGE_VIEW_FINAL_SCALE
            ).apply {
                duration = ANIMATION_DURATION
                start()
            }
            ObjectAnimator.ofFloat(
                deleteIcon,
                "alpha",
                IMAGE_VIEW_INITIAL_TRANSPARENCY,
                IMAGE_VIEW_FINAL_TRANSPARENCY
            ).apply {
                duration = ANIMATION_DURATION
                start()
            }
        }

    }

    private inner class RecyclerAdapter(var list: List<ImageItem>) : RecyclerView.Adapter<ImageHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
            val view = layoutInflater.inflate(R.layout.card_layout, parent, false)
            return ImageHolder(view)
        }

        override fun onBindViewHolder(holder: ImageHolder, position: Int) {
            val item = list[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

}
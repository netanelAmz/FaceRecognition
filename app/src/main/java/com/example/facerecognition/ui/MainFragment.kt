package com.example.facerecognition.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.facerecognition.databinding.FragmentMainBinding
import com.example.facerecognition.ui.adapter.PhotosRecyclerView
import com.example.facerecognition.ui.wm.DataObject
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), PhotosRecyclerView.OnItemClick {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val mainVM: MainVM by viewModels()
    lateinit var layoutManager: LinearLayoutManager
    var state : Int = 0
    var photosList: ArrayList<Bitmap> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        binding.buttonDetect.setOnClickListener {
            CoroutineScope(Main).launch {
                binding.shimmerFrameLayout.visibility = View.VISIBLE
                binding.shimmerFrameLayout.startShimmerAnimation()
                loadFacesPhotos()
            }
        }
    }

    private fun initView() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (state!= tab!!.position){
                    photosList.clear()
                    try {
                        binding.rvImages.adapter!!.notifyDataSetChanged()
                    } catch (e: Exception) {
                    }
                }
                state= tab.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }


    private suspend fun loadFacesPhotos() {

        mainVM.loadFacesPhotos(state)

        mainVM.listPhotos.observe(viewLifecycleOwner, {
            if (it != null) {
                try {
                    photosList.clear()
                    photosList.addAll(it)
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.rvImages.visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(requireContext())
                    binding.rvImages.layoutManager = layoutManager
                    binding.rvImages.adapter = PhotosRecyclerView(photosList, requireContext(), this@MainFragment)
                    DataObject.listBitmaps.value = null
                }catch (ex : Exception){
                    println( "Exception ${ex.message}" )
                }

            }

        })
    }

    override fun onItemClick(i: Int) {
//        TODO("Not yet implemented")
    }

}
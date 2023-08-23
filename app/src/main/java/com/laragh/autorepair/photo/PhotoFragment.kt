package com.laragh.autorepair.photo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laragh.autorepair.R
import com.laragh.autorepair.databinding.FragmentPhotoBinding

class PhotoFragment : Fragment() {

    private var binding: FragmentPhotoBinding? = null

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var mutableList: MutableList<CardViewItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavMenu()
        val myUri = Uri.parse("")
        mutableList = mutableListOf(CardViewItem(CardViewItem.EMPTY_TYPE, myUri))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCloseButton()
        initAdapter()
    }

    private fun hideBottomNavMenu() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.GONE
    }

    private fun initCloseButton() {
        binding?.closeButton?.setOnClickListener {
            binding?.root?.findNavController()?.navigate(
                R.id.action_photoFragment_to_homeFragment
            )
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123)
    }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, data)",
            "androidx.fragment.app.Fragment"
        )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val x = data.clipData!!.itemCount
                for (i in 0 until x) {
                    mutableList.add(
                        CardViewItem(
                            CardViewItem.IMAGE_TYPE,
                            data.clipData!!.getItemAt(i).uri
                        )
                    )

                }
            } else if (data?.data != null) {
                val imgUrl = data.data!!
                val size = mutableList.size
                mutableList.add(size - 1, CardViewItem(CardViewItem.IMAGE_TYPE, imgUrl))
            }
            photoAdapter.setItems(mutableList)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteItem(position: Int) {
        if (::mutableList.isInitialized && ::photoAdapter.isInitialized) {
            mutableList.removeAt(position)
            photoAdapter.setItems(mutableList)
        }
    }

    private fun initAdapter() {
        photoAdapter = PhotoAdapter(
            mutableList,
            { position -> deleteItem(position) },
            { openGallery() }
        )
        binding?.recyclerView?.apply {
            adapter = photoAdapter
        }
    }
}
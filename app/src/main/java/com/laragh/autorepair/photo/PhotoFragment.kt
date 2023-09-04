package com.laragh.autorepair.photo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentPhotoBinding
import com.laragh.autorepair.utils.Constants.PHOTOS
import com.laragh.autorepair.utils.Constants.STORAGE_URL

class PhotoFragment : BaseFragment<FragmentPhotoBinding>() {

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var mutableList: MutableList<CardViewItem>
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavMenu()
        val myUri = Uri.parse("")
        mutableList = mutableListOf(CardViewItem(CardViewItem.EMPTY_TYPE, myUri))
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPhotoBinding {
        return FragmentPhotoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCloseButton()
        initAdapter()
        setAttachButtonColor()
    }

    private fun hideBottomNavMenu() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.GONE
    }

    private fun initCloseButton() {
        binding.closeButton.setOnClickListener {
            binding.root.findNavController().navigate(
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
            val size = mutableList.size
            if (data?.clipData != null) {
                val x = data.clipData!!.itemCount
                for (i in 0 until x) {
                    mutableList.add(
                        size - 1,
                        CardViewItem(
                            CardViewItem.IMAGE_TYPE,
                            data.clipData!!.getItemAt(i).uri
                        )
                    )
                }
            } else if (data?.data != null) {
                val imgUrl = data.data!!
                mutableList.add(size - 1, CardViewItem(CardViewItem.IMAGE_TYPE, imgUrl))
            }
            photoAdapter.setItems(mutableList)
        }
        setAttachButtonColor()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteItem(position: Int) {
        if (::mutableList.isInitialized && ::photoAdapter.isInitialized) {
            mutableList.removeAt(position)
            photoAdapter.setItems(mutableList)
            setAttachButtonColor()
        }
    }

    private fun initAdapter() {
        photoAdapter = PhotoAdapter(
            mutableList,
            { position -> deleteItem(position) },
            { openGallery() }
        )
        binding.recyclerView.apply {
            adapter = photoAdapter
        }
    }

    private fun setAttachButtonColor() {
        if (mutableList.size > 1) {
            binding.attachButton.setBackgroundColor(resources.getColor(R.color.orange))
            binding.attachButton.isClickable = true
            initAttachButton()
        } else {
            binding.attachButton.setBackgroundColor(resources.getColor(R.color.gray))
            binding.attachButton.isClickable = false
        }
    }

    private fun initAttachButton() {
        binding.attachButton.setOnClickListener {
            saveInStorage()
            binding.root.findNavController().navigate(
                R.id.action_photoFragment_to_homeFragment
            )
        }
    }

    private fun saveInStorage() {
        val storageRef = Firebase.storage(STORAGE_URL).reference
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        for (i in 0..mutableList.size - 2) {
            val photoUri = mutableList[i].uri!!
            val photosRef: StorageReference =
                storageRef.child(PHOTOS).child(userID)
                    .child(userViewModel.selectedCar.value!!.id)
                    .child(photoUri.pathSegments.last())

            val uploadTask = photosRef.putFile(photoUri)
            uploadTask.addOnFailureListener {

            }.addOnSuccessListener {

            }
        }
    }
}
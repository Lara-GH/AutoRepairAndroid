package com.laragh.autorepair.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentHomeBinding
import com.laragh.autorepair.utils.Permissions

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: UserViewModel by activityViewModels()
    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavMenu()

        val selectedCar = viewModel.selectedCar.value
        if (selectedCar != null) {
            fillTextView(
                selectedCar.year!!,
                selectedCar.make!!,
                selectedCar.model!!,
                selectedCar.engine!!
            )
            getCarLogo(selectedCar.make.lowercase())
        }
        initAddPhotoButton()
    }

    @SuppressLint("SetTextI18n")
    private fun fillTextView(year: String, make: String, model: String, engine: String) {
        binding.textMakeModel.text = ("$make  $model")
        binding.textEngineYear.text = ("$engine  $year")
    }

    private fun getCarLogo(logo: String) {
        Glide.with(requireContext())
            .load("https://www.carlogos.org/car-logos/$logo-logo.png")
            .placeholder(R.drawable.ic_car_gray)
            .error(R.drawable.ic_car_gray)
            .into(binding.imageCarLogo)
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initAddPhotoButton() {
        binding.addPhotoButton.setOnClickListener {
            if (Permissions(requireContext(), requestMultiplePermissions).checkPermissions()) {
                binding.root.findNavController().navigate(
                    R.id.action_homeFragment_to_photoFragment
                )
            }
        }
    }

    private fun showBottomNavMenu() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.VISIBLE
    }
}

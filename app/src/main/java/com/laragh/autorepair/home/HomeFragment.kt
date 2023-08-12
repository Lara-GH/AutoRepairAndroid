package com.laragh.autorepair.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laragh.autorepair.utils.Permissions
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: UserViewModel by activityViewModels()
    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBottomNavMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        binding?.textMakeModel?.text = ("$make  $model")
        binding?.textEngineYear?.text = ("$engine  $year")
    }

    private fun getCarLogo(logo: String) {
        Picasso.get()
            .load("https://www.carlogos.org/car-logos/$logo-logo.png")
            .placeholder(R.drawable.ic_car_gray)
            .error(R.drawable.ic_car_gray)
            .into(binding?.imageCarLogo)
    }

    private fun initAddPhotoButton() {
        binding?.addPhotoButton?.setOnClickListener {
            Permissions(requireContext(), requestMultiplePermissions).checkPermissions()
        }
    }

    private fun showBottomNavMenu() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.VISIBLE
    }
}

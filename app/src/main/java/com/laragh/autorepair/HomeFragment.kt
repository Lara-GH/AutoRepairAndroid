package com.laragh.autorepair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laragh.autorepair.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

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

        userViewModel.selectedCar.observe(viewLifecycleOwner){
            it.make?.let { it1 -> getCarLogo(it1.lowercase()) }
        }
    }

    private fun getCarLogo(logo: String) {
        Picasso.get()
            .load("https://www.carlogos.org/car-logos/$logo-logo.png")
            .placeholder(R.drawable.ic_car_gray)
            .error(R.drawable.ic_car_gray)
            .into(binding?.imageCarLogo)
    }

    private fun showBottomNavMenu() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.VISIBLE
    }
}

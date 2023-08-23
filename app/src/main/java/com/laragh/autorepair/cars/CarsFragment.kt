package com.laragh.autorepair.cars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentCarsBinding
import com.laragh.autorepair.models.Car

class CarsFragment : Fragment() {

    private var binding: FragmentCarsBinding? = null
    lateinit var carsAdapter: CarsAdapter
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        userViewModel.getUserCars()
        userViewModel.getUserCarsLiveData.observe(viewLifecycleOwner) { cars ->
            carsAdapter.differ.submitList(cars as MutableList<Car>)
        }
        addCarButton()

        carsAdapter.setOnItemClickListener {
            userViewModel.selectCar(it)
            binding?.root?.findNavController()?.popBackStack()
            binding?.root?.findNavController()?.navigate(
                R.id.action_signInFragment_to_homeFragment
            )
        }
    }

    private fun initAdapter() {
        carsAdapter = CarsAdapter()
        binding?.recyclerView?.apply {
            adapter = carsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun addCarButton() {
        binding?.buttonAddCar?.setOnClickListener {
            binding?.root?.findNavController()?.popBackStack()
            binding?.root?.findNavController()?.navigate(
                R.id.action_signInFragment_to_addCarFragment
            )
        }
    }
}
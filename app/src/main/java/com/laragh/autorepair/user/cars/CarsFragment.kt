package com.laragh.autorepair.user.cars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentCarsBinding
import com.laragh.autorepair.user.models.Car

class CarsFragment : BaseFragment<FragmentCarsBinding>() {

    lateinit var carsAdapter: CarsAdapter
    private val userViewModel: UserViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCarsBinding {
        return FragmentCarsBinding.inflate(inflater, container, false)
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
            binding.root.findNavController().popBackStack()
            binding.root.findNavController().navigate(
                R.id.action_addCarFragment_to_homeFragment
            )
        }
    }

    private fun initAdapter() {
        carsAdapter = CarsAdapter()
        binding.recyclerView.apply {
            adapter = carsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun addCarButton() {
        binding.buttonAddCar.setOnClickListener {
            binding.root.findNavController().popBackStack()
        }
    }
}
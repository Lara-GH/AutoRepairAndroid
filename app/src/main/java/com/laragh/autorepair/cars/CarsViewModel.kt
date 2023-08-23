package com.laragh.autorepair.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laragh.autorepair.UserRepository
import com.laragh.autorepair.models.Car

class CarsViewModel : ViewModel() {

    private val repository = UserRepository()

    private val mutableGetUserCarsLiveData: MutableLiveData<List<Car>> = MutableLiveData()
    val getUserCarsLiveData: LiveData<List<Car>> get() = mutableGetUserCarsLiveData

    fun getUserCars() {
        repository.getUserCars(mutableGetUserCarsLiveData)
    }
}
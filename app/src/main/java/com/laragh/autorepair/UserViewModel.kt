package com.laragh.autorepair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laragh.autorepair.models.Car

class UserViewModel: ViewModel() {

    private val repository = UserRepository()

    private val mutableGetUserCarsLiveData: MutableLiveData<List<Car>> = MutableLiveData()
    val getUserCarsLiveData: LiveData<List<Car>> get() = mutableGetUserCarsLiveData

    private val mutableSelectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> get() = mutableSelectedCar

    fun getCars() {
        repository.getUserCars(mutableGetUserCarsLiveData)
    }

    fun selectCar(car: Car) {
        mutableSelectedCar.value = car
    }
}
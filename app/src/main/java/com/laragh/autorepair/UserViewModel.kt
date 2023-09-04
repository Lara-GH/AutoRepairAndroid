package com.laragh.autorepair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laragh.autorepair.models.Car

open class UserViewModel: ViewModel() {

    private val repository = UserRepository()

    private val mutableGetUserCarsLiveData: MutableLiveData<List<Car>> = MutableLiveData()
    open val getUserCarsLiveData: LiveData<List<Car>> get() = mutableGetUserCarsLiveData

    private val mutableSelectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> get() = mutableSelectedCar

    fun getUserCars() {
        repository.getUserCars(mutableGetUserCarsLiveData)
    }

    fun selectCar(car: Car) {
        mutableSelectedCar.value = car
    }

    fun addedPhoto(addedPhotos: Boolean, carID: String, car: Car) {
        car.addedPhotos = addedPhotos
        mutableSelectedCar.value = car
        repository.addedPhoto(addedPhotos, carID)
    }

    open fun addCar(car: Car){
        repository.addCar(car, mutableGetUserCarsLiveData)
    }
}
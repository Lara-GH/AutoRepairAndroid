package com.laragh.autorepair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laragh.autorepair.models.Car
import com.laragh.autorepair.photo.CardViewItem

open class UserViewModel: ViewModel() {

    private val repository = UserRepository()

    private val mutableGetUserCarsLiveData: MutableLiveData<List<Car>> = MutableLiveData()
    open val getUserCarsLiveData: LiveData<List<Car>> get() = mutableGetUserCarsLiveData

    private val mutableSelectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> get() = mutableSelectedCar

    private var mutablePhotosListLiveData: MutableLiveData<List<CardViewItem>> = MutableLiveData()
    val getPhotosListLiveData: LiveData<List<CardViewItem>> get() = mutablePhotosListLiveData

    fun getUserCars() {
        repository.getUserCars(mutableGetUserCarsLiveData)
    }

    fun selectCar(car: Car) {
        mutableSelectedCar.value = car
    }

    open fun addCar(car: Car, int: String){
        repository.addCar(car, int, mutableGetUserCarsLiveData)
    }

    fun setPhotosList(list: List<CardViewItem>){
        mutablePhotosListLiveData.value = list
    }
}
package com.laragh.autorepair.user.addcar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.laragh.autorepair.UserViewModel

class AddCarViewModel : UserViewModel() {

    private val repository = AddCarRepository()

    private var year_ = ""
    private val mutableGetYearsLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val getYearsLiveData: LiveData<List<String>> get() = mutableGetYearsLiveData
    private val mutableGetMakesLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val getMakesLiveData: LiveData<List<String>> get() = mutableGetMakesLiveData
    private val mutableGetModelsLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val getModelsLiveData: LiveData<List<String>> get() = mutableGetModelsLiveData
    private val mutableGetEnginesLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val getEnginesLiveData: LiveData<List<String>> get() = mutableGetEnginesLiveData

    fun getYears() {
        repository.getYears(mutableGetYearsLiveData)
    }

    fun getMakes(year: String) {
        year_ = year
        repository.getMakes(year, mutableGetMakesLiveData)
    }

    fun getModels(make: String) {
        repository.getModels(year_, make, mutableGetModelsLiveData)
    }

    fun getEngines(model: String){
        repository.getEngines(model, mutableGetEnginesLiveData)
    }

//    private val userRepository = UserRepository()
//    private val mutableGetUserCarsLiveData: MutableLiveData<List<Car>> = MutableLiveData()
//    override val getUserCarsLiveData: LiveData<List<Car>> get() = mutableGetUserCarsLiveData
//
//    override fun addCar(car: Car, int: String){
//        userRepository.addCar(car, int, mutableGetUserCarsLiveData)
//    }
}
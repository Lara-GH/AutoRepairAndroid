package com.laragh.autorepair.addcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddCarViewModel : ViewModel() {

    private val repository = AddCarRepository()

    private var year_ = ""
    val getYearsLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val getMakesLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val getModelsLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val getEnginesLiveData: MutableLiveData<List<String>> = MutableLiveData()

    fun getYears() {
        repository.getYears(getYearsLiveData)
    }

    fun getMakes(year: String) {
        year_ = year
        repository.getMakes(year, getMakesLiveData)
    }

    fun getModels(make: String) {
        repository.getModels(year_, make, getModelsLiveData)
    }

    fun getEngines(model: String){
        repository.getEngines(model, getEnginesLiveData)
    }
}
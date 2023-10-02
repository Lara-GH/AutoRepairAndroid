package com.laragh.autorepair.user.addcar

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.laragh.autorepair.user.models.CarItem

class AddCarRepository {

    private var engines: MutableList<CarItem> = mutableListOf()

    fun getYears(liveData: MutableLiveData<List<String>>) {
        val years = Firebase.database.reference.child("year")
        years.get().addOnSuccessListener {
            val list = mutableListOf<String>()
            if (it.exists()) {
                for (snapshot in it.children) {
                    val year: String? = snapshot.key
                    if (year != null) {
                        list.add(year)
                    }
                }
                liveData.postValue(list)
            }
        }
    }

    fun getMakes(year: String, liveData: MutableLiveData<List<String>>) {
        val makes = Firebase.database.reference.child(year)
        makes.get().addOnSuccessListener {
            val list = mutableListOf<String>()
            if (it.exists()) {
                for (snapshot in it.children) {
                    val make: String? = snapshot.key
                    if (make != null) {
                        list.add(make)
                    }
                }
                liveData.postValue(list)
            }
        }
    }

    fun getModels(year: String, model: String, liveData: MutableLiveData<List<String>>) {

        val models = Firebase.database.reference.child(year).child(model)
        models.get().addOnSuccessListener {

            val list = mutableListOf<String>()
            if (it.exists()) {

                for (snapshot in it.children) {
                    val car: CarItem? = snapshot.getValue(CarItem::class.java)

                    if (car?.model != null) {
                        list.add(car.model)
                        engines.add(car)
                    }
                    liveData.postValue(list)
                }
            } else {
                println("1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!! Failed to read value.")
            }
        }.addOnFailureListener {
            println("2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!! Failed to read value.")
        }
    }

    fun getEngines(model_: String, liveData: MutableLiveData<List<String>>) {
        for (model in engines) {
            if (model.model == model_) {
                model.engine?.let {
                    liveData.postValue(it)
                }
            }
        }
    }
}
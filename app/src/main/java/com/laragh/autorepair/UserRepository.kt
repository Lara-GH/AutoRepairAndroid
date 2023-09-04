package com.laragh.autorepair

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.laragh.autorepair.models.Car
import com.laragh.autorepair.utils.Constants.USER

class UserRepository {

    private val userID = FirebaseAuth.getInstance().currentUser!!.uid
    fun getUserCars(liveData: MutableLiveData<List<Car>>) {
        Firebase.database.reference.child(USER).child(userID).get().addOnSuccessListener {
            val list = mutableListOf<Car>()
            if (it.exists()) {
                for (snapshot in it.children) {
                    val car: Car? = snapshot.getValue(Car::class.java)
                    if (car != null) {
                        list.add(car)
                    }
                }
                liveData.postValue(list)
            }
        }
    }

    fun addCar(car: Car, liveData: MutableLiveData<List<Car>>) {
        val newRef = Firebase.database.reference.child(USER).child(userID).push()
        car.id = newRef.key.toString()
        newRef.setValue(car)
        getUserCars(liveData)
    }
}
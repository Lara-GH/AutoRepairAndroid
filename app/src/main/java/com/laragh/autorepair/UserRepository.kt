package com.laragh.autorepair

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.laragh.autorepair.models.Car

class UserRepository {

    fun getUserCars(liveData: MutableLiveData<List<Car>>){
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        Firebase.database.reference.child("users").child(userID).get().addOnSuccessListener {
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
}
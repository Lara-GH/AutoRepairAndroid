package com.laragh.autorepair

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.laragh.autorepair.user.models.Car
import com.laragh.autorepair.utils.Constants.CARS
import com.laragh.autorepair.utils.Constants.USER

class UserRepository {

    private val userID = FirebaseAuth.getInstance().currentUser!!.uid
    private val ref = Firebase.database.reference.child(USER).child(userID)

    fun createUser() {
        ref.child("isUser").setValue(true)
    }

    fun getUserCars(liveData: MutableLiveData<List<Car>>) {
        ref.child(CARS).get().addOnSuccessListener {
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
        val newRef = ref.child(CARS).push()
        car.id = newRef.key.toString()
        newRef.setValue(car)
        getUserCars(liveData)
    }

    fun addedPhoto(addedPhotos: Boolean, carID: String) {
        ref.child(CARS).child(carID).child("addedPhotos").setValue(addedPhotos)
    }

    fun checkUserAccessLevel(liveData: MutableLiveData<String>) {
        ref.get().addOnSuccessListener {
            if (it.exists()) {
                if (it.child("isAdmin").exists()) {
                    liveData.postValue("admin")
                } else if (it.child("isMechanic").exists()) {
                    liveData.postValue("mechanic")
                } else liveData.postValue("user")
            }
        }
    }
}
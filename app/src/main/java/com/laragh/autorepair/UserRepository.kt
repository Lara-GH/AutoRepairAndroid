package com.laragh.autorepair

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.laragh.autorepair.user.models.Car
import com.laragh.autorepair.user.models.Task
import com.laragh.autorepair.utils.Constants.CARS
import com.laragh.autorepair.utils.Constants.NAME
import com.laragh.autorepair.utils.Constants.PHONE
import com.laragh.autorepair.utils.Constants.TASKS
import com.laragh.autorepair.utils.Constants.USER
import java.util.*

class UserRepository {

    private val userID = FirebaseAuth.getInstance().currentUser!!.uid
    private val refUser = Firebase.database.reference.child(USER).child(userID)

    fun createUser() {
        refUser.child("isUser").setValue(true)
    }

    fun getUserCars(liveData: MutableLiveData<List<Car>>) {
        refUser.child(CARS).get().addOnSuccessListener {
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
        val newRef = refUser.child(CARS).push()
        car.id = newRef.key.toString()
        newRef.setValue(car)
        getUserCars(liveData)
    }

    fun addedPhoto(addedPhotos: Boolean, carID: String) {
        refUser.child(CARS).child(carID).child("addedPhotos").setValue(addedPhotos)
    }

    fun checkUserAccessLevel(liveData: MutableLiveData<String>) {
        refUser.get().addOnSuccessListener {
            if (it.exists()) {
                if (it.child("isAdmin").exists()) {
                    liveData.postValue("admin")
                } else if (it.child("isMechanic").exists()) {
                    liveData.postValue("mechanic")
                } else liveData.postValue("user")
            }
        }
    }

    fun checkIfNameAndPhoneExist(liveData: MutableLiveData<String>) {
        refUser.get().addOnSuccessListener {
            if (it.exists()) {
                if (it.child("name").exists()) {
                    if (it.child("phone").exists()) {
                        liveData.postValue("nameAndPhoneExist")
                    } else liveData.postValue("nameExist")
                } else if (it.child("phone").exists()) {
                    liveData.postValue("phoneExist")
                } else liveData.postValue("nameAndPhoneDoNotExist")
            }
        }
    }

    fun setUserName(name: String){
        refUser.child(NAME).setValue(name)
    }

    fun setUserPhone(phone: String){
        refUser.child(PHONE).setValue(phone)
    }

    @SuppressLint("SimpleDateFormat")
    fun createTask(carID: String, description: String){
        val newRef = refUser.child(CARS).child(carID).child(TASKS).push()
        val date = SimpleDateFormat("MM/dd/yyyy").format(Date())
        val task = Task("", "new", date, description)
        task.id = newRef.key.toString()
        newRef.setValue(task)
    }
}
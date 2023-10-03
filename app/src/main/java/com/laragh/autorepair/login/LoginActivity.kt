package com.laragh.autorepair.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.admin.AdminActivity
import com.laragh.autorepair.databinding.ActivityLoginBinding
import com.laragh.autorepair.mechanic.MechanicActivity
import com.laragh.autorepair.user.UserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), IRouterLoginActivity {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            checkUserAccessLevel()
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                delay(2500)
                setContentView(binding.root)
                supportFragmentManager.beginTransaction().add(R.id.container, SignInFragment())
                    .commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            checkUserAccessLevel()
        }
    }

    private fun checkUserAccessLevel() {
        userViewModel.accessLevel.observe(this) {
            when (it) {
                "user" -> {
                    startActivity(Intent(this, UserActivity::class.java))
                }
                "admin" -> {
                    startActivity(Intent(this, AdminActivity::class.java))
                }
                "mechanic" -> {
                    startActivity(Intent(this, MechanicActivity::class.java))
                }
            }
        }
        userViewModel.checkUserAccessLevel()
    }

    override fun openSignUpFragment() {
        supportTransaction(SignUpFragment())
    }

    private fun supportTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}

interface IRouterLoginActivity {
    fun openSignUpFragment()
}
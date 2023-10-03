package com.laragh.autorepair.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.user.UserActivity
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.admin.AdminActivity
import com.laragh.autorepair.databinding.FragmentSignInBinding
import com.laragh.autorepair.mechanic.MechanicActivity

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val userViewModel: UserViewModel by activityViewModels()
    private var listener: IRouterLoginActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IRouterLoginActivity){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding {
        return FragmentSignInBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewNotRegistered.setOnClickListener {
            listener?.openSignUpFragment()
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, SignUpFragment()).commit()
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                       checkUserAccessLevel()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), R.string.empty_fields, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            checkUserAccessLevel()
        }
    }

    private fun checkUserAccessLevel(){
        userViewModel.accessLevel.observe(viewLifecycleOwner) {
            when (it) {
                "user" -> {
                    startActivity(Intent(activity, UserActivity::class.java))
                }
                "admin" -> {
                    startActivity(Intent(activity, AdminActivity::class.java))
                }
                "mechanic" -> {
                    startActivity(Intent(activity, MechanicActivity::class.java))
                }
            }
        }
        userViewModel.checkUserAccessLevel()
    }
}
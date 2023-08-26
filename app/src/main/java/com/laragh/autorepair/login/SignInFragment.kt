package com.laragh.autorepair.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.R
import com.laragh.autorepair.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding {
        return FragmentSignInBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavMenu()
        binding.textviewNotRegistered.setOnClickListener {
            binding.root.findNavController().navigate(
                R.id.action_signInFragment_to_signUpFragment
            )
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.root.findNavController().navigate(
                            R.id.action_signInFragment_to_addCarFragment
                        )
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
            binding.root.findNavController().navigate(
                R.id.action_signInFragment_to_addCarFragment
            )
        }
    }

    private fun hideBottomNavMenu() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.GONE
    }
}
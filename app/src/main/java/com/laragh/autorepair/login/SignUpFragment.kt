package com.laragh.autorepair.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.laragh.autorepair.R
import com.laragh.autorepair.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var binding: FragmentSignUpBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        hideBottomNavMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.textviewAlreadyRegistered?.setOnClickListener {
            binding?.root?.findNavController()?.navigate(
                R.id.action_signUpFragment_to_signInFragment
            )
        }

        binding?.signUpButton?.setOnClickListener {
            val email = binding?.emailEt?.text.toString()
            val pass = binding?.passET?.text.toString()
            val confirmPass = binding?.confirmPassEt?.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding?.root?.findNavController()?.navigate(
                                R.id.action_signUpFragment_to_signInFragment
                            )
                        } else {
                            Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), R.string.password_is_not_matching, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), R.string.empty_fields, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideBottomNavMenu(){
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.GONE
    }
}
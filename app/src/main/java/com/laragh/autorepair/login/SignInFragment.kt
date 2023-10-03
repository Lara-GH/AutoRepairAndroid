package com.laragh.autorepair.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.R
import com.laragh.autorepair.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var listener: IRouterLoginActivity? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewNotRegistered.setOnClickListener {
            listener?.openSignUpFragment()
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        (activity as LoginActivity).checkUserAccessLevel()
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
            (activity as LoginActivity).checkUserAccessLevel()
        }
    }
}
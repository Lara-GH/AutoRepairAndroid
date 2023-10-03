package com.laragh.autorepair.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val userViewModel: UserViewModel by activityViewModels()
    private var listener: IRouterLoginActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
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

        binding.textviewAlreadyRegistered.setOnClickListener {
            listener?.openSignInFragment()
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passwordEt.text.toString()
            val confirmPass = binding.confirmPasswordEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            userViewModel.createUser()
                            listener?.openSignInFragment()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.password_is_not_matching,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), R.string.empty_fields, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.laragh.autorepair.user.adduserinfo

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColorStateList
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.laragh.autorepair.BaseFragment
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentAddUserInfoBinding

class AddUserInfoFragment : BaseFragment<FragmentAddUserInfoBinding>() {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddUserInfoBinding {
        return FragmentAddUserInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNextButtonColor()
    }

    private fun setNextButtonColor() {
        binding.nameEt.addTextChangedListener { it: Editable? ->
            isEmpty(it)
        }
        binding.phoneEt.addTextChangedListener { it: Editable? ->
            isEmpty(it)
        }
    }

    private fun isEmpty(it: Editable?) {
        if (!it.isNullOrEmpty()) {
            if (!binding.nameEt.text.isNullOrEmpty() && !binding.phoneEt.text.isNullOrEmpty()) {
                binding.buttonNext.isClickable = true
                binding.buttonNext.backgroundTintList =
                    getColorStateList(requireContext(), R.color.orange)
                binding.buttonNext.setOnClickListener {
                    userViewModel.setUserName(binding.nameEt.text.toString())
                    userViewModel.setUserPhone(binding.phoneEt.text.toString())
                }
            }
        } else {
            binding.buttonNext.isClickable = false
            binding.buttonNext.backgroundTintList =
                getColorStateList(requireContext(), R.color.gray)
        }
    }
}
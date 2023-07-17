package com.laragh.autorepair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.laragh.autorepair.databinding.FragmentAddCarBinding

class AddCarFragment : Fragment() {

    private var binding: FragmentAddCarBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        autoCompleteTextView()
    }

    private fun autoCompleteTextView(){
        val list = listOf("Honda", "Toyota", "Mazda", "Audi", "BMW")

        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, list)

        val autoCompleteTextView = view?.findViewById<AutoCompleteTextView>(R.id.make_filled)
        autoCompleteTextView?.setAdapter(adapter)

        autoCompleteTextView?.setOnItemClickListener{parent, view, position, id ->
            Toast.makeText(requireContext(), "Clicked item : ${autoCompleteTextView.text}",Toast.LENGTH_SHORT).show()
        }
    }
}
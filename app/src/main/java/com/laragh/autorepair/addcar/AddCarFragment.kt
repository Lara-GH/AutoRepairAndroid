package com.laragh.autorepair.addcar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.laragh.autorepair.R
import com.laragh.autorepair.UserViewModel
import com.laragh.autorepair.databinding.FragmentAddCarBinding
import com.laragh.autorepair.models.Car

class AddCarFragment : Fragment() {

    private var binding: FragmentAddCarBinding? = null
    private val viewModel: AddCarViewModel by lazy {
        ViewModelProvider(this)[AddCarViewModel::class.java]
    }
    private val userViewModel: UserViewModel by activityViewModels()
    var list = mutableListOf<Car>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomNavMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getCars()
        userViewModel.getUserCarsLiveData.observe(viewLifecycleOwner) { cars ->
            list = cars as MutableList<Car>
        }

        initAddCarButton()

        viewModel.getYears()
        viewModel.getYearsLiveData.observe(viewLifecycleOwner) { years ->
            autoCompleteTextView(years, R.id.year_filled)
        }
        viewModel.getMakesLiveData.observe(viewLifecycleOwner) { makes ->
            autoCompleteTextView(makes.sorted(), R.id.make_filled)
        }
        viewModel.getModelsLiveData.observe(viewLifecycleOwner) { models ->
            autoCompleteTextView(models.sorted(), R.id.model_filled)
        }
        viewModel.getEnginesLiveData.observe(viewLifecycleOwner) { engines ->
            autoCompleteTextView(engines.filterNotNull(), R.id.engine_filled)
        }
    }

    private fun initAddCarButton() {
        binding?.addCarButton?.setOnClickListener {
            val car = Car(
                binding!!.engineFilled.text.toString(),
                binding!!.makeFilled.text.toString(),
                binding!!.modelFilled.text.toString(),
                binding!!.yearFilled.text.toString()
            )
            list.add(car)
            userViewModel.selectCar(car)
            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            Firebase.database.reference.child("users").child(userID).setValue(list)

            binding?.root?.findNavController()?.navigate(
                R.id.action_addCarFragment_to_homeFragment
            )
        }
    }

    private fun autoCompleteTextView(list: List<String>, textView: Int) {
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, list.distinct())

        val autoCompleteTextView = view?.findViewById<AutoCompleteTextView>(textView)
        autoCompleteTextView?.setAdapter(adapter)
        autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->

            if (textView == R.id.year_filled) {
                clear(R.id.make_filled)
                clear(R.id.model_filled)
                clear(R.id.engine_filled)
                viewModel.getMakes(autoCompleteTextView.text.toString())
            }

            if (textView == R.id.make_filled) {
                clear(R.id.model_filled)
                clear(R.id.engine_filled)
                viewModel.getModels(autoCompleteTextView.text.toString())
            }

            if (textView == R.id.model_filled) {
                clear(R.id.engine_filled)
                viewModel.getEngines(autoCompleteTextView.text.toString())
            }

            Toast.makeText(
                requireContext(),
                "Clicked item : ${autoCompleteTextView.text}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun clear(textView_: Int) {
        val array: Array<String> = emptyArray()
        val adapterEmpty = ArrayAdapter(requireContext(), R.layout.drop_down_item, array)
        val textView = view?.findViewById<AutoCompleteTextView>(textView_)
        textView?.setAdapter(adapterEmpty)
        textView?.setText("", false)
    }

    private fun hideBottomNavMenu() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_menu)
        navView.visibility = View.GONE
    }
}
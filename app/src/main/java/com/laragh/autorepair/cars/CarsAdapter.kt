package com.laragh.autorepair.cars

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laragh.autorepair.R
import com.laragh.autorepair.databinding.ItemCarBinding
import com.laragh.autorepair.models.Car
import okhttp3.internal.lowercase

class CarsAdapter: RecyclerView.Adapter<CarsAdapter.CarsViewHolder>() {

    private lateinit var binding: ItemCarBinding
    private lateinit var context: Context

    inner class CarsViewHolder : RecyclerView.ViewHolder(binding.root)

    private val callback = object : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(
            oldItem: Car,
            newItem: Car
        ): Boolean {
            return oldItem.engine == newItem.engine
        }

        override fun areContentsTheSame(
            oldItem: Car,
            newItem: Car
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCarBinding.inflate(inflater, parent, false)
        context = parent.context
        return CarsViewHolder()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(context)
                .load("https://www.carlogos.org/car-logos/${car.make?.lowercase()}-logo.png")
                .placeholder(R.drawable.ic_car_gray_extrasmall)
                .error(R.drawable.ic_car_gray_extrasmall)
                .into(binding.carImage)
            binding.textMakeModel.text = ("${car.make}  ${car.model}")
            binding.textEngineYear.text = ("${car.engine}  ${car.year}")

            setOnClickListener {
                onItemClickListener?.let { it(car) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Car) -> Unit)? = null

    fun setOnItemClickListener(listener: (Car) -> Unit) {
        onItemClickListener = listener
    }
}
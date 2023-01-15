package com.isgarsi.spanbbowl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isgarsi.spanbbowl.databinding.RvTableItemBinding
import com.isgarsi.spanbbowl.models.WeatherTableItem

class WeatherTableAdapter (private var mList: List<WeatherTableItem>) :
    RecyclerView.Adapter<WeatherTableAdapter.ViewHolder>() {

    // Hacemos el viewholder como clase interna para que pueda acceder a los elementos de la clase padre
    inner class ViewHolder(val binding: RvTableItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvTableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                binding.tvDice.text = this.dice
                binding.tvDescription.text = this.description
                binding.tvName.text = this.name
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

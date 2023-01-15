package com.isgarsi.spanbbowl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isgarsi.spanbbowl.databinding.RvSkillsItemBinding
import com.isgarsi.spanbbowl.models.SkillItem

class SkillItemsAdapter(private var mList: List<SkillItem>) :
    RecyclerView.Adapter<SkillItemsAdapter.ViewHolder>() {

    // Hacemos el viewholder como clase interna para que pueda acceder a los elementos de la clase padre
    inner class ViewHolder(val binding: RvSkillsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvSkillsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                binding.tvSkillName.text = this.name
                binding.tvDescription.text = this.description
                binding.expandedView.visibility = if (this.expanded) View.VISIBLE else View.GONE

                binding.cardLayout.setOnClickListener {
                    this.expanded = !this.expanded
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

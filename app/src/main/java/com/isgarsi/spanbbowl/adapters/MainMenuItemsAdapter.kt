package com.isgarsi.spanbbowl.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igs.passwordmanager.listeners.RecyclerMainActivityListener
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.MainmenuRvItemBinding
import com.isgarsi.spanbbowl.models.MainMenuItem
import com.isgarsi.spanbbowl.utils.inflate

class MainMenuItemsAdapter (private val items: ArrayList<MainMenuItem>,
                            private val listener: RecyclerMainActivityListener,
                            private val context: Context) : RecyclerView.Adapter<MainMenuItemsAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        val itemBinding = MainmenuRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    class MyViewHolder(private val itemBinding: MainmenuRvItemBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: MainMenuItem, listener: RecyclerMainActivityListener){
            itemBinding.textViewItem.text = item.optionName
            itemBinding.imageItem.setImageResource(item.imageId)
            itemBinding.root.setOnClickListener() {listener.onItemSelected(item)}
        }
    }
}
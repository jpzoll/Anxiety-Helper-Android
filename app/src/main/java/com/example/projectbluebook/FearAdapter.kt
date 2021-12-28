package com.example.projectbluebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_fear.view.*

class FearAdapter(
    private var fearList: List<Fear>
) :
    RecyclerView.Adapter<FearAdapter.FearViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FearViewHolder {
        val fearItemView = LayoutInflater.from(parent.context).inflate(R.layout.item_fear,
            parent, false)

        return FearViewHolder(fearItemView)
    }

    override fun onBindViewHolder(holder: FearViewHolder, position: Int) {
        val currentFear = fearList[position]

        holder.titleView.text = currentFear.title
        holder.cbView.isChecked = currentFear.isSelected
        holder.cbView.setOnCheckedChangeListener { compoundButton, b ->
           currentFear.isSelected = holder.cbView.isChecked
        }
    }

    override fun getItemCount(): Int {
        return fearList.size
    }
    inner class FearViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.tv_title
        val cbView: CheckBox = itemView.cb_select
    }
}
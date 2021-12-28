package com.example.projectbluebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.worksheet_item.view.*

class WorksheetAdapter(
    private val worksheetList: List<Worksheet>,
    private val listener: OnWorksheetClickListener
) :
    RecyclerView.Adapter<WorksheetAdapter.WorksheetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksheetViewHolder {
        val worksheetItemView = LayoutInflater.from(parent.context).inflate(R.layout.worksheet_item,
        parent, false)

        return WorksheetViewHolder(worksheetItemView)
    }

    override fun onBindViewHolder(holder: WorksheetViewHolder, position: Int) {
        val currentWorksheet = worksheetList[position]

        holder.titleView.text = currentWorksheet.title
        holder.dateView.text = currentWorksheet.date
    }

    override fun getItemCount(): Int {
        return worksheetList.size
    }

    inner class WorksheetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val titleView: TextView = itemView.tv_title
        val dateView: TextView = itemView.tv_date
        val deleteView: ImageView = itemView.img_delete

        init {
            itemView.setOnClickListener(this)
            deleteView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (v == itemView) {
                    listener.onWorksheetClick(position)
                    println("bindingAdapterPosition = " + position)
                } else if (v == deleteView) {
                    listener.onWorksheetClickDelete(position)
                    println("bindingAdapterPosition = " + position)
                }

            }
        }


    }

    interface OnWorksheetClickListener {
        fun onWorksheetClick(position: Int)
        fun onWorksheetClickDelete(position: Int)
    }
}
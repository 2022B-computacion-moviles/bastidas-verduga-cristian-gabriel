package com.example.googletasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.checkbox.MaterialCheckBox
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val context: Context,
    private val list: List<TaskElement>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: MaterialCheckBox
        val title: TextView

        init {
            checkBox = itemView.findViewById(R.id.checkbox)
            title = itemView.findViewById(R.id.text_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = list[position]
        holder.title.text = element.title
        holder.checkBox.isChecked = element.checked
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            element.checked = isChecked
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
package com.example.googletasks

import MenuElement
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val context: Context,
    private val list: List<MenuElement>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView
        val title: TextView

        init {
            icon = itemView.findViewById(R.id.iv_icon)
            title = itemView.findViewById(R.id.tv_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_dialog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = list[position]
        holder.title.text = element.title
        when (element.icon) {
            0 -> holder.icon.setImageResource(R.drawable.baseline_add_24)
            1 -> holder.icon.setImageResource(R.drawable.baseline_import_export_24)
            2 -> holder.icon.setImageResource(R.drawable.baseline_list_alt_24)
            3 -> holder.icon.setImageResource(R.drawable.baseline_star_24)
            4 -> holder.icon.setImageResource(R.drawable.baseline_account_circle_24)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
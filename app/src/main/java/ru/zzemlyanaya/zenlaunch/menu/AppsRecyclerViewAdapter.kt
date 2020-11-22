/*
 * *
 *  * Created by Eugeniya Zemlyanaya (@zzemlyanaya) on 22.11.20 19:09
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 22.11.20 19:02
 *
 */

package ru.zzemlyanaya.zenlaunch.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ru.zzemlyanaya.zenlaunch.R


class AppsRecyclerViewAdapter(
    private val onClick: (AppInfo) -> Unit,
    private val onLongClick: (AppInfo) -> Boolean,
    private var values: List<AppInfo>
) : RecyclerView.Adapter<AppsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if(item.label.length > 16)
            holder.label.text = "${item.label.slice(0..16)}..."
        else
            holder.label.text = item.label
        holder.itemView.setOnClickListener { onClick(item) }
        holder.itemView.setOnLongClickListener { onLongClick(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: AppCompatTextView = view.findViewById(R.id.textAppLabel)
    }
}
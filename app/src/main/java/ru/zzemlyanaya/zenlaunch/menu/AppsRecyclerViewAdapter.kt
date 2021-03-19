/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 19.03.2021, 19:18
 */

package ru.zzemlyanaya.zenlaunch.menu

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ru.zzemlyanaya.zenlaunch.Accents
import ru.zzemlyanaya.zenlaunch.App
import ru.zzemlyanaya.zenlaunch.App.Companion.prefs
import ru.zzemlyanaya.zenlaunch.R
import java.util.*
import kotlin.collections.ArrayList


open class AppsRecyclerViewAdapter(
    private val onClick: (AppInfo) -> Unit,
    private val onLongClick: ((AppInfo) -> Boolean)?,
    private var values: List<AppInfo>
) : RecyclerView.Adapter<AppsRecyclerViewAdapter.ViewHolder>() {

    private val copy = ArrayList<AppInfo>()
    private val accent = prefs.accent.get()

    init {
        copy.addAll(values)
    }

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
        holder.label.setTextColor(Color.parseColor(accent))

        holder.itemView.setOnClickListener { onClick(item) }
        if (onLongClick != null)
            holder.itemView.setOnLongClickListener { onLongClick.invoke(item)}
    }

    override fun getItemCount(): Int = values.size

    fun filter(query: String) {
        val query = query.toLowerCase()
        val result = ArrayList<AppInfo>()
        if (query.isBlank())
            result.addAll(copy)
        else
            for (app in copy)
                if (app.label.toLowerCase().contains(query))
                    result.add(app)
        if (result.size == 1)
            onClick.invoke(result.first())
        else {
            (values as ArrayList<AppInfo>).clear()
            (values as ArrayList<AppInfo>).addAll(result)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: AppCompatTextView = view.findViewById(R.id.textAppLabel)
    }
}
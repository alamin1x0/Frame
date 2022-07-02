package com.developeralamin.frame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FramAdapter(val context: Context, val list : List<FramModel>, val onClick: OnClick) : RecyclerView.Adapter<FramAdapter.FrameViewHolder>() {

    class FrameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.framItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        return FrameViewHolder(LayoutInflater.from(context).inflate(R.layout.fram_layout_item, parent,false))

    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        Glide.with(context).load(list[position].drawab).into(holder.imageView)

        holder.imageView.setOnClickListener {
            onClick.frameClick(position)
        }
    }

    override fun getItemCount(): Int {
       return  list.size
    }
}
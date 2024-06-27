package com.example.semestralka.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.semestralka.R
import com.example.semestralka.databinding.ViewholderPicListBinding

class PicListAdapter(val items:MutableList<String>,var picMain: ImageView):
    RecyclerView.Adapter<PicListAdapter.Viewholder>() {

        private var selectedPosition=-1
        private var lastSelectedPosition=-1
    private lateinit var context: Context

    inner class Viewholder(val binding:ViewholderPicListBinding):
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicListAdapter.Viewholder {
        context = parent.context
        val binding=ViewholderPicListBinding.inflate(LayoutInflater.from(context),parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: PicListAdapter.Viewholder, position: Int) {
       Glide.with(holder.itemView.context)
           .load(items[position])
           .into(holder.binding.picList)

        holder.binding.root.setOnClickListener{
            lastSelectedPosition=selectedPosition
            selectedPosition=position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)

            Glide.with(holder.itemView.context)
                .load(items[position])
                .into(picMain)
        }
        if(selectedPosition===position){
            holder.binding.picLayout.setBackgroundResource(R.drawable.grey_bg_selected)
        }else{
            holder.binding.picList.setBackgroundResource(R.drawable.grey_bg)
        }
    }

    override fun getItemCount(): Int =items.size
}
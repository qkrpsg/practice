package com.threeducks.practice.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.threeducks.practice.R
import kotlinx.android.synthetic.main.item_layout.view.*

class CustomAdapter(private val items: ArrayList<Item>):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked: ${item.tags}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    } // onBindViewHolder()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CustomAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        return CustomAdapter.ViewHolder(inflatedView)
    } // onCreateViewHolder()

    class ViewHolder(v: View):

        RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bind(listener: View.OnClickListener, items: Item) {
            Glide.with(view).load(items.imageURL).into(view.iv_preView)
            view.tv_threeDucks.text = "ThreeDucks"
            view.tv_tags.text = items.tags
            view.setOnClickListener(listener)
        } // bind()

    } // class :: ViewHolder

} // class :: CustomAdapter
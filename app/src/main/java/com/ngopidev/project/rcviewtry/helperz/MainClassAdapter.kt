package com.ngopidev.project.rcviewtry.helperz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.ClipData.Item




/**
 *   created by Irfan Assidiq on 2019-09-25
 *   email : assidiq.irfan@gmail.com
 **/
class MainClassAdapter : RecyclerView.Adapter<MainClassAdapter.MainViewHolder>{

    lateinit var itemList : MutableList<ItemData>
    lateinit var ctx : Context

    constructor(){}
    constructor(ctx : Context , itemList : MutableList<ItemData>){
        this.ctx = ctx
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(ctx).inflate(com.ngopidev.project.rcviewtry.R.layout.cart_list_item, parent, false)
        return MainViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val dataItem = itemList.get(position)
        holder.name.text = dataItem.name
        holder.description.text = dataItem.description
        holder.price.text = dataItem.price.toString()
        Glide.with(ctx)
            .load(dataItem.thumbnail)
            .error(com.ngopidev.project.rcviewtry.R.mipmap.ic_launcher)
            .into(holder.thumbnail)
    }

    class MainViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var view_background : RelativeLayout
        var delete_icon : ImageView
        var view_foreground : RelativeLayout
        var thumbnail : ImageView
        var name : TextView
        var description : TextView
        var price : TextView
        init {
            view_background = itemView.findViewById(com.ngopidev.project.rcviewtry.R.id.view_background)
            delete_icon = itemView.findViewById(com.ngopidev.project.rcviewtry.R.id.delete_icon)
            view_foreground = itemView.findViewById(com.ngopidev.project.rcviewtry.R.id.view_foreground)
            thumbnail = itemView.findViewById(com.ngopidev.project.rcviewtry.R.id.thumbnail)
            name = itemView.findViewById(com.ngopidev.project.rcviewtry.R.id.name)
            description = itemView.findViewById(com.ngopidev.project.rcviewtry.R.id.description)
            price = itemView.findViewById(com.ngopidev.project.rcviewtry.R.id.price)

        }
    }

    fun removeItem(position : Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: ItemData, position: Int) {
        itemList.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }
}
package com.kdapps.byteseq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item_layout.view.*

class MyUserAdapter(val userList: ArrayList<UserEntity>): RecyclerView.Adapter<MyUserAdapter.viewHolder>() {
    class viewHolder(view: View):RecyclerView.ViewHolder(view){
        val tv_id = view.tv_id
        val tv_key = view.tv_key
        val tv_date = view.tv_date
        val tv_sold = view.tv_sold
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val id = userList[position].id
        val key = userList[position].key
        val date = userList[position].date
        val sold = userList[position].sold

        holder.tv_id.setText(id)
        holder.tv_key.setText(key)
        holder.tv_date.setText(date)
        holder.tv_sold.setText(sold)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
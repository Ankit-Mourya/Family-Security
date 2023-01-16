package com.example.familysafety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(private val listMember: List<Member_Model>) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_member,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listMember[position]
        holder.name.text = item.name
        holder.address.text=item.address
    }

    override fun getItemCount(): Int {
       return listMember.size
    }
    class ViewHolder(private val item: View): RecyclerView.ViewHolder(item) {
              val imageuser = item.findViewById<ImageView>(R.id.first_img)
              val name = item.findViewById<TextView>(R.id.name)
              val address=item.findViewById<TextView>(R.id.address)
    }
}
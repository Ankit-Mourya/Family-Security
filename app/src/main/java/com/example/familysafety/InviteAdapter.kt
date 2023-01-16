package com.example.familysafety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InviteAdapter(private val contact:List<Contactmodel>): RecyclerView.Adapter<InviteAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
           val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_invite,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contact[position]
         holder.name.text =item.name
    }

    override fun getItemCount(): Int {
        return contact.size
    }
    class ViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        val imageuser = item.findViewById<ImageView>(R.id.forth_img)
        val name=item.findViewById<TextView>(R.id.user_name)
        val invite =item.findViewById<LinearLayout>(R.id.Linear_invite)

    }

}
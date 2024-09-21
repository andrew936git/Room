package com.example.room


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val context: Context):
    RecyclerView.Adapter<CustomAdapter.ContactViewHolder>(){

        private val contacts = ArrayList<Contact>()

    inner class ContactViewHolder(itemView: View):
    RecyclerView.ViewHolder(itemView) {
        private val surnameTV: TextView = itemView.findViewById(R.id.surnameTV)
        private val phoneTV: TextView = itemView.findViewById(R.id.phoneTV)


        fun bind(contact: Contact) {
            surnameTV.text = contact.surname
            phoneTV.text = contact.phoneNumber
        }


    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Contact>){
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val viewHolder = ContactViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.list_item, parent, false))
        return viewHolder
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }
}
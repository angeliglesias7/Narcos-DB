package com.example.narcosdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.narcosdb.R
import com.example.narcosdb.entity.Contact


class ContactAdapter(context: Context?, items: ArrayList<Contact>?): BaseAdapter() {

    private var context: Context? = context
    private var items: ArrayList<Contact>? = items

    override fun getCount(): Int {
        return items!!.size
    }

    fun getItems(): ArrayList<Contact>? {
        return items
    }

    override fun getItem(position: Int): Any {
        return items!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var v: View? = convertView
        if (convertView == null) {
            val inf =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = inf.inflate(R.layout.item_contact, null)
        }
        val contact = getItem(position) as Contact
        val surname = v?.findViewById(R.id.surname) as TextView
        surname.text = contact.surname
        val passport = v.findViewById(R.id.passport) as TextView
        passport.text = contact.passport
        val phone = v.findViewById(R.id.phone) as TextView
        phone.text = contact.phone
        val address = v.findViewById(R.id.address) as TextView
        address.text = contact.address
        return v
    }
}
package com.example.narcosdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.adapter.ContactAdapter
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.fragments.detailsFragments.ContactDetailsFragment
import com.example.narcosdb.viewmodel.ContactViewModel


class ContactFragment : Fragment(), AdapterView.OnItemClickListener {

    private var contactViewModel : ContactViewModel? = null
    private var contactList = ArrayList<Contact>()
    private var addContact : ImageButton? = null
    private lateinit var listView : ListView
    private var contactAdapter : ContactAdapter? = null
    private var frameLayout: DrawerLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_contact, container, false)
        frameLayout = v.findViewById(R.id.contact_layout)
        listView = v.findViewById(R.id.contactListView)
        contactViewModel = activity?.application?.let { ContactViewModel(it) }
        getAll()

        addContact = v.findViewById<ImageButton>(R.id.addContact)
        addContact?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, ContactDetailsFragment())
            transaction?.addToBackStack("newcontact")
            transaction?.commit()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val contact = contactAdapter!!.getItem(position) as Contact
            val bundle = Bundle()
            bundle.putString("surname", contact.surname)
            bundle.putString("passport", contact.passport)
            bundle.putString("phone", contact.phone)
            bundle.putString("address", contact.address)
            val contactDetailsFragment = ContactDetailsFragment()
            contactDetailsFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.framelayout_id, contactDetailsFragment)
                .addToBackStack("newContact").commit()
        }

        return v
    }

    fun onItemSelected() {
        val list = contactAdapter!!.getItems()
        contactAdapter = ContactAdapter(context, list)
        listView.adapter = contactAdapter
    }

    fun onNothingSelected() {}

    private fun getAll() {
        contactViewModel?.getAll()?.observe(
            viewLifecycleOwner,
            Observer<List<Contact>> { list -> //called every time data changes
                contactList = list as java.util.ArrayList<Contact>
                println(list.size)
                contactAdapter = ContactAdapter(this.context, contactList)
                listView.adapter = contactAdapter
            })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    private fun closeDrawer() {
        if (frameLayout!!.isDrawerOpen(GravityCompat.START)) {
            frameLayout!!.closeDrawer(GravityCompat.START)
        }
    }

}
package com.example.narcosdb.fragments.detailsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.narcosdb.R
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.viewmodel.ContactViewModel
import kotlinx.coroutines.runBlocking


class ContactDetailsFragment : Fragment() {
    private var frameLayout: DrawerLayout? = null
    private var eContactSurname: EditText? = null
    private var eContactPassport: EditText? = null
    private var eContactPhone: EditText? = null
    private var eContactAddress: EditText? = null
    private var saveContact: Button? = null
    private var deleteContact: Button? = null
    private var isNewContact: Boolean? = null
    private var contactViewModel: ContactViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_contact_details, container, false)
        frameLayout = v.findViewById<View>(R.id.newContact_layout) as DrawerLayout
        saveContact = v.findViewById(R.id.saveContact)
        deleteContact = v.findViewById(R.id.deleteContact)
        eContactSurname = v.findViewById(R.id.eContactSurname)
        eContactPassport = v.findViewById(R.id.eContactPassport)
        eContactPhone = v.findViewById(R.id.eContactPhone)
        eContactAddress = v.findViewById(R.id.eContactAddress)
        contactViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)
        try {
            val bundle = this.arguments
            if (bundle != null) {
                val name = bundle.getString("surname")
                val quality = bundle.getString("passport")
                val price = bundle.getString("phone")
                val description = bundle.getString("address")
                eContactSurname?.setText(name)
                eContactPassport?.setText(quality)
                eContactPhone?.setText(price)
                eContactAddress?.setText(description)
                isNewContact = false
            } else {
                isNewContact = true
                deleteContact?.visibility = View.GONE
            }
        } catch (e: Exception) {
            println(e.message)
        }
        saveContact?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val contact = contact
                if (isNewContact == true) {
                    runBlocking {
                        val result = contactViewModel?.insert(contact)
                        if(result.toString() == "-1"){
                            Toast.makeText(context,
                                HtmlCompat.fromHtml("<font color='red'>"+getString(R.string.create_contact_error)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(context,
                                HtmlCompat.fromHtml("<font color='green'>"+getString(R.string.create_contact)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    runBlocking {
                        val result = contactViewModel?.update(contact)
                        if(result.toString() == "-1"){
                            Toast.makeText(context,
                                HtmlCompat.fromHtml("<font color='red'>"+getString(R.string.update_contact_error)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(context,
                                HtmlCompat.fromHtml("<font color='green'>"+getString(R.string.update_contact)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
        deleteContact?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val contact = contact
                runBlocking {
                    val result = contactViewModel?.delete(contact)
                    if(result.toString() == "-1"){
                        Toast.makeText(context,
                            HtmlCompat.fromHtml("<font color='red'>"+getString(R.string.delete_contact_error)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,
                            HtmlCompat.fromHtml("<font color='green'>"+getString(R.string.delete_contact)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        return v
    }

    private fun checkFields(): Boolean {
        return !eContactSurname?.text.toString().isNullOrEmpty() && !eContactPassport?.text.toString().isNullOrEmpty() &&
                !eContactPhone?.text.toString().isNullOrEmpty() && !eContactAddress?.text.toString().isNullOrEmpty()
    }

    private val contact: Contact
        get() = Contact(eContactSurname!!.text.toString(), eContactPassport!!.text.toString(), eContactPhone!!.text.toString(), eContactAddress!!.text.toString())


}
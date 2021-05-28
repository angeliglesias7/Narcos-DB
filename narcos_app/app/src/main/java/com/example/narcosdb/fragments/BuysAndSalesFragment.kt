package com.example.narcosdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.viewmodel.*
import kotlinx.coroutines.runBlocking


class BuysAndSalesFragment : Fragment() {

    private var transactionType: Spinner? = null
    private var drugSpinner: Spinner? = null
    private var moneyWarehouseSpinner: Spinner? = null
    private var drugWarehouseSpinner: Spinner? = null
    private var contactSpinner: Spinner? = null
    private var doTransaction: Button? = null
    private var amountDrugToBuy: EditText? = null
    private var drugList = ArrayList<Drug>()
    private var mwList = ArrayList<MoneyWarehouse>()
    private var dwList = ArrayList<DrugWarehouse>()
    private var contactList = ArrayList<Contact>()
    private var buysAndSalesViewModel: BuysAndSalesViewModel? = null
    private var drugViewModel: DrugViewModel? = null
    private var mwViewModel: MoneyWarehouseViewModel? = null
    private var dwViewModel: DrugWarehouseViewModel? = null
    private var contactViewModel: ContactViewModel? = null
    private lateinit var drug: Drug
    private lateinit var mw: MoneyWarehouse
    private lateinit var dw: DrugWarehouse
    private lateinit var contact: Contact


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_buys_and_sales, container, false)
        amountDrugToBuy = v.findViewById(R.id.amountDrugToBuy)
        doTransaction = v.findViewById(R.id.doTransaction)
        transactionType = v.findViewById(R.id.transactionType) as Spinner
        drugSpinner = v.findViewById(R.id.drugSpinner) as Spinner
        moneyWarehouseSpinner = v.findViewById(R.id.moneyWarehouseSpinner) as Spinner
        drugWarehouseSpinner = v.findViewById(R.id.drugWarehouseSpinner) as Spinner
        contactSpinner = v.findViewById(R.id.contactSpinner) as Spinner
        loadTransactionSpinner()
        drugViewModel = activity?.application?.let { DrugViewModel(it) }
        getAllDrugs()
        mwViewModel = activity?.application?.let { MoneyWarehouseViewModel(it) }
        getAllMoneyWarehouses()
        dwViewModel = activity?.application?.let { DrugWarehouseViewModel(it) }
        getAllDrugWarehouses()
        contactViewModel = activity?.application?.let { ContactViewModel(it) }
        getAllContacts()
        buysAndSalesViewModel = activity?.application?.let { BuysAndSalesViewModel(it) }

        doTransaction?.setOnClickListener {
            if (checkFields()) {
                getValues()
                val amount = amountDrugToBuy?.text.toString().toInt()
                if(transactionType!!.selectedItem.toString() == "Compra"){
                    runBlocking {
                        val result = buysAndSalesViewModel?.buyDrug(drug, mw, dw, contact, amount)
                        if(result.toString() == "0"){
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#800000'>"+getString(R.string.error_buy)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#00BB2D'>"+getString(R.string.successful_buy)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    runBlocking {
                        val result = buysAndSalesViewModel?.saleDrug(drug, mw, dw, contact, amount)
                        if(result.toString() == "0"){
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#800000'>"+getString(R.string.error_sale)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#00BB2D'>"+getString(R.string.successful_sale)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                val toast = Toast.makeText(
                    activity!!.applicationContext,
                    resources.getString(R.string.fields_transfer_error),
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
        }
        return v
    }

    private fun getAllDrugs() {
        drugViewModel?.getAll()?.observe(
            viewLifecycleOwner,
            Observer<List<Drug>> { list -> //called every time data changes
                drugList = list as java.util.ArrayList<Drug>
                loadDrugSpinner(drugList)
            })
    }

    private fun getAllMoneyWarehouses() {
        mwViewModel?.getAllWarehouses()?.observe(
            viewLifecycleOwner,
            Observer<List<MoneyWarehouse>> { list -> //called every time data changes
                mwList = list as java.util.ArrayList<MoneyWarehouse>
                loadMoneyWarehouseSpinner(mwList)
            })
    }

    private fun getAllDrugWarehouses() {
        dwViewModel?.getAll()?.observe(
            viewLifecycleOwner,
            Observer<List<DrugWarehouse>> { list -> //called every time data changes
                dwList = list as java.util.ArrayList<DrugWarehouse>
                loadDrugWarehouseSpinner(dwList)
            })
    }

    private fun getAllContacts() {
        contactViewModel?.getAll()?.observe(
            viewLifecycleOwner,
            Observer<List<Contact>> {
                list -> contactList = list as java.util.ArrayList<Contact>
                loadContactSpinner(contactList)
            }
        )
    }

    private fun loadTransactionSpinner() {
        val nameList = arrayListOf<String>()
        nameList.add(getString(R.string.buy))
        nameList.add(getString(R.string.sale))
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        transactionType!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadDrugSpinner(drugList: List<Drug>) {
        val nameList = arrayListOf<String>()
        for (i in drugList.indices) {
            nameList.add(drugList[i].name + "," + drugList[i].quality + "%")
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        drugSpinner!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadMoneyWarehouseSpinner(mwList: List<MoneyWarehouse>) {
        val nameList = arrayListOf<String>()
        for (i in mwList.indices) {
            nameList.add(mwList[i].name)
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        moneyWarehouseSpinner!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadDrugWarehouseSpinner(dwList: List<DrugWarehouse>) {
        val nameList = arrayListOf<String>()
        for (i in dwList.indices) {
            nameList.add(dwList[i].name)
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        drugWarehouseSpinner!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadContactSpinner(contactList: List<Contact>) {
        val nameList = arrayListOf<String>()
        for (i in contactList.indices) {
            nameList.add(contactList[i].surname)
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        contactSpinner!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun checkFields() : Boolean{
        if (drugSpinner?.selectedItem != null && contactSpinner?.selectedItem != null &&
            drugWarehouseSpinner?.selectedItem != null && moneyWarehouseSpinner?.selectedItem != null &&
            amountDrugToBuy?.text!!.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun getValues(){
        val array = drugSpinner!!.selectedItem.toString().split(",").toTypedArray()
        val array1 = array[1].split("%").toTypedArray()
        for (i in drugList.indices) {
            if (drugList[i].name == array[0] && drugList[i].quality.toString() == array1[0]) {
                drug = drugList[i]
                break
            }
        }
        for (i in mwList.indices) {
            if (mwList[i].name == (moneyWarehouseSpinner!!.selectedItem.toString())
            ) {
                mw = mwList[i]
            }
        }
        for (i in dwList.indices) {
            if (dwList[i].name == (drugWarehouseSpinner!!.selectedItem.toString())
            ) {
                dw = dwList[i]
            }
        }
        for (i in contactList.indices) {
            if (contactList[i].surname == contactSpinner!!.selectedItem.toString()) {
                contact = contactList[i]
            }
        }
    }


}
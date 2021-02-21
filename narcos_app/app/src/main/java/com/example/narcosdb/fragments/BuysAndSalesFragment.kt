package com.example.narcosdb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.viewmodel.DrugViewModel
import com.example.narcosdb.viewmodel.DrugWarehouseViewModel
import com.example.narcosdb.viewmodel.MoneyWarehouseViewModel


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
    private var drugViewModel: DrugViewModel? = null
    private var mwViewModel: MoneyWarehouseViewModel? = null
    private var dwViewModel: DrugWarehouseViewModel? = null


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

        doTransaction?.setOnClickListener {
            if (checkFields()) {
                //getValues()
                val amount = amountDrugToBuy?.text.toString().toInt()
                //mwViewModel?.transferMoney(originWarehouse, destinationWarehouse, amount)
            } else {
                val toast = Toast.makeText(
                    context,
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

    private fun loadTransactionSpinner() {
        val nameList = arrayListOf<String>()
        nameList.add("Compra")
        nameList.add("Venta")
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
        if (amountDrugToBuy!!.text.toString().toFloat() > 0 ) {
            return true
        }
        return false
    }



}
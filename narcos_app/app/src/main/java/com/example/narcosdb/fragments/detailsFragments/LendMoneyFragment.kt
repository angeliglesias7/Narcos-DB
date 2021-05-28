package com.example.narcosdb.fragments.detailsFragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.HtmlCompat
import com.example.narcosdb.R
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.entity.Loan
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.viewmodel.ContactViewModel
import com.example.narcosdb.viewmodel.LoanViewModel
import com.example.narcosdb.viewmodel.MoneyWarehouseViewModel
import androidx.lifecycle.Observer
import com.example.narcosdb.viewmodel.BuysAndSalesViewModel
import kotlinx.coroutines.runBlocking
import java.util.*

class LendMoneyFragment : Fragment() {

    private var doLend: Button? = null
    private var contactSpinner: Spinner? = null
    private var moneyWarehouseSpinner: Spinner? = null
    private var amountMoneyToLend: EditText? = null
    private var loanInterest: EditText? = null
    private var mwList = ArrayList<MoneyWarehouse>()
    private var contactList = ArrayList<Contact>()
    private var loanViewModel: LoanViewModel? = null
    private var contactViewModel: ContactViewModel? = null
    private var mwViewModel: MoneyWarehouseViewModel? = null
    private lateinit var appContext : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appContext = context!!.applicationContext

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_lend_money, container, false)
        doLend = v.findViewById(R.id.doLend)
        contactSpinner = v.findViewById(R.id.contactSpinner) as Spinner
        moneyWarehouseSpinner = v.findViewById(R.id.moneyWarehouseSpinner) as Spinner
        amountMoneyToLend = v.findViewById(R.id.amountMoneyToLend)
        loanInterest = v.findViewById(R.id.loanInterest)

        mwViewModel = activity?.application?.let { MoneyWarehouseViewModel(it) }
        getAllMoneyWarehouses()
        contactViewModel = activity?.application?.let { ContactViewModel(it) }
        getAllContacts()
        loanViewModel = activity?.application?.let { LoanViewModel(it) }

        doLend?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val loan = getLoan()
                runBlocking {
                    val result = loanViewModel?.newLoanMade(loan)
                    println(result)
                    if(result.toString() == "1"){
                        Toast.makeText(appContext,
                            HtmlCompat.fromHtml("<font color='#00BB2D'>"+getString(R.string.create_loan)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(appContext,
                            HtmlCompat.fromHtml("<font color='#800000'>"+getString(R.string.create_loan_error)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()

                    }
                }
            }
        })

        return v
    }

    private fun getAllMoneyWarehouses() {
        mwViewModel?.getAllWarehouses()?.observe(
            viewLifecycleOwner,
            Observer<List<MoneyWarehouse>> { list -> //called every time data changes
                mwList = list as ArrayList<MoneyWarehouse>
                loadMoneyWarehouseSpinner(mwList)
            })
    }

    private fun getAllContacts() {
        contactViewModel?.getAll()?.observe(
            viewLifecycleOwner,
            Observer<List<Contact>> {
                    list -> contactList = list as ArrayList<Contact>
                loadContactSpinner(contactList)
            }
        )
    }

    private fun loadMoneyWarehouseSpinner(mwList: List<MoneyWarehouse>) {
        val nameList = arrayListOf<String>()
        for (i in mwList.indices) {
            nameList.add(mwList[i].name)
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(appContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        moneyWarehouseSpinner!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadContactSpinner(contactList: List<Contact>) {
        val nameList = arrayListOf<String>()
        for (i in contactList.indices) {
            nameList.add(contactList[i].surname)
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(appContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        contactSpinner!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun checkFields(): Boolean {
        if (contactSpinner?.selectedItem != null && moneyWarehouseSpinner?.selectedItem != null &&
            amountMoneyToLend?.text!!.isNotEmpty() && loanInterest?.text!!.isNotEmpty()
            && loanInterest?.text!!.toString().toDouble() < 100) {
            return true
        }
        return false
    }

    private fun getLoan(): Loan {
        var percentage = loanInterest?.text.toString().toDouble() / 100
        percentage += 1
        var totalAmount: Double = amountMoneyToLend?.text.toString().toDouble() * percentage
        val loan = Loan(contactSpinner?.selectedItem.toString(), moneyWarehouseSpinner?.selectedItem.toString(),
            amountMoneyToLend?.text.toString().toDouble(), totalAmount, loanInterest?.text.toString().toDouble(),
            false, 1, Calendar.getInstance().time.toString(), Calendar.getInstance().time.toString())

        return loan
    }




}
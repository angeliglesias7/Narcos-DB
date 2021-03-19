package com.example.narcosdb.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.fragments.detailsFragments.MoneyWarehouseDetailsFragment
import com.example.narcosdb.viewmodel.MoneyWarehouseViewModel


class MoneyWarehouseFragment : Fragment(), View.OnClickListener {

    private var moneyWarehouseTable: TableLayout? = null
    private var moneyWarehouseList: ArrayList<MoneyWarehouse> = ArrayList()
    private var addMoneyWarehouse: ImageButton? = null
    private var frameLayout: DrawerLayout? = null
    private var totalMoney: TextView? = null
    private var spinner1: Spinner? = null
    private var spinner2: Spinner? = null
    private var amountToTransfer: EditText? = null
    private var transferButton: Button? = null
    private var originWarehouse: MoneyWarehouse = MoneyWarehouse()
    private var destinationWarehouse: MoneyWarehouse = MoneyWarehouse()
    private var mwViewModel: MoneyWarehouseViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_money_warehouse, container, false)

        moneyWarehouseTable = v.findViewById<View>(R.id.moneyWarehouseTable) as TableLayout
        moneyWarehouseTable!!.setBackgroundResource(R.drawable.border)
        frameLayout = v.findViewById<View>(R.id.moneyWarehouse_layout) as DrawerLayout

        totalMoney = v.findViewById(R.id.totalMoneyWarehouses) as TextView
        spinner1 = v.findViewById(R.id.warehouse1spinner) as Spinner
        spinner2 = v.findViewById(R.id.warehouse2spinner) as Spinner
        amountToTransfer = v.findViewById(R.id.amountToTransfer) as EditText
        transferButton = v.findViewById(R.id.transferButton) as Button
        initializeTable()

        mwViewModel = activity?.application?.let { MoneyWarehouseViewModel(it) }
        getAllWarehouses()
        getAllMoney()

        addMoneyWarehouse = v.findViewById<ImageButton>(R.id.addMoneyWarehouse)
        addMoneyWarehouse?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, MoneyWarehouseDetailsFragment())
            transaction?.addToBackStack("newmoneywarehouse")
            transaction?.commit()
        }

        transferButton = v.findViewById(R.id.transferButton)
        transferButton?.setOnClickListener {
            if (checkFields()) {
                getValues()
                val amount = amountToTransfer?.text.toString().toInt()
                mwViewModel?.transferMoney(originWarehouse, destinationWarehouse, amount)
            } else {
                val toast = Toast.makeText(
                    context,
                    resources.getString(R.string.fields_transfer_error),
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
        }

        v.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().onBackPressed()
                return@OnKeyListener true
            }
            false
        })
        return v
    }

    private fun getAllWarehouses() {
        mwViewModel?.getAllWarehouses()?.observe(
            activity!!,
            Observer<List<MoneyWarehouse>> { list -> //called every time data changes
                moneyWarehouseList = list as java.util.ArrayList<MoneyWarehouse>
                println(list.size)
                fillTable(moneyWarehouseList)
                loadSpinners(moneyWarehouseList)
            })
    }

    private fun getAllMoney() {
        mwViewModel?.getAllMoney()!!.observe(activity!!,
            Observer<Float> { aFloat ->
                if (aFloat != null) {
                    totalMoney!!.text = "$aFloat €"
                } else {
                    totalMoney!!.text = "0 €"
                }
            })
    }

    override fun onClick(v: View) {
        val clicked_id = v.id
        val mRow = moneyWarehouseTable!!.getChildAt(clicked_id) as TableRow
        val name = mRow.getChildAt(0) as TextView
        val place = mRow.getChildAt(1) as TextView
        val m2 = mRow.getChildAt(2) as TextView
        val amountMoney = mRow.getChildAt(3) as TextView
        val bundle = Bundle()
        bundle.putString("name", name.text.toString())
        bundle.putString("place", place.text.toString())
        bundle.putString("m2", m2.text.toString())
        bundle.putString("amountMoney", amountMoney.text.toString())
        val moneyWarehouseDetailsFragment = MoneyWarehouseDetailsFragment()
        moneyWarehouseDetailsFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout_id, moneyWarehouseDetailsFragment)
            .addToBackStack("newmoneywarehouse").commit()
    }

    private fun initializeTable() {
        val tr = TableRow(context)
        val tv0 = TextView(context)
        tv0.text = "Nombre"
        tv0.setTextColor(Color.BLACK)
        tv0.gravity = Gravity.CENTER
        tv0.setPadding(20, 0, 20, 0)
        tr.addView(tv0)
        val tv1 = TextView(context)
        tv1.text = "Lugar"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(context)
        tv2.text = "Metros cuadrados"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
        val tv3 = TextView(context)
        tv3.text = "Cantidad de dinero (€)"
        tv3.setTextColor(Color.BLACK)
        //tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(20, 0, 20, 0)
        tr.addView(tv3)
        val tableRowParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        val leftMargin = 10
        val topMargin = 10
        val rightMargin = 10
        val bottomMargin = 10
        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
        tr.layoutParams = tableRowParams
        tr.setBackgroundResource(R.drawable.border)
        tr.setBackgroundColor(Color.parseColor("#0079D6"))
        tr.isClickable = true
        moneyWarehouseTable!!.addView(tr)
    }

    private fun fillTable(drugList: java.util.ArrayList<MoneyWarehouse>) {
        for (i in drugList.indices) {
            val tr = TableRow(context)
            val t1v = TextView(context)
            t1v.text = drugList[i].name
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(context)
            t2v.text = drugList[i].place.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(context)
            t3v.text = drugList[i].m2.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
            val t4v = TextView(context)
            t4v.text = drugList[i].amountMoney.toString()
            t4v.setTextColor(Color.BLACK)
            //t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(20, 0, 20, 0)
            tr.addView(t4v)
            val tableRowParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            val leftMargin = 10
            val topMargin = 10
            val rightMargin = 10
            val bottomMargin = 10
            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
            tr.layoutParams = tableRowParams
            tr.isClickable = true
            tr.id = i + 1
            tr.setOnClickListener(this@MoneyWarehouseFragment)
            //tr.setBackgroundResource(R.drawable.border);
            tr.setBackgroundColor(Color.parseColor("#DAE8FC"))
            moneyWarehouseTable!!.addView(tr)
        }
    }

    private fun loadSpinners(moneyWarehouseList: List<MoneyWarehouse>) {
        val nameList = arrayListOf<String>()
        for (i in moneyWarehouseList.indices) {
            nameList.add(moneyWarehouseList[i].name)
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, nameList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner1!!.adapter = adapter
        spinner2!!.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun checkFields(): Boolean {
        if (!amountToTransfer?.text.isNullOrEmpty() && amountToTransfer?.text.toString().toFloat() > 0 &&
            spinner1?.selectedItem.toString() != spinner2?.selectedItem.toString()
        ) {
            return true
        }
        return false
    }

    private fun getValues() {
        for (i in moneyWarehouseList.indices) {
            if (moneyWarehouseList[i].name == spinner1?.selectedItem.toString()) {
                originWarehouse = moneyWarehouseList[i]
            }
        }
        for (i in moneyWarehouseList.indices) {
            if (moneyWarehouseList[i].name == spinner2?.selectedItem.toString()) {
                destinationWarehouse = moneyWarehouseList[i]
            }
        }
    }


}
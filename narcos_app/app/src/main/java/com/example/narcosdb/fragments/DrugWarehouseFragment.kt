package com.example.narcosdb.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.fragments.detailsFragments.DrugWarehouseDetailsFragment
import com.example.narcosdb.fragments.detailsFragments.MoneyWarehouseDetailsFragment
import com.example.narcosdb.viewmodel.DrugWarehouseViewModel
import com.example.narcosdb.viewmodel.MoneyWarehouseViewModel


class DrugWarehouseFragment : Fragment(), View.OnClickListener {
    private var drugWarehouseTable: TableLayout? = null
    private var drugWarehouseList: ArrayList<DrugWarehouse> = ArrayList()
    private var addDrugWarehouse: ImageButton? = null
    private var frameLayout: DrawerLayout? = null
    private var dwViewModel: DrugWarehouseViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_drug_warehouse, container, false)

        drugWarehouseTable = v.findViewById<View>(R.id.drugWarehouseTable) as TableLayout
        drugWarehouseTable!!.setBackgroundResource(R.drawable.border)
        frameLayout = v.findViewById<View>(R.id.drugWarehouse_layout) as DrawerLayout

        initializeTable()
        dwViewModel = activity?.application?.let { DrugWarehouseViewModel(it) }
        getAllWarehouses()

        addDrugWarehouse = v.findViewById<ImageButton>(R.id.addDrugWarehouse)
        addDrugWarehouse?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, DrugWarehouseDetailsFragment())
            transaction?.addToBackStack("newdrugwarehouse")
            transaction?.commit()
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
        dwViewModel?.getAll()?.observe(
            viewLifecycleOwner,
            Observer<List<DrugWarehouse>> { list -> //called every time data changes
                drugWarehouseList = list as java.util.ArrayList<DrugWarehouse>
                println(list.size)
                fillTable(drugWarehouseList)
            })
    }

    override fun onClick(v: View) {
        val clicked_id = v.id
        val mRow = drugWarehouseTable!!.getChildAt(clicked_id) as TableRow
        val name = mRow.getChildAt(0) as TextView
        val place = mRow.getChildAt(1) as TextView
        val m2 = mRow.getChildAt(2) as TextView
        val bundle = Bundle()
        bundle.putString("name", name.text.toString())
        bundle.putString("place", place.text.toString())
        bundle.putString("m2", m2.text.toString())
        val drugWarehouseDetailsFragment = DrugWarehouseDetailsFragment()
        drugWarehouseDetailsFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout_id, drugWarehouseDetailsFragment)
            .addToBackStack("newdrugwarehouse").commit()
    }

    private fun initializeTable() {
        val tr = TableRow(activity!!.applicationContext)
        val tv0 = TextView(activity!!.applicationContext)
        tv0.text = "Nombre"
        tv0.setTextColor(Color.BLACK)
        tv0.gravity = Gravity.CENTER
        tv0.setPadding(20, 0, 20, 0)
        tr.addView(tv0)
        val tv1 = TextView(activity!!.applicationContext)
        tv1.text = "Lugar"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(activity!!.applicationContext)
        tv2.text = "Metros cuadrados"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
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
        drugWarehouseTable!!.addView(tr)
    }

    private fun fillTable(drugList: java.util.ArrayList<DrugWarehouse>) {
        for (i in drugList.indices) {
            val tr = TableRow(activity!!.applicationContext)
            val t1v = TextView(activity!!.applicationContext)
            t1v.text = drugList[i].name
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(activity!!.applicationContext)
            t2v.text = drugList[i].place.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(activity!!.applicationContext)
            t3v.text = drugList[i].m2.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
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
            tr.setOnClickListener(this@DrugWarehouseFragment)
            //tr.setBackgroundResource(R.drawable.border);
            tr.setBackgroundColor(Color.parseColor("#DAE8FC"))
            drugWarehouseTable!!.addView(tr)
        }
    }
}
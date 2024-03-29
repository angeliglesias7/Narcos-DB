package com.example.narcosdb.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.entity.DrugBuy
import com.example.narcosdb.entity.DrugSales
import com.example.narcosdb.viewmodel.BuysAndSalesViewModel


class HistoryFragment : Fragment() {
    private var buyHistoryTable : TableLayout? = null
    private var salesHistoryTable : TableLayout? = null
    private var buyHistoryList = ArrayList<DrugBuy>()
    private var salesHistoryList = ArrayList<DrugSales>()
    private var buysAndSalesViewModel: BuysAndSalesViewModel? = null
    private var frameLayout : DrawerLayout? = null
    private lateinit var appContext : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appContext = context!!.applicationContext

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_history, container, false)

        buyHistoryTable = v.findViewById<View>(R.id.buyHistoryTable) as TableLayout
        buyHistoryTable!!.setBackgroundResource(R.drawable.border)
        salesHistoryTable = v.findViewById<View>(R.id.salesHistoryTable) as TableLayout
        salesHistoryTable!!.setBackgroundResource(R.drawable.border)
        frameLayout = v.findViewById<View>(R.id.history_layout) as DrawerLayout
        initializeBuyTable()
        initializeSalesTable()
        buysAndSalesViewModel = activity?.application?.let { BuysAndSalesViewModel(it) }
        getBuyHistory()
        getSalesHistory()

        v.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().onBackPressed()
                return@OnKeyListener true
            }
            false
        })
        return v
    }

    private fun getBuyHistory(){
        buysAndSalesViewModel?.getBuyHistory()?.observe(
            viewLifecycleOwner,
            Observer<List<DrugBuy>> { list -> //called every time data changes
                buyHistoryList = list as java.util.ArrayList<DrugBuy>
                fillBuyTable(buyHistoryList)
            })
    }

    private fun getSalesHistory(){
        buysAndSalesViewModel?.getSalesHistory()?.observe(
            viewLifecycleOwner,
            Observer<List<DrugSales>> { list -> //called every time data changes
                salesHistoryList = list as java.util.ArrayList<DrugSales>
                fillSalesTable(salesHistoryList)
            })
    }

    private fun initializeBuyTable() {
        val tr = TableRow(appContext)
        val tv0 = TextView(appContext)
        tv0.text = "Droga"
        tv0.setTextColor(Color.BLACK)
        tv0.gravity = Gravity.CENTER
        tv0.setPadding(20, 0, 20, 0)
        tr.addView(tv0)
        val tv1 = TextView(appContext)
        tv1.text = "Calidad (%)"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(appContext)
        tv2.text = "Cantidad (kg)"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
        val tv3 = TextView(appContext)
        tv3.text = "Fecha"
        tv3.setTextColor(Color.BLACK)
        //tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(20, 0, 20, 0)
        tr.addView(tv3)
        val tv4 = TextView(appContext)
        tv4.text = "Contacto"
        tv4.setTextColor(Color.BLACK)
        tv4.gravity = Gravity.CENTER
        tv4.setPadding(20, 0, 20, 0)
        tr.addView(tv4)
        val tv5 = TextView(appContext)
        tv5.text = "Dinero (€)"
        tv5.setTextColor(Color.BLACK)
        tv5.gravity = Gravity.CENTER
        tv5.setPadding(20, 0, 20, 0)
        tr.addView(tv5)
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
        buyHistoryTable!!.addView(tr)
    }

    private fun initializeSalesTable() {
        val tr = TableRow(appContext)
        val tv0 = TextView(appContext)
        tv0.text = "Droga"
        tv0.setTextColor(Color.BLACK)
        tv0.gravity = Gravity.CENTER
        tv0.setPadding(20, 0, 20, 0)
        tr.addView(tv0)
        val tv1 = TextView(appContext)
        tv1.text = "Calidad (%)"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(appContext)
        tv2.text = "Cantidad (kg)"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
        val tv3 = TextView(appContext)
        tv3.text = "Fecha"
        tv3.setTextColor(Color.BLACK)
        //tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(20, 0, 20, 0)
        tr.addView(tv3)
        val tv4 = TextView(appContext)
        tv4.text = "Contacto"
        tv4.setTextColor(Color.BLACK)
        tv4.gravity = Gravity.CENTER
        tv4.setPadding(20, 0, 20, 0)
        tr.addView(tv4)
        val tv5 = TextView(appContext)
        tv5.text = "Dinero (€)"
        tv5.setTextColor(Color.BLACK)
        tv5.gravity = Gravity.CENTER
        tv5.setPadding(20, 0, 20, 0)
        tr.addView(tv5)
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
        salesHistoryTable!!.addView(tr)
    }

    private fun fillBuyTable(buyHistoryList: ArrayList<DrugBuy>) {
        for (i in buyHistoryList.indices) {
            val tr = TableRow(appContext)
            val t1v = TextView(appContext)
            t1v.text = buyHistoryList[i].drugName
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(appContext)
            t2v.text = buyHistoryList[i].quality.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(appContext)
            t3v.text = buyHistoryList[i].quantity.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
            val t4v = TextView(appContext)
            t4v.text = buyHistoryList[i].date
            t4v.setTextColor(Color.BLACK)
            //t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(20, 0, 20, 0)
            tr.addView(t4v)
            val t5v = TextView(appContext)
            t5v.text = buyHistoryList[i].contact
            t5v.setTextColor(Color.BLACK)
            t5v.gravity = Gravity.CENTER
            t5v.setPadding(20, 0, 20, 0)
            tr.addView(t5v)
            val t6v = TextView(appContext)
            t6v.text = buyHistoryList[i].amountMoney.toString()
            t6v.setTextColor(Color.BLACK)
            t6v.gravity = Gravity.CENTER
            t6v.setPadding(20, 0, 20, 0)
            tr.addView(t6v)
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
            //tr.setBackgroundResource(R.drawable.border);
            tr.setBackgroundColor(Color.parseColor("#DAE8FC"))
            buyHistoryTable!!.addView(tr)
        }
    }

    private fun fillSalesTable(salesHistoryList: ArrayList<DrugSales>) {
        for (i in salesHistoryList.indices) {
            val tr = TableRow(appContext)
            val t1v = TextView(appContext)
            t1v.text = salesHistoryList[i].drugName
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(appContext)
            t2v.text = salesHistoryList[i].quality.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(appContext)
            t3v.text = salesHistoryList[i].quantity.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
            val t4v = TextView(appContext)
            t4v.text = salesHistoryList[i].date
            t4v.setTextColor(Color.BLACK)
            //t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(20, 0, 20, 0)
            tr.addView(t4v)
            val t5v = TextView(appContext)
            t5v.text = salesHistoryList[i].contact
            t5v.setTextColor(Color.BLACK)
            t5v.gravity = Gravity.CENTER
            t5v.setPadding(20, 0, 20, 0)
            tr.addView(t5v)
            val t6v = TextView(appContext)
            t6v.text = salesHistoryList[i].amountMoney.toString()
            t6v.setTextColor(Color.BLACK)
            t6v.gravity = Gravity.CENTER
            t6v.setPadding(20, 0, 20, 0)
            tr.addView(t6v)
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
            //tr.setBackgroundResource(R.drawable.border);
            tr.setBackgroundColor(Color.parseColor("#DAE8FC"))
            salesHistoryTable!!.addView(tr)
        }
    }

    private fun closeDrawer() {
        if (frameLayout!!.isDrawerOpen(GravityCompat.START)) {
            frameLayout!!.closeDrawer(GravityCompat.START)
        }
    }

}
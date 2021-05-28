package com.example.narcosdb.fragments.detailsFragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.narcosdb.R
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.model.DrugInWarehousePOJO
import com.example.narcosdb.viewmodel.DrugWarehouseViewModel
import kotlinx.coroutines.runBlocking
import java.util.ArrayList


class DrugWarehouseDetailsFragment : Fragment() {
    private var frameLayout: DrawerLayout? = null
    private var eDwName: EditText? = null
    private var eDwPlace: EditText? = null
    private var eDwM2: EditText? = null
    private var saveDw: Button? = null
    private var deleteDw: Button? = null
    private var destroyDw: Button? = null
    private var isNewDw: Boolean? = null
    private var drugsInWarehouseTable: TableLayout? = null
    private var drugsInWarehouseList: ArrayList<DrugInWarehousePOJO> = ArrayList()
    private var dwViewModel: DrugWarehouseViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_drug_warehouse_details, container, false)
        frameLayout = v.findViewById<View>(R.id.newDrugWarehouse_layout) as DrawerLayout
        saveDw = v.findViewById(R.id.saveDrugWarehouse)
        deleteDw = v.findViewById(R.id.deleteDrugWarehouse)
        destroyDw = v.findViewById(R.id.destroyDrugWarehouse)
        drugsInWarehouseTable = v.findViewById(R.id.drugsInWarehouseTable)
        eDwName = v.findViewById(R.id.eDWName)
        eDwPlace = v.findViewById(R.id.eDWPlace)
        eDwM2 = v.findViewById(R.id.eDWM2)
        initializeTable()
        dwViewModel = ViewModelProvider(requireActivity()).get(DrugWarehouseViewModel::class.java)
        try {
            val bundle = this.arguments
            if (bundle != null) {
                val name = bundle.getString("name")
                val quality = bundle.getString("place")
                val price = bundle.getString("m2")
                eDwName?.setText(name)
                eDwPlace?.setText(quality)
                eDwM2?.setText(price)
                isNewDw = false
            } else {
                isNewDw = true
                deleteDw?.visibility = View.GONE
                destroyDw?.visibility = View.GONE
            }
        } catch (e: Exception) {
            println(e.message)
        }
        getDrugsInWarehouse()
        saveDw?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val drugWarehouse = getDrugWarehouse
                if (isNewDw == true) {
                    runBlocking {
                        val result = dwViewModel?.insert(drugWarehouse)
                        if(result.toString() == "-1"){
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#800000'>"+getString(R.string.create_mw_error)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#00BB2D'>"+getString(R.string.create_mw)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    runBlocking {
                        val result = dwViewModel?.update(drugWarehouse)
                        if(result.toString() == "-1"){
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#800000'>"+getString(R.string.update_mw_error)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(activity!!.applicationContext,
                                HtmlCompat.fromHtml("<font color='#00BB2D'>"+getString(R.string.update_mw)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
        deleteDw?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val drugWarehouse = getDrugWarehouse
                runBlocking {
                    val result = dwViewModel?.delete(drugWarehouse)
                    if(result.toString() == "-1"){
                        Toast.makeText(activity!!.applicationContext,
                            HtmlCompat.fromHtml("<font color='#800000'>"+getString(R.string.delete_mw_error)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(activity!!.applicationContext,
                            HtmlCompat.fromHtml("<font color='#00BB2D'>"+getString(R.string.delete_mw)+"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        v.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().onBackPressed()
                return@OnKeyListener true
            }
            false
        })
        return v
    }

    private fun getDrugsInWarehouse() {
        dwViewModel?.getDrugsInWarehouse(eDwName?.text.toString())?.observe(
            viewLifecycleOwner,
            Observer<List<DrugInWarehousePOJO>> { list -> //called every time data changes
                drugsInWarehouseList = list as ArrayList<DrugInWarehousePOJO>
                println(eDwName?.text.toString())
                println(list.size)
                fillTable(drugsInWarehouseList)
            })
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
        tv1.text = "Calidad (%)"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(activity!!.applicationContext)
        tv2.text = "Precio (€/kg)"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
        val tv3 = TextView(activity!!.applicationContext)
        tv3.text = "Cantidad (kg)"
        tv3.setTextColor(Color.BLACK)
        tv3.gravity = Gravity.CENTER
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
        drugsInWarehouseTable!!.addView(tr)
    }

    private fun fillTable(drugList: java.util.ArrayList<DrugInWarehousePOJO>) {
        for (i in drugList.indices) {
            val tr = TableRow(activity!!.applicationContext)
            val t1v = TextView(activity!!.applicationContext)
            t1v.text = drugList[i].drugName
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(activity!!.applicationContext)
            t2v.text = drugList[i].drugQuality.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(activity!!.applicationContext)
            t3v.text = drugList[i].price.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
            val t4v = TextView(activity!!.applicationContext)
            t4v.text = drugList[i].amount.toString()
            t4v.setTextColor(Color.BLACK)
            t4v.gravity = Gravity.CENTER
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
            tr.id = i + 1
            tr.setBackgroundResource(R.drawable.border);
            tr.setBackgroundColor(Color.parseColor("#DAE8FC"))
            drugsInWarehouseTable!!.addView(tr)
        }
    }

    private fun checkFields(): Boolean {
        return !eDwName?.text.toString().isNullOrEmpty()  && !eDwPlace?.text.toString().isNullOrEmpty()
                && !eDwM2?.text.toString().isNullOrEmpty()
    }

    private val getDrugWarehouse: DrugWarehouse
        get() = DrugWarehouse(eDwName!!.text.toString(), eDwPlace!!.text.toString(), eDwM2!!.text.toString().toInt())

}
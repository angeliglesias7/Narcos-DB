package com.example.narcosdb.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.fragments.detailsFragments.DrugDetailsFragment
import com.example.narcosdb.viewmodel.DrugViewModel
import java.util.ArrayList

class DrugFragment : Fragment(), View.OnClickListener {

    private var drugTable: TableLayout? = null
    private var drugViewModel: DrugViewModel? = null
    private var drugList = ArrayList<Drug>()
    private var addDrug: ImageButton? = null
    private var frameLayout: DrawerLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_drug, container, false)
        drugTable = v.findViewById<View>(R.id.drugTable) as TableLayout
        drugTable!!.setBackgroundResource(R.drawable.border)
        frameLayout = v.findViewById<View>(R.id.drug_layout) as DrawerLayout
        initializeTable()
        drugViewModel = activity?.application?.let { DrugViewModel(it) }
        getAll()

        addDrug = v.findViewById<ImageButton>(R.id.addDrug)
        addDrug?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, DrugDetailsFragment())
            transaction?.addToBackStack("newdrug")
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

    private fun getAll() {
        drugViewModel?.getAll()?.observe(
            viewLifecycleOwner,
            Observer<List<Drug>> { list -> //called every time data changes
                drugList = list as ArrayList<Drug>
                println("Está entrando")
                println(list.size)
                fillTable(drugList)
            })
    }

    override fun onClick(v: View) {
        val clicked_id = v.id
        val mRow = drugTable!!.getChildAt(clicked_id) as TableRow
        val name = mRow.getChildAt(0) as TextView
        val quality = mRow.getChildAt(1) as TextView
        val price = mRow.getChildAt(2) as TextView
        val description = mRow.getChildAt(3) as TextView
        val bundle = Bundle()
        bundle.putString("name", name.text.toString())
        bundle.putString("quality", quality.text.toString())
        bundle.putString("price", price.text.toString())
        bundle.putString("description", description.text.toString())
        val drugDetailsFragment = DrugDetailsFragment()
        drugDetailsFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout_id, drugDetailsFragment)
            .addToBackStack("newdrug").commit()
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
        tv1.text = "Calidad (%)"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(context)
        tv2.text = "Precio (€/kg)"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
        val tv3 = TextView(context)
        tv3.text = "Descripción"
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
        drugTable!!.addView(tr)
    }

    private fun fillTable(drugList: ArrayList<Drug>) {
        for (i in drugList.indices) {
            val tr = TableRow(context)
            val t1v = TextView(context)
            t1v.text = drugList[i].name
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(context)
            t2v.text = drugList[i].quality.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(context)
            t3v.text = drugList[i].price.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
            val t4v = TextView(context)
            t4v.text = drugList[i].description
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
            tr.setOnClickListener(this@DrugFragment)
            //tr.setBackgroundResource(R.drawable.border);
            tr.setBackgroundColor(Color.parseColor("#DAE8FC"))
            drugTable!!.addView(tr)
        }
    }

    private fun closeDrawer() {
        if (frameLayout!!.isDrawerOpen(GravityCompat.START)) {
            frameLayout!!.closeDrawer(GravityCompat.START)
        }
    }
}
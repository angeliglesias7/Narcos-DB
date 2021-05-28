package com.example.narcosdb.fragments.detailsFragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.entity.Loan
import com.example.narcosdb.viewmodel.LoanViewModel


class LoanHistoryFragment : Fragment() {

    private var loansMadeHistoryTable : TableLayout? = null
    private var loansToPayHistoryTable : TableLayout? = null
    private var loansMadeList = ArrayList<Loan>()
    private var loansToPayList = ArrayList<Loan>()
    private var loanViewModel: LoanViewModel? = null
    private lateinit var appContext : Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appContext = context!!.applicationContext

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_loan_history, container, false)

        loansMadeHistoryTable = v.findViewById<View>(R.id.loansMadeHistoryTable) as TableLayout
        loansMadeHistoryTable!!.setBackgroundResource(R.drawable.border)
        loansToPayHistoryTable = v.findViewById<View>(R.id.loansToPayHistoryTable) as TableLayout
        loansToPayHistoryTable!!.setBackgroundResource(R.drawable.border)

        initializeLoansMadeTable()
        initializeLoansToPayTable()

        loanViewModel = activity?.application?.let { LoanViewModel(it) }
        getLoansMadeHistory()
        getLoansToPayHistory()

        v.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().onBackPressed()
                return@OnKeyListener true
            }
            false
        })
        return v
    }

    private fun getLoansMadeHistory(){
        loanViewModel?.getAllLoansMade()?.observe(
            viewLifecycleOwner,
            Observer<List<Loan>> { list -> //called every time data changes
                loansMadeList = list as java.util.ArrayList<Loan>
                fillLoansMadeTable(loansMadeList)
            })
    }

    private fun getLoansToPayHistory(){
        loanViewModel?.getAllLoansToPay()?.observe(
            viewLifecycleOwner,
            Observer<List<Loan>> { list -> //called every time data changes
                loansToPayList = list as java.util.ArrayList<Loan>
                fillLoansToPayTable(loansToPayList)
            })
    }

    private fun initializeLoansMadeTable() {
        val tr = TableRow(appContext)
        val tv0 = TextView(appContext)
        tv0.text = "Contacto"
        tv0.setTextColor(Color.BLACK)
        tv0.gravity = Gravity.CENTER
        tv0.setPadding(20, 0, 20, 0)
        tr.addView(tv0)
        val tv1 = TextView(appContext)
        tv1.text = "Cantidad (€)"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(appContext)
        tv2.text = "Interés (%)"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
        val tv3 = TextView(appContext)
        tv3.text = "Almacén"
        tv3.setTextColor(Color.BLACK)
        //tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(20, 0, 20, 0)
        tr.addView(tv3)
        val tv4 = TextView(appContext)
        tv4.text = "Fecha préstamo"
        tv4.setTextColor(Color.BLACK)
        tv4.gravity = Gravity.CENTER
        tv4.setPadding(20, 0, 20, 0)
        tr.addView(tv4)
        val tv5 = TextView(appContext)
        tv5.text = "Fecha devolución"
        tv5.setTextColor(Color.BLACK)
        tv5.gravity = Gravity.CENTER
        tv5.setPadding(20, 0, 20, 0)
        tr.addView(tv5)
        val tv6 = TextView(appContext)
        tv6.text = "Fecha límite"
        tv6.setTextColor(Color.BLACK)
        tv6.gravity = Gravity.CENTER
        tv6.setPadding(20, 0, 20, 0)
        tr.addView(tv6)
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
        loansMadeHistoryTable!!.addView(tr)
    }

    private fun initializeLoansToPayTable() {
        val tr = TableRow(appContext)
        val tv0 = TextView(appContext)
        tv0.text = "Contacto"
        tv0.setTextColor(Color.BLACK)
        tv0.gravity = Gravity.CENTER
        tv0.setPadding(20, 0, 20, 0)
        tr.addView(tv0)
        val tv1 = TextView(appContext)
        tv1.text = "Cantidad (€)"
        tv1.setTextColor(Color.BLACK)
        tv1.gravity = Gravity.CENTER
        tv1.setPadding(20, 0, 20, 0)
        tr.addView(tv1)
        val tv2 = TextView(appContext)
        tv2.text = "Interés (%)"
        tv2.setTextColor(Color.BLACK)
        tv2.gravity = Gravity.CENTER
        tv2.setPadding(20, 0, 20, 0)
        tr.addView(tv2)
        val tv3 = TextView(appContext)
        tv3.text = "Almacén"
        tv3.setTextColor(Color.BLACK)
        //tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(20, 0, 20, 0)
        tr.addView(tv3)
        val tv4 = TextView(appContext)
        tv4.text = "Fecha préstamo"
        tv4.setTextColor(Color.BLACK)
        tv4.gravity = Gravity.CENTER
        tv4.setPadding(20, 0, 20, 0)
        tr.addView(tv4)
        val tv5 = TextView(appContext)
        tv5.text = "Fecha devolución"
        tv5.setTextColor(Color.BLACK)
        tv5.gravity = Gravity.CENTER
        tv5.setPadding(20, 0, 20, 0)
        tr.addView(tv5)
        val tv6 = TextView(appContext)
        tv6.text = "Fecha límite"
        tv6.setTextColor(Color.BLACK)
        tv6.gravity = Gravity.CENTER
        tv6.setPadding(20, 0, 20, 0)
        tr.addView(tv6)
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
        loansToPayHistoryTable!!.addView(tr)
    }

    private fun fillLoansMadeTable(loansMadeHistoryList: ArrayList<Loan>) {
        for (i in loansMadeHistoryList.indices) {
            val tr = TableRow(appContext)
            val t1v = TextView(appContext)
            t1v.text = loansMadeHistoryList[i].contact
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(appContext)
            t2v.text = loansMadeHistoryList[i].totalAmount.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(appContext)
            t3v.text = loansMadeHistoryList[i].interest.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
            val t4v = TextView(appContext)
            t4v.text = loansMadeHistoryList[i].warehouse
            t4v.setTextColor(Color.BLACK)
            //t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(20, 0, 20, 0)
            tr.addView(t4v)
            val t5v = TextView(appContext)
            t5v.text = loansMadeHistoryList[i].loanDate
            t5v.setTextColor(Color.BLACK)
            t5v.gravity = Gravity.CENTER
            t5v.setPadding(20, 0, 20, 0)
            tr.addView(t5v)
            val t6v = TextView(appContext)
            t6v.text = loansMadeHistoryList[i].returnDate
            t6v.setTextColor(Color.BLACK)
            t6v.gravity = Gravity.CENTER
            t6v.setPadding(20, 0, 20, 0)
            tr.addView(t6v)
            val t7v = TextView(appContext)
            t7v.text = loansMadeHistoryList[i].dueDate
            t7v.setTextColor(Color.BLACK)
            t7v.gravity = Gravity.CENTER
            t7v.setPadding(20, 0, 20, 0)
            tr.addView(t7v)
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
            loansMadeHistoryTable!!.addView(tr)
        }
    }

    private fun fillLoansToPayTable(loansToPayHistoryList: ArrayList<Loan>) {
        for (i in loansToPayHistoryList.indices) {
            val tr = TableRow(appContext)
            val t1v = TextView(appContext)
            t1v.text = loansToPayHistoryList[i].contact
            t1v.setTextColor(Color.BLACK)
            t1v.gravity = Gravity.CENTER
            t1v.setPadding(20, 0, 20, 0)
            tr.addView(t1v)
            val t2v = TextView(appContext)
            t2v.text = loansToPayHistoryList[i].totalAmount.toString()
            t2v.setTextColor(Color.BLACK)
            t2v.gravity = Gravity.CENTER
            t2v.setPadding(20, 0, 20, 0)
            tr.addView(t2v)
            val t3v = TextView(appContext)
            t3v.text = loansToPayHistoryList[i].interest.toString()
            t3v.setTextColor(Color.BLACK)
            t3v.gravity = Gravity.CENTER
            t3v.setPadding(20, 0, 20, 0)
            tr.addView(t3v)
            val t4v = TextView(appContext)
            t4v.text = loansToPayHistoryList[i].warehouse
            t4v.setTextColor(Color.BLACK)
            //t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(20, 0, 20, 0)
            tr.addView(t4v)
            val t5v = TextView(appContext)
            t5v.text = loansToPayHistoryList[i].loanDate.toString()
            t5v.setTextColor(Color.BLACK)
            t5v.gravity = Gravity.CENTER
            t5v.setPadding(20, 0, 20, 0)
            tr.addView(t5v)
            val t6v = TextView(appContext)
            t6v.text = loansToPayHistoryList[i].returnDate.toString()
            t6v.setTextColor(Color.BLACK)
            t6v.gravity = Gravity.CENTER
            t6v.setPadding(20, 0, 20, 0)
            tr.addView(t6v)
            val t7v = TextView(appContext)
            t7v.text = loansToPayHistoryList[i].dueDate.toString()
            t7v.setTextColor(Color.BLACK)
            t7v.gravity = Gravity.CENTER
            t7v.setPadding(20, 0, 20, 0)
            tr.addView(t7v)
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
            loansToPayHistoryTable!!.addView(tr)
        }
    }

}
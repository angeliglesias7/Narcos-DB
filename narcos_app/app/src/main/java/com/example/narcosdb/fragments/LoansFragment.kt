package com.example.narcosdb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.narcosdb.R
import com.example.narcosdb.fragments.detailsFragments.*


class LoansFragment : Fragment() {

    private var lendButton: Button? = null
    private var returnMoneyButton: Button? = null
    private var loanHistoryButton: Button? = null
    private var loanStatsButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_loans, container, false)

        lendButton = v.findViewById(R.id.lendButton)
        returnMoneyButton = v.findViewById(R.id.returnMoneyButton)
        loanHistoryButton = v.findViewById(R.id.loanHistoryButton)
        loanStatsButton = v.findViewById(R.id.loanStatsButton)

        lendButton?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, LendMoneyFragment())
            transaction?.addToBackStack("lendmoney")
            transaction?.commit()
        }

        returnMoneyButton?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, ReturnMoneyFragment())
            transaction?.addToBackStack("returnmoney")
            transaction?.commit()
        }

        loanHistoryButton?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, LoanHistoryFragment())
            transaction?.addToBackStack("loanhistory")
            transaction?.commit()
        }

        loanStatsButton?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framelayout_id, LoanStatsFragment())
            transaction?.addToBackStack("loanstats")
            transaction?.commit()
        }

        return v
    }

}
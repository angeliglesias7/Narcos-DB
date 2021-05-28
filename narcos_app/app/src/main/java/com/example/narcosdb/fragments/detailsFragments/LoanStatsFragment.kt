package com.example.narcosdb.fragments.detailsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.narcosdb.R
import com.example.narcosdb.viewmodel.LoanViewModel

class LoanStatsFragment : Fragment() {

    private var totalMadeLoan: TextView? = null
    private var totalToPayLoan: TextView? = null
    private var totalMoneyReceived: TextView? = null
    private var totalMoneyPaid: TextView? = null
    private var pendingMoneyToReceive: TextView? = null
    private var pendingMoneyToPay: TextView? = null
    private var contactMoreLoansMade: TextView? = null
    private var contactMoreLoansToPay: TextView? = null
    private var loanViewModel: LoanViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_loan_stats, container, false)

        totalMadeLoan = v.findViewById(R.id.totalMadeLoan) as TextView
        totalToPayLoan = v.findViewById(R.id.totalToPayLoan) as TextView
        totalMoneyReceived = v.findViewById(R.id.totalMoneyReceived) as TextView
        totalMoneyPaid = v.findViewById(R.id.totalMoneyPaid) as TextView
        pendingMoneyToReceive = v.findViewById(R.id.pendingMoneyToReceive) as TextView
        pendingMoneyToPay = v.findViewById(R.id.pendingMoneyToPay) as TextView
        contactMoreLoansMade = v.findViewById(R.id.contactMoreLoansMade) as TextView
        contactMoreLoansToPay = v.findViewById(R.id.contactMoreLoansToPay) as TextView

        loanViewModel = activity?.application?.let { LoanViewModel(it) }
        getTotalMoneyReceived()
        getTotalMoneyPaid()

        return v
    }

    private fun getTotalMoneyReceived() {
        loanViewModel?.getAllMoneyReceived()!!.observe(activity!!,
            Observer<Float> { aFloat ->
                if (aFloat != null) {
                    totalMoneyReceived!!.text = "$aFloat €"
                } else {
                    totalMoneyReceived!!.text = "0 €"
                }
            })
    }

    private fun getTotalMoneyPaid() {
        loanViewModel?.getAllMoneyPaid()!!.observe(activity!!,
            Observer<Float> { aFloat ->
                if (aFloat != null) {
                    totalMoneyPaid!!.text = "$aFloat €"
                } else {
                    totalMoneyPaid!!.text = "0 €"
                }
            })
    }

}
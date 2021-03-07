package com.example.narcosdb.fragments.detailsFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.narcosdb.R
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.viewmodel.MoneyWarehouseViewModel


class MoneyWarehouseDetailsFragment : Fragment() {
    private var frameLayout: DrawerLayout? = null
    private var eMwName: EditText? = null
    private var eMwPlace: EditText? = null
    private var eMwM2: EditText? = null
    private var eMwAmountMoney: EditText? = null
    private var saveMw: Button? = null
    private var deleteMw: Button? = null
    private var isNewMw: Boolean? = null
    private var mwViewModel: MoneyWarehouseViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_money_warehouse_details, container, false)
        frameLayout = v.findViewById<View>(R.id.newMoneyWarehouse_layout) as DrawerLayout
        saveMw = v.findViewById(R.id.saveMoneyWarehouse)
        deleteMw = v.findViewById(R.id.deleteMoneyWarehouse)
        eMwName = v.findViewById(R.id.eMWName)
        eMwPlace = v.findViewById(R.id.eMWPlace)
        eMwM2 = v.findViewById(R.id.eMWM2)
        eMwAmountMoney = v.findViewById(R.id.eMWAmountMoney)
        mwViewModel = ViewModelProvider(requireActivity()).get(MoneyWarehouseViewModel::class.java)
        try {
            val bundle = this.arguments
            if (bundle != null) {
                val name = bundle.getString("name")
                val quality = bundle.getString("place")
                val price = bundle.getString("m2")
                val description = bundle.getString("amountMoney")
                eMwName?.setText(name)
                eMwPlace?.setText(quality)
                eMwM2?.setText(price)
                eMwAmountMoney?.setText(description)
                isNewMw = false
            } else {
                isNewMw = true
                deleteMw?.visibility = View.GONE
            }
        } catch (e: Exception) {
            println(e.message)
        }
        saveMw?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val moneyWarehouse = getMoneyWarehouse
                if (isNewMw == true) {
                    mwViewModel?.insert(moneyWarehouse)
                } else {
                    mwViewModel?.update(moneyWarehouse)
                }
            }
        })
        deleteMw?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val moneyWarehouse = getMoneyWarehouse
                mwViewModel?.delete(moneyWarehouse)
            }
        })
        return v
    }

    private fun checkFields(): Boolean {
        return !eMwName?.text.toString().isNullOrEmpty()  && !eMwPlace?.text.toString().isNullOrEmpty()
                && !eMwM2?.text.toString().isNullOrEmpty() && !eMwAmountMoney?.text.toString().isNullOrEmpty()
    }

    private val getMoneyWarehouse: MoneyWarehouse
        get() = MoneyWarehouse(eMwName!!.text.toString(), eMwPlace!!.text.toString(), eMwM2!!.text.toString().toInt(), eMwAmountMoney!!.text.toString().toDouble())


}
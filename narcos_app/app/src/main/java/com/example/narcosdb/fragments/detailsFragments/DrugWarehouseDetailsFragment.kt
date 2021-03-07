package com.example.narcosdb.fragments.detailsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.narcosdb.R
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.viewmodel.DrugWarehouseViewModel


class DrugWarehouseDetailsFragment : Fragment() {
    private var frameLayout: DrawerLayout? = null
    private var eDwName: EditText? = null
    private var eDwPlace: EditText? = null
    private var eDwM2: EditText? = null
    private var saveDw: Button? = null
    private var deleteDw: Button? = null
    private var destroyDw: Button? = null
    private var isNewDw: Boolean? = null
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
        eDwName = v.findViewById(R.id.eDWName)
        eDwPlace = v.findViewById(R.id.eDWPlace)
        eDwM2 = v.findViewById(R.id.eDWM2)
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
        saveDw?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val moneyWarehouse = getDrugWarehouse
                if (isNewDw == true) {
                    dwViewModel?.insert(moneyWarehouse)
                } else {
                    dwViewModel?.update(moneyWarehouse)
                }
            }
        })
        deleteDw?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val moneyWarehouse = getDrugWarehouse
                dwViewModel?.delete(moneyWarehouse)
            }
        })
        return v
    }

    private fun checkFields(): Boolean {
        return !eDwName?.text.toString().isNullOrEmpty()  && !eDwPlace?.text.toString().isNullOrEmpty()
                && !eDwM2?.text.toString().isNullOrEmpty()
    }

    private val getDrugWarehouse: DrugWarehouse
        get() = DrugWarehouse(eDwName!!.text.toString(), eDwPlace!!.text.toString(), eDwM2!!.text.toString().toInt())

}
package com.example.narcosdb.fragments.detailsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.narcosdb.R
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.viewmodel.DrugViewModel

class DrugDetailsFragment : Fragment() {
    private var frameLayout: DrawerLayout? = null
    private var eDrugName: EditText? = null
    private var eDrugQuality: EditText? = null
    private var eDrugPrice: EditText? = null
    private var eDrugDescription: EditText? = null
    private var saveDrug: Button? = null
    private var deleteDrug: Button? = null
    private var isNewDrug: Boolean? = null
    private var drugViewModel: DrugViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_drug_details, container, false)
        frameLayout = v.findViewById<View>(R.id.newDrug_layout) as DrawerLayout
        saveDrug = v.findViewById(R.id.saveDrug)
        deleteDrug = v.findViewById(R.id.deleteDrug)
        eDrugName = v.findViewById(R.id.eDrugName)
        eDrugQuality = v.findViewById(R.id.eDrugQuality)
        eDrugPrice = v.findViewById(R.id.eDrugPrice)
        eDrugDescription = v.findViewById(R.id.eDrugDescription)
        drugViewModel = ViewModelProvider(requireActivity()).get(DrugViewModel::class.java)
        try {
            val bundle = this.arguments
            if (bundle != null) {
                val name = bundle.getString("name")
                val quality = bundle.getString("quality")
                val price = bundle.getString("price")
                val description = bundle.getString("description")
                eDrugName?.setText(name)
                eDrugQuality?.setText(quality)
                eDrugPrice?.setText(price)
                eDrugDescription?.setText(description)
                isNewDrug = false
            } else {
                isNewDrug = true
                deleteDrug?.visibility = View.GONE
            }
        } catch (e: Exception) {
            println(e.message)
        }
        saveDrug?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val drug = drug
                if (isNewDrug == true) {
                    drugViewModel?.insert(drug)
                } else {
                    drugViewModel?.update(drug)
                }
            }
        })
        deleteDrug?.setOnClickListener(View.OnClickListener {
            if (checkFields()) {
                val drug = drug
                drugViewModel?.delete(drug)
            }
        })
        return v
    }

    fun showToast(result: Boolean) {
        val toast: Toast = if (result) {
            Toast.makeText(context, "Droga creada correctamente", Toast.LENGTH_LONG)
        } else {
            Toast.makeText(context, "Error. La droga ya existe", Toast.LENGTH_LONG)
        }
        toast.show()
    }

    private fun checkFields(): Boolean {
        return !eDrugName?.text.toString().isNullOrEmpty() && !eDrugPrice?.text.toString().isNullOrEmpty() &&
            !eDrugQuality?.text.toString().isNullOrEmpty() && !eDrugDescription?.text.toString().isNullOrEmpty()
    }

    private val drug: Drug
        private get() = Drug(eDrugName!!.text.toString(), eDrugPrice!!.text.toString().toFloat(), eDrugQuality!!.text.toString().toInt(), eDrugDescription!!.text.toString())
}
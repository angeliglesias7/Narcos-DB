package com.example.narcosdb.activity

import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.narcosdb.R
import com.example.narcosdb.entity.Drug


class TestActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        listView = findViewById(R.id.listView)
        button = findViewById(R.id.button)

        listView?.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listView?.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val v = view as CheckedTextView
            val currentCheck = v.isChecked
            val drug: Drug = listView?.getItemAtPosition(position) as Drug
            drug.active = !currentCheck
        }

        button?.setOnClickListener { printSelectedItems() }

        this.initListViewData()
    }

    private fun initListViewData() {
        val cocaina = Drug("Cocaina", 80.0, 80, "buena")
        val heroina = Drug("Heroina", 70.0, 70, "buena")
        val marihuana = Drug("Marihuana", 60.0, 70, "buena")
        marihuana.active = true
        val drugs: Array<Drug> = arrayOf<Drug>(cocaina, heroina, marihuana)

        // android.R.layout.simple_list_item_checked:
        // ListItem is very simple (Only one CheckedTextView).
        val arrayAdapter: ArrayAdapter<Drug> =
            ArrayAdapter<Drug>(this, android.R.layout.simple_list_item_multiple_choice, drugs)
        listView!!.adapter = arrayAdapter
        for (i in drugs.indices) {
            listView!!.setItemChecked(i, drugs[i].active)
        }
    }

    private fun printSelectedItems() {
        val sp = listView!!.checkedItemPositions
        var sb = StringBuilder()
        for (i in 0 until sp.size()) {
            if (sp.valueAt(i)) {
                val drug: Drug = listView!!.getItemAtPosition(i) as Drug
                val s: String = drug.name
                sb = sb.append(" $s")
            }
        }
        Toast.makeText(this, "Selected items are: $sb", Toast.LENGTH_LONG).show()
    }
}
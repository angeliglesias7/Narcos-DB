package com.example.narcosdb

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.narcosdb.activity.LoginActivity
import com.example.narcosdb.fragments.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var drawer: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    private var frameLayout: FrameLayout? = null
    private var navigationView: NavigationView? = null
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.principalText)
        textView?.text = "Bienvenido de nuevo a Narcos DB. Utilizando el menú de la izquierda podrá acceder a las funcionalidades de la aplicación "
        initializeViews()
        toggleDrawer()
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.toolbar_title)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        frameLayout = findViewById(R.id.framelayout_id)
        navigationView = findViewById(R.id.nav_view)
        navigationView?.setNavigationItemSelectedListener(this)
    }

    private fun toggleDrawer() {
        val drawerToggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_drug -> {
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_id, DrugFragment())
                    .addToBackStack("drug")
                    .commit()
                closeDrawer()
            }
            R.id.nav_money_warehouse -> {
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_id, MoneyWarehouseFragment())
                    .addToBackStack("moneywarehouse").commit()
                closeDrawer()
            }
            R.id.nav_drug_warehouse -> {
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_id, DrugWarehouseFragment())
                    .addToBackStack("drugwarehouse").commit()
                closeDrawer()
            }
            R.id.nav_contact -> {
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_id, ContactFragment())
                    .addToBackStack("contact").commit()
                closeDrawer()
            }
            R.id.nav_history -> {
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_id, HistoryFragment())
                    .addToBackStack("history").commit()
                closeDrawer()
            }
            R.id.nav_loans -> {
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_id, LoansFragment())
                    .addToBackStack("loans").commit()
                closeDrawer()
            }
            R.id.nav_buys -> {
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_id, BuysAndSalesFragment())
                    .addToBackStack("buys").commit()
                closeDrawer()
            }
        }
        return true
    }

    private fun closeDrawer() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                val alertBuilder = AlertDialog.Builder(this@MainActivity)
                alertBuilder.setIcon(android.R.drawable.ic_dialog_alert)
                alertBuilder.setTitle(resources.getString(R.string.menu_close_session))
                alertBuilder.setMessage(resources.getString(R.string.string_close_session))
                alertBuilder.setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    deleteCredentials()
                    goToLogin()
                }
                alertBuilder.setNegativeButton(resources.getString(R.string.no), null)
                val dialog = alertBuilder.create()
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToLogin() {
        val i = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(i)
    }

    private fun deleteCredentials() {
        val preferences = applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear().commit()
    }

    private fun loadUsername(): String? {
        val preferences = applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        return preferences.getString("username", null)
    }
}
package com.example.narcosdb.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.narcosdb.MainActivity
import com.example.narcosdb.R

class LoginActivity : AppCompatActivity() {

    private var code: EditText? = null
    private var loginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        code = findViewById(R.id.code)
        loginButton = findViewById(R.id.loginButton)
        loginButton?.setOnClickListener(View.OnClickListener {
            var insertedCode = code?.text.toString()
            if(getString(R.string.app_code) == insertedCode){
                goToHome()
            }else{
                val toast = Toast.makeText(applicationContext, "Código incorrecto", Toast.LENGTH_LONG)
                toast.show()
            }
        })

    }

    private fun goToHome() {
        val i = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }




}
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boton= findViewById<Button>(R.id.buttoN);
        val text= findViewById<TextView>(R.id.texT)
        boton.setOnClickListener {
            if(boton.getText().toString().equals("Polo")){
                boton.setText(getString(R.string.marco))
                text.setText(getString(R.string.polo))
            }
            else {
                boton.setText(R.string.polo)
                text.setText(R.string.marco)
            }
        }

    }





}
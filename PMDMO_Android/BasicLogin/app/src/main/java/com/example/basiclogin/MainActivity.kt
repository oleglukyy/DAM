package com.example.basiclogin

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    val USERNAME = "baldomero"
    val PASSWORD = "llegateligero"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameField = findViewById<TextInputLayout>(R.id.inUsername)
        val passwordField = findViewById<TextInputLayout>(R.id.inPassword)

        val loginButton = findViewById<Button>(R.id.btLogin)

        loginButton.setOnClickListener {

            val username = getFieldValue(usernameField, "Can't be blank")
            val password = getFieldValue(passwordField, "Invalid password")
            if (username != null && password != null) {

                if (username != USERNAME) {
                    Snackbar.make(
                        findViewById<ConstraintLayout>(R.id.main),
                        "User not found",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {

                    if (password != PASSWORD) {

                        passwordField.error = "Incorrect password"

                    } else {

                        Snackbar.make(
                            findViewById<ConstraintLayout>(R.id.main),
                            "User logged in",
                            Snackbar.LENGTH_SHORT
                        ).setAction("SHOW") {
                            MaterialAlertDialogBuilder(this)
                                .setTitle("User data")
                                .setMessage("Logged in as $username")
                                .setPositiveButton("Accept") { dialog, which ->
                                    dialog.dismiss()
                                }
                                .create()
                                .show()
                        }.show()

                    }

                }

            }


        }

    }

    fun getFieldValue(field: TextInputLayout, message: String): String? {

        val text = field.editText?.text.toString()

        if (text.isBlank()) {
            field.error = message
            return null
        }
        field.error = null
        return text


    }
}
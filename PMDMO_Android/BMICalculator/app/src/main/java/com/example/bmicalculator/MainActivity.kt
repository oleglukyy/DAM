package com.example.bmicalculator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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

        val unitOption = findViewById<RadioGroup>(R.id.ragOptions)

        val weightLayout = findViewById<TextInputLayout>(R.id.layoutWeight)
        val heightLayout = findViewById<TextInputLayout>(R.id.layoutHeight)

        val calculateButton = findViewById<Button>(R.id.btCalculate)
        val resetButton = findViewById<Button>(R.id.btReset)

        val classCheckBox = findViewById<CheckBox>(R.id.cbClassification)
        val result = findViewById<TextView>(R.id.result)

        val image = findViewById<ImageView>(R.id.imClassification)



        unitOption.setOnCheckedChangeListener { _, i ->

            if (i == R.id.metric) {
                weightLayout.suffixText = getString(R.string.imperialWeight)
                heightLayout.suffixText = getString(R.string.imperialHeight)
            } else {
                weightLayout.suffixText = getString(R.string.metricWeight)
                heightLayout.suffixText = getString(R.string.metricHeight)
            }
        }

        calculateButton.setOnClickListener {


            val isWeightValid = validateInput(weightLayout)
            val isHeightValid = validateInput(heightLayout)

            if (isWeightValid && isHeightValid) {

                val weight = weightLayout.editText?.text.toString().toDouble()
                val height = heightLayout.editText?.text.toString().toDouble()

                val bmi = weight / (height * height)

                result.text = "BMI: %.2f".format(bmi)
                result.setBackgroundColor(Color.MAGENTA)

                if (classCheckBox.isChecked) {
                    when {
                        bmi < 18.5 -> image.setImageResource(R.drawable.underweight)
                        bmi < 24.9 -> image.setImageResource(R.drawable.normal_weight)
                        bmi < 29.9 -> image.setImageResource(R.drawable.overweight)
                        bmi < 34.9 -> image.setImageResource(R.drawable.obesity1)
                        bmi < 40 -> image.setImageResource(R.drawable.obesity2)
                        else -> image.setImageResource(R.drawable.obesity3)
                    }
                    image.visibility = View.VISIBLE
                } else {
                    image.visibility = View.INVISIBLE
                }

            }
        }


        resetButton.setOnClickListener {
            weightLayout.editText?.setText("")
            heightLayout.editText?.setText("")
            image.visibility = View.INVISIBLE
            result.text = ""
            result.background = null
            weightLayout.error = null
            heightLayout.error = null

        }


    }
}

private fun validateInput(input: TextInputLayout): Boolean {

    if (input.editText?.text.toString().isBlank() || input.editText?.text.toString()
            .toDouble() < 0
    ) {
        input.error = "Invalid " + input.hint
        return false;
    } else {
        input.error = null
    }

    return true
}


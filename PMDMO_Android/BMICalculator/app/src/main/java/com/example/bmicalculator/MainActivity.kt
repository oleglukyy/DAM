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
import com.google.android.material.switchmaterial.SwitchMaterial
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

        val convertSwitch = findViewById<SwitchMaterial>(R.id.swConvert)
        val unitOption = findViewById<RadioGroup>(R.id.ragOptions)

        val weightLayout = findViewById<TextInputLayout>(R.id.layoutWeight)
        val heightLayout = findViewById<TextInputLayout>(R.id.layoutHeight)

        val calculateButton = findViewById<Button>(R.id.btCalculate)
        val resetButton = findViewById<Button>(R.id.btReset)

        val classCheckBox = findViewById<CheckBox>(R.id.cbClassification)
        val result = findViewById<TextView>(R.id.result)

        val image = findViewById<ImageView>(R.id.imClassification)



        unitOption.setOnCheckedChangeListener { _, i ->
            val convert = convertSwitch.isChecked
            val currentWeight = weightLayout.editText?.text.toString().toDoubleOrNull()
            val currentHeight = heightLayout.editText?.text.toString().toDoubleOrNull()

            if (i == R.id.imperial) {
                weightLayout.suffixText = getString(R.string.imperialWeight)
                heightLayout.suffixText = getString(R.string.imperialHeight)
                if (convert && currentWeight != null && currentHeight != null) {
                    weightLayout.editText?.setText("${currentWeight * 2.2}")
                    heightLayout.editText?.setText("${currentHeight * 39.3}")

                }
            } else {
                weightLayout.suffixText = getString(R.string.metricWeight)
                heightLayout.suffixText = getString(R.string.metricHeight)
                if (convert && currentWeight != null && currentHeight != null) {
                    weightLayout.editText?.setText("${currentWeight / 2.2}")
                    heightLayout.editText?.setText("${currentHeight / 39.3}")
                }
            }



        }

        calculateButton.setOnClickListener {


            val weight = getValidNumber(weightLayout)
            val height = getValidNumber(heightLayout)

            if (weight !=null && height !=null) {
                var bmi = weight / (height * height)
                    if (unitOption.checkedRadioButtonId == R.id.imperial) {
                        bmi *= 703
                    }

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
            convertSwitch.isChecked = false
            unitOption.check(R.id.metric)
            classCheckBox.isChecked = false

            weightLayout.editText?.setText("")
            heightLayout.editText?.setText("")

            image.visibility = View.INVISIBLE
            result.text = ""
            result.background = null

            weightLayout.error = null
            heightLayout.error = null
        }


    }
    private fun getValidNumber(input: TextInputLayout): Double? {
        val value = input.editText?.text?.toString()
            ?.trim()
            ?.replace(',', '.')
            ?.toDoubleOrNull()

        if (value == null || value <= 0) {
            input.error = "Introduce un número mayor que 0"
            return null
        }

        input.error = null
        return value
    }
}




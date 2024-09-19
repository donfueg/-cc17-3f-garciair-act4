package com.example.tipcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Ensure this works for your API level
        setContentView(R.layout.activity_main)

        // Set up insets for edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tipEditText: EditText = findViewById(R.id.cost_of_service)
        val ratingGroup: RadioGroup = findViewById(R.id.RadioGroup) // Make sure the ID matches your layout
        val roundSwitch: Switch = findViewById(R.id.switch1)
        val calculateButton: Button = findViewById(R.id.button) // Ensure the ID matches your layout
        val resultTextView: TextView = findViewById(R.id.amount)

        calculateButton.setOnClickListener {
            val tipInput = tipEditText.text.toString()
            val tipAmount = tipInput.toDoubleOrNull()

            if (tipAmount == null || tipAmount <= 0) {
                resultTextView.text = getString(R.string.tip_amount, 0.0)
                return@setOnClickListener
            }

            val tipPercent = when (ratingGroup.checkedRadioButtonId) {
                R.id.amazing -> 0.20
                R.id.good -> 0.18
                else -> 0.15 // Default case if no radio button is selected
            }

            var calculatedTip = tipAmount * tipPercent
            if (roundSwitch.isChecked) {
                calculatedTip = kotlin.math.ceil(calculatedTip)
            }

            resultTextView.text = getString(R.string.tip_amount, calculatedTip)
        }
    }
}

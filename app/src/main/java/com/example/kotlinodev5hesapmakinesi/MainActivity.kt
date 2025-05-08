package com.example.kotlinodev5hesapmakinesi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinodev5hesapmakinesi.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var textViewDisplay: TextView
    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var buttonAdd: Button
    private lateinit var buttonEquals: Button
    private lateinit var buttonReset: Button

    private var currentNumber: String = ""
    private var firstNumber: Double = 0.0
    private var isOperatorPressed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textViewDisplay = binding.textViewDisplay
        button0 = binding.button0
        button1 = binding.button1
        button2 = binding.button2
        button3 = binding.button3
        button4 = binding.button4
        button5 = binding.button5
        button6 = binding.button6
        button7 = binding.button7
        button8 = binding.button8
        button9 = binding.button9
        buttonAdd = binding.buttonAdd
        buttonEquals = binding.buttonEquals
        buttonReset = binding.buttonReset

        button0.setOnClickListener { appendDigit("0") }
        button1.setOnClickListener { appendDigit("1") }
        button2.setOnClickListener { appendDigit("2") }
        button3.setOnClickListener { appendDigit("3") }
        button4.setOnClickListener { appendDigit("4") }
        button5.setOnClickListener { appendDigit("5") }
        button6.setOnClickListener { appendDigit("6") }
        button7.setOnClickListener { appendDigit("7") }
        button8.setOnClickListener { appendDigit("8") }
        button9.setOnClickListener { appendDigit("9") }

        buttonAdd.setOnClickListener { handleOperator("+") }

        buttonEquals.setOnClickListener { calculateResult() }

        buttonReset.setOnClickListener { resetCalculator() }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun appendDigit(digit: String) {
        if (currentNumber == "0" && digit == "0") {
            return
        }
        if (currentNumber == "0") {
            currentNumber = digit
        } else {
            currentNumber += digit
        }
        textViewDisplay.text = currentNumber
    }

    private fun handleOperator(operator: String) {
        if (currentNumber.isNotEmpty()) {
            try {
                firstNumber = currentNumber.toDouble()
                isOperatorPressed = true
                currentNumber = ""

                if (firstNumber == firstNumber.toInt().toDouble()) {
                    textViewDisplay.text = "${firstNumber.toInt()} $operator"
                } else {
                    textViewDisplay.text = "$firstNumber $operator"
                }

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
                resetCalculator()
            }
        } else if (firstNumber != 0.0 && !isOperatorPressed) {
            isOperatorPressed = true
            if (firstNumber == firstNumber.toInt().toDouble()) {
                textViewDisplay.text = "${firstNumber.toInt()} $operator"
            } else {
                textViewDisplay.text = "$firstNumber $operator"
            }
        }
    }

    private fun calculateResult() {
        if (isOperatorPressed && currentNumber.isNotEmpty()) {
            try {
                val secondNumber = currentNumber.toDouble()
                val result = firstNumber + secondNumber

                if (result == result.toInt().toDouble()) {
                    textViewDisplay.text = result.toInt().toString()
                    currentNumber = result.toInt().toString()
                } else {
                    textViewDisplay.text = result.toString()
                    currentNumber = result.toString()
                }

                firstNumber = result
                isOperatorPressed = false
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
                resetCalculator()
            }
        } else if (!isOperatorPressed && currentNumber.isNotEmpty()){
            textViewDisplay.text = currentNumber
            try {
                firstNumber = currentNumber.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
                resetCalculator()
            }
            currentNumber = ""
        }
    }

    private fun resetCalculator() {
        currentNumber = ""
        firstNumber = 0.0
        isOperatorPressed = false
        textViewDisplay.text = "0"
    }
}

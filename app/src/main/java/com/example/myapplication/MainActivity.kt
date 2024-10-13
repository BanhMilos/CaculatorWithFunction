package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentNumber: String = ""
    private var operator: String? = null
    private var firstNumber: Double = 0.0
    private var isOperatorPressed: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        display = findViewById(R.id.textView2)
        val buttons = listOf(
            R.id.button0,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
        )
        buttons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                numberPressed((it as Button).text.toString())
            }
        }
        findViewById<Button>(R.id.buttonPlus).setOnClickListener { operatorPressed("+") }
        findViewById<Button>(R.id.buttonMinus).setOnClickListener { operatorPressed("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { operatorPressed("*") }
        findViewById<Button>(R.id.buttonSlash).setOnClickListener { operatorPressed("/") }

        findViewById<Button>(R.id.buttonEqual).setOnClickListener { caculateResult() }
        findViewById<Button>(R.id.buttonC).setOnClickListener { clear() }
        findViewById<Button>(R.id.buttonCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.buttonDot).setOnClickListener { addDecimal() }
    }
    private fun numberPressed(number: String){
            if (isOperatorPressed){
                currentNumber = ""
                isOperatorPressed = false
            }
            currentNumber += number
            display.text = currentNumber
    }
    private fun operatorPressed(op: String){
            if (currentNumber.isNotEmpty()){
                firstNumber = currentNumber.toDouble()
                operator = op
                isOperatorPressed = true
            }
    }

    private fun caculateResult(){
        val secondNumber = currentNumber.toDoubleOrNull() ?: return
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else "Error"
            else -> return
        }
        display.text = result.toString()
        currentNumber = result.toString()
        isOperatorPressed = false
    }

    private fun clear(){
        currentNumber = ""
        firstNumber = 0.0
        operator = null
        display.text = "0"
    }

    private fun clearEntry(){
        currentNumber = ""
        display.text = "0"
    }

    private fun addDecimal(){
        if (!currentNumber.contains(".")){
            currentNumber += "."
            display.text = currentNumber
        }
    }
}
// DrawActivity.kt

package com.glen.midtermexam2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : BaseActivity() {

    private lateinit var display: TextView
    private var currentInput: StringBuilder = StringBuilder()
    private var currentOperator: String? = null
    private var operand1: Double? = null
    private var currentDisplay: StringBuilder = StringBuilder()
    private lateinit var ongoingEquationDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        supportActionBar?.title = "Calculator"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        display = findViewById(R.id.display)
        ongoingEquationDisplay = findViewById(R.id.ongoingEquationDisplay)


        // Digits
        val digitButtons = arrayOf(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9)
        for (buttonId in digitButtons) {
            findViewById<Button>(buttonId).setOnClickListener { onDigitClick(it) }
        }

        // Decimal point
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { onDecimalClick() }

        // Operations
        val operationButtons = arrayOf(R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide)
        for (buttonId in operationButtons) {
            findViewById<Button>(buttonId).setOnClickListener { onOperationClick(it) }
        }

        // Equals
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsClick() }

        // Additional functionalities
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { onDeleteClick() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { onPercentClick() }
        findViewById<Button>(R.id.btnToggleSign).setOnClickListener { onToggleSignClick() }
    }

    private fun clearIfMathError(): Boolean {
//        Toast.makeText(this, currentInput.toString(), Toast.LENGTH_SHORT).show()
        if (currentInput.contains("Error")) {
            currentInput.clear()
            currentDisplay.clear()
            updateDisplay()
            return true
        }
        return false
    }
    private fun updateIfNoOperand() {
        if (operand1 == null) {
            currentDisplay.clear()
            currentDisplay.append(currentInput)
        }
    }

    private fun onDigitClick(view: View) {
        clearIfMathError()
        val digit = (view as Button).text
        currentInput.append(digit)
        currentDisplay.append(digit)
        updateIfNoOperand()
        updateDisplay()
    }

    private fun onDecimalClick() {
        if(clearIfMathError()) {
            return
        }

        if (!currentInput.contains(".")) {
            currentInput.append(".")
            currentDisplay.append(".")
            updateIfNoOperand()
            updateDisplay()
        }
    }

    private fun onOperationClick(view: View) {

        if(clearIfMathError()) {
            return
        }
        if (operand1 != null && currentInput.isNotEmpty()) {
            equate()
        }

        if (currentInput.isEmpty()) {
            return
        }

        if (operand1 == null) {
            operand1 = currentInput.toString().toDouble()
            currentInput.clear()
        }

        currentOperator = (view as Button).text.toString()

        // Update the ongoing equation display
        currentDisplay.clear()
        currentDisplay.append("$operand1 $currentOperator ")
        updateDisplay()
    }

    private fun onEqualsClick() {
        if(clearIfMathError()) {
            return
        }
        equate()
    }

    private fun equate() {
        currentDisplay.append(" =")
        if (operand1 != null && currentOperator != null && currentInput.isNotEmpty()) {
            val operand2 = currentInput.toString().toDouble()

            if (currentOperator == "/" && operand2 == 0.0) {
                // Handle division by zero error
                currentInput.clear()
                currentInput.append("Math Error")
                updateDisplay()
                operand1 = null
                currentOperator = null
                return
            }

            val result = when (currentOperator) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "*" -> operand1!! * operand2
                "/" -> operand1!! / operand2
                else -> 0.0
            }

            val formattedResult = String.format("%.5f", result)

            currentInput.clear()
            currentInput.append(formattedResult)
            updateDisplay()
            operand1 = null
            currentOperator = null
        }
    }


    private fun onClearClick() {
        currentInput.clear()
        operand1 = null
        currentOperator = null
        currentDisplay.clear()
        updateDisplay()
    }

    private fun onDeleteClick() {
        if(clearIfMathError()) {
            return
        }

        if (currentInput.isNotEmpty()) {
            currentInput.deleteCharAt(currentInput.length - 1)
            currentDisplay.deleteCharAt(currentDisplay.length - 1)
            updateIfNoOperand()
            updateDisplay()
        }
    }

    private fun onPercentClick() {
        if(clearIfMathError()) {
            return
        }
        if (currentInput.isNotEmpty()) {
            val input = currentInput.toString().toDouble()
            val result = input / 100
            currentInput.clear()
            currentInput.append(result)
            updateIfNoOperand()
            updateDisplay()
        }
    }

    private fun onToggleSignClick() {
        if(clearIfMathError()) {
            return
        }
        if (currentInput.isNotEmpty() && currentInput.toString().toDouble() != 0.0) {
            val input = currentInput.toString().toDouble()
            val result = -input
            currentInput.clear()
            currentInput.append(result)

            updateIfNoOperand()
            updateDisplay()

        }

    }

    private fun updateDisplay() {
        ongoingEquationDisplay.text = currentDisplay.toString()
        display.text = currentInput.toString()
    }
}

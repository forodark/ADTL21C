// DrawActivity.kt

package com.glen.midtermexam2

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class LightsOutActivity : BaseActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var cells: Array<Array<Button>>
    private lateinit var moveCounterTextView: TextView
    private var moveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lightsout)

        supportActionBar?.title = "Lights Out"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gridLayout = findViewById(R.id.gridLayout)
        moveCounterTextView = findViewById(R.id.moveCounterTextView)

        initializeGame()
        randomizeCells()

        val resetButton: Button = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initializeGame() {
        cells = Array(5) { row ->
            Array(5) { col ->
                createCell(row, col)
            }
        }
        moveCount = 0
        updateMoveCounter()
    }

    private fun randomizeCells() {
        for (row in cells.indices) {
            for (col in cells[row].indices) {
                if (Random.nextBoolean()) {
                    toggleCellState(row, col)
                    updateAdjacentCells(row, col)
                }
            }
        }
    }

    private fun createCell(row: Int, col: Int): Button {
        val cell = Button(this)

        // Set fixed dimensions for each cell
        val cellSize = resources.getDimensionPixelSize(R.dimen.cell_size)
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.width = cellSize
        layoutParams.height = cellSize
        cell.layoutParams = layoutParams

        // Set gravity to center the content within the cell
        cell.gravity = Gravity.CENTER

        cell.setBackgroundResource(R.drawable.cell_background)
        cell.setOnClickListener { onCellClick(it) }
        cell.tag = CellTag(row, col)
        gridLayout.addView(cell)
        return cell
    }



    private fun onCellClick(view: View) {
        val cellTag = view.tag as CellTag
        toggleCellState(cellTag.row, cellTag.col)
        updateAdjacentCells(cellTag.row, cellTag.col)
        incrementMoveCounter()
        checkForWin()
    }

    private fun toggleCellState(row: Int, col: Int) {
        val cell = cells[row][col]
        val currentState = cell.background.constantState
        val newState = if (currentState == ContextCompat.getDrawable(this, R.drawable.cell_background)?.constantState) {
            ContextCompat.getDrawable(this, R.drawable.cell_background_off)
        } else {
            ContextCompat.getDrawable(this, R.drawable.cell_background)
        }
        cell.background = newState
    }

    private fun updateAdjacentCells(row: Int, col: Int) {
        val directions = arrayOf(
            Pair(-1, 0),
            Pair(1, 0),
            Pair(0, -1),
            Pair(0, 1)
        )

        for (direction in directions) {
            val newRow = row + direction.first
            val newCol = col + direction.second
            if (newRow in 0..4 && newCol in 0..4) {
                toggleCellState(newRow, newCol)
            }
        }
    }

    private fun checkForWin() {
        var allCellsOff = true

        for (row in cells.indices) {
            for (col in cells[row].indices) {
                val cell = cells[row][col]
                val currentState = cell.background.constantState

                if (currentState != ContextCompat.getDrawable(this, R.drawable.cell_background_off)?.constantState) {
                    allCellsOff = false
                    break
                }
            }
            if (!allCellsOff) {
                break
            }
        }

        if (allCellsOff) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Congratulations!")
            alertDialogBuilder.setMessage("You've won the game in $moveCount moves.")
            alertDialogBuilder.setPositiveButton("Back") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Reset Game") { _, _ ->
                resetGame()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun resetGame() {
        clearCells()
        initializeGame()
        randomizeCells()
    }

    private fun clearCells() {
        for (row in cells.indices) {
            for (col in cells[row].indices) {
                gridLayout.removeView(cells[row][col])
            }
        }
    }

    private fun incrementMoveCounter() {
        moveCount++
        updateMoveCounter()
    }

    private fun updateMoveCounter() {
        moveCounterTextView.text = "Target: 12  Moves: $moveCount"
    }



    private data class CellTag(val row: Int, val col: Int)
}

package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var board = Array(3) { Array(3) { "" } }
    private var isXTurn = true
    private val buttons = mutableListOf<Button>()
    private var isGame = true;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons
        buttons.addAll(
            listOf(
                findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3),
                findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6),
                findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9)
            )
        )

        // Set click listeners for each button
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { handleButtonClick(index / 3, index % 3, button) }
        }

        // Start button logic (optional, reset game)
        findViewById<Button>(R.id.start_button)?.setOnClickListener { if(isGame){
            Toast.makeText(this,"Game is still played",Toast.LENGTH_SHORT).show()
        } else{
            resetGame()
        } }
    }

    private fun handleButtonClick(row: Int, col: Int, button: Button) {
        if (board[row][col].isNotEmpty()) {
            Toast.makeText(this, "Cell already taken!", Toast.LENGTH_SHORT).show()
            return
        }

        // Update board and button
        val symbol = if (isXTurn) "X" else "O"
        board[row][col] = symbol
        button.text = symbol
        button.isEnabled = false

        // Check for win or draw
        if (checkWinner(symbol)) {
            Toast.makeText(this, "$symbol wins!", Toast.LENGTH_LONG).show()
            disableAllButtons()
            isGame=false
        } else if (isBoardFull()) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show()
            isGame=false
        }

        // Switch turn
        isXTurn = !isXTurn
    }

    private fun checkWinner(symbol: String): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)
            ) return true
        }

        // Check diagonals
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)
        ) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun disableAllButtons() {
        buttons.forEach { it.isEnabled = false }
    }

    private fun resetGame() {
        // Reset board state
        board = Array(3) { Array(3) { "" } }
        isXTurn = true

        // Reset buttons
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }

    }
}
package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int turn = 0; //0 - X, 1 - O
    private Boolean gameOver = false;
    private Boolean gameWon = false;
    private TextView currentTurnText;
    private TextView winnerText;

    private Button[] buttonsArray = new Button[9];
    private Button[] winnerButtons = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentTurnText = findViewById(R.id.CurrentTurn);
        winnerText = findViewById(R.id.Winner);

        buttonsArray[0] = findViewById(R.id.TopLeft);
        buttonsArray[1] = findViewById(R.id.TopMiddle);
        buttonsArray[2] = findViewById(R.id.TopRight);
        buttonsArray[3] = findViewById(R.id.MiddleLeft);
        buttonsArray[4] = findViewById(R.id.MiddleMiddle);
        buttonsArray[5] = findViewById(R.id.MiddleRight);
        buttonsArray[6] = findViewById(R.id.BottomLeft);
        buttonsArray[7] = findViewById(R.id.BottomMiddle);
        buttonsArray[8] = findViewById(R.id.BottomRight);

        initialStart();
    }


    private void initialStart(){
        turn = Math.random() < 0.5 ? 0 : 1;
        switch (turn) {
            case 0:
                currentTurnText.setText("Current Turn: X");
                break;
            case 1:
                currentTurnText.setText("Current Turn: O");
                break;
        }
    }

    public void assignSign(View view){
        Button pressedButton = (Button) view;

        if(pressedButton.getText().toString().isEmpty() && gameOver == false) {
            if (turn % 2 == 0) {
                pressedButton.setText("X");
                pressedButton.setBackgroundColor(Color.rgb(64, 118, 255));
                currentTurnText.setText("Current Turn: O");
            } else {
                pressedButton.setText("O");
                pressedButton.setBackgroundColor(Color.rgb(235, 12, 12));
                currentTurnText.setText("Current Turn: X");
            }
            turn++;
        }

        if(gameWon() == true){
            gameOver = true;
            gameWon = true;
        }

        if(checkBoxesEmpty() == false) {
            gameOver = true;
        }

        if(gameOver == true){
            currentTurnText.setText("Game Over");
            if(gameWon){
                winnerText.setText("Winner: " + ((turn % 2 == 1) ? 'X' : 'O'));
            }else{
                winnerText.setText("No Winner");
            }

        }
    }

    boolean gameWon(){
        char currentSymbol;

        // Check rows
        for (int i = 0; i < 8; i+=3) {
            String buttonText = buttonsArray[i].getText().toString();
            if (buttonText.isEmpty()) {
                continue; // Skip if no symbol is assigned to the button
            }
            currentSymbol = buttonText.charAt(0);

            // Check if the other two buttons in the row have the same symbol
            if (buttonsArray[i + 1].getText().toString().isEmpty() == false && buttonsArray[i + 2].getText().toString().isEmpty() == false
                    && currentSymbol == buttonsArray[i + 1].getText().toString().charAt(0)
                    && currentSymbol == buttonsArray[i + 2].getText().toString().charAt(0)) {
                winnerButtons[0] = buttonsArray[i];
                winnerButtons[1] = buttonsArray[i + 1];
                winnerButtons[2] = buttonsArray[i + 2];
                changeWinningButtons();
                return true; // Game won
            }
        }

        //Check columns
        for (int i = 0; i < 3; i ++) {
            String buttonText = buttonsArray[i].getText().toString();
            if (buttonText.isEmpty()) {
                continue; // Skip if no symbol is assigned to the button
            }
            currentSymbol = buttonText.charAt(0);

            // Check if the other two buttons in the row have the same symbol
            if (buttonsArray[i + 3].getText().toString().isEmpty() == false && buttonsArray[i + 6].getText().toString().isEmpty() == false
                    && currentSymbol == buttonsArray[i + 3].getText().toString().charAt(0)
                    && currentSymbol == buttonsArray[i + 6].getText().toString().charAt(0)) {
                winnerButtons[0] = buttonsArray[i];
                winnerButtons[1] = buttonsArray[i + 3];
                winnerButtons[2] = buttonsArray[i + 6];
                changeWinningButtons();
                return true; // Game won
            }
        }

        //Check diagonals
        if(buttonsArray[0].getText().toString().isEmpty() == false){
            currentSymbol = buttonsArray[0].getText().toString().charAt(0);
            if(buttonsArray[4].getText().toString().isEmpty() == false &&
                    currentSymbol == buttonsArray[4].getText().toString().charAt(0) &&
                    buttonsArray[8].getText().toString().isEmpty() == false &&
                    currentSymbol == buttonsArray[8].getText().toString().charAt(0)){
                winnerButtons[0] = buttonsArray[0];
                winnerButtons[1] = buttonsArray[4];
                winnerButtons[2] = buttonsArray[8];
                changeWinningButtons();
                return true; // Game won
            }
        }

        if(buttonsArray[2].getText().toString().isEmpty() == false){
            currentSymbol = buttonsArray[2].getText().toString().charAt(0);
            if(buttonsArray[4].getText().toString().isEmpty() == false &&
                    currentSymbol == buttonsArray[4].getText().toString().charAt(0) &&
                    buttonsArray[6].getText().toString().isEmpty() == false &&
                    currentSymbol == buttonsArray[6].getText().toString().charAt(0)){
                winnerButtons[0] = buttonsArray[2];
                winnerButtons[1] = buttonsArray[4];
                winnerButtons[2] = buttonsArray[6];
                changeWinningButtons();
                return true; // Game won
            }
        }



        return false;
    }

    void changeWinningButtons(){
        for(int i = 0; i < winnerButtons.length; i++){
            winnerButtons[i].setBackgroundColor(Color.rgb(49, 232, 64)); //Changes button color
        }
    }

    //Checks if any buttons are empty
    boolean checkBoxesEmpty(){
        for(int i = 0; i < buttonsArray.length; i++){
            if(buttonsArray[i].getText().toString().isEmpty()) {
                    return true;
                }
            }
        return false;
    }

    public void resetGame(View view){
        gameOver = false;
        gameWon = false;
        turn = 0;

        for(int i = 0; i < buttonsArray.length; i++){
            buttonsArray[i].setText("");
            buttonsArray[i].setBackgroundColor(Color.rgb(166, 166, 166));
        }

        initialStart();
    }
}
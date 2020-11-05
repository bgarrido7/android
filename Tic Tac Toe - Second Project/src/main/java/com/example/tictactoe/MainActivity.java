package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean p1Turn = true;
    private int roundCount, p1Points, p2Points;
    private TextView textViewP1, textViewP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewP1 = findViewById(R.id.text_view_p1);
        textViewP2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button button_reset = findViewById(R.id.rst);
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals(""))
            return;

        if (p1Turn)
            ((Button) view).setText("X");
        else
            ((Button) view).setText("O");

        roundCount++;
        if (checkforWin()) {
            if (p1Turn)
                player1Wins();
            else
                player2Wins();
        }
        else if (roundCount == 9)
            draw();
        else
            p1Turn = !p1Turn; //troca de jogador
    }

    private boolean checkforWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (!(field[i][0].equals("")) && //garante que nao está a comparar campos vazios
                    field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]))
                return true; //ganhou na horizontal
        }
        for (int i = 0; i < 3; i++) {
            if (!(field[0][i].equals("")) &&
                    field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]))
                return true; //ganhou na vertical
        }
        //ganhou na diagonal
        if (!(field[1][1].equals("")) &&
                field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]))
            return true;

        //ganhou na diagonal
        if (!(field[1][1].equals("")) &&
                field[2][0].equals(field[0][2]) && field[1][1].equals(field[0][2]))
            return true;

        return false;
    }
    private void player1Wins(){
        p1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        p2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        textViewP1.setText("Player 1: " + p1Points);
        textViewP2.setText("Player 2: " + p2Points);

    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        p1Turn = true;
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
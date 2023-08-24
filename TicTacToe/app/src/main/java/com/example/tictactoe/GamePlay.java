package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GamePlay extends AppCompatActivity {

    private TicTacToeBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);
        board = findViewById(R.id.ticTacToeBoard);

        String[] names = getIntent().getStringArrayExtra("PLAYER_NAMES");
        boolean pvp = getIntent().getExtras().getBoolean("PVP");
        board.label = findViewById(R.id.playerTurn);
        board.pvp = pvp;
        if (pvp) {
            board.p1 = names[0];
            board.p2 = names[1];
        } else {
            if (names[0].equals("Player 1")) {
                board.p1 = "Human";
            } else {
                board.p1 = names[0];
            }
            board.p2 = "Computer";
        }
        board.board.computer = !pvp;
    }

    public void goHomePage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void resetButton(View view){
        board.resetGame();
    }
}
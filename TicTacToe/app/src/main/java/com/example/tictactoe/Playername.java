package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Playername extends AppCompatActivity {

    private EditText p1, p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playername);

        p1 = findViewById(R.id.editTextTextPersonName);
        p2 = findViewById(R.id.editTextTextPersonName2);
    }

    public void PVP(View view){
        String s1 = p1.getText().toString();
        String s2 = p2.getText().toString();
        if (s1.equals("") || s1 == null){
            s1 = "Player 1";
        }
        if (s2.equals("") || s2 == null){
            s2 = "Player 2";
        }

        Intent intent = new Intent(this, GamePlay.class);
        intent.putExtra("PLAYER_NAMES", new String[] {s1, s2} );
        intent.putExtra("PVP", true );
        startActivity(intent);
    }

    public void PVC(View view){
        String s1 = p1.getText().toString();
        String s2 = p2.getText().toString();
        if (s1.equals("") || s1 == null){
            s1 = "Player 1";
        }
        if (s2.equals("") || s2 == null){
            s2 = "Player 2";
        }

        Intent intent = new Intent(this, GamePlay.class);
        intent.putExtra("PLAYER_NAMES", new String[] {s1, s2} );
        intent.putExtra("PVP", false );
        startActivity(intent);
    }
}
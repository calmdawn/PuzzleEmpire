package com.calmdawnstudio.puzzleempire.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.calmdawnstudio.puzzleempire.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imagePuzzleIv = findViewById(R.id.activity_main_image_puzzle_iv);
        ImageView numberPuzzleIv = findViewById(R.id.activity_main_number_puzzle_btn);

        imagePuzzleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ImageSwapPuzzleActivity.class);
                startActivity(intent);
            }
        });

        numberPuzzleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NumberPuzzleActivity.class);
                startActivity(intent);
            }
        });

    }
}

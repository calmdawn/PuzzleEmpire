package com.example.shufflepuzzle.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shufflepuzzle.R;
import com.example.shufflepuzzle.adapter.ImageSwapRecyclerAdapter;
import com.example.shufflepuzzle.adapter.PhotoListRecyclerAdapter;

public class ImageSwapPuzzleActivity extends AppCompatActivity implements View.OnClickListener, PhotoListRecyclerAdapter.PhotoListClickListener {
    ImageView originalIv;

    Button matrix3Btn;
    Button matrix4Btn;
    Button matrix5Btn;

    PhotoListRecyclerAdapter photoListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_swap_puzzle);

        originalIv = findViewById(R.id.activity_image_swap_puzzle_original_iv);

        matrix3Btn = findViewById(R.id.activity_image_swap_puzzle_matrix3_btn);
        matrix4Btn = findViewById(R.id.activity_image_swap_puzzle_matrix4_btn);
        matrix5Btn = findViewById(R.id.activity_image_swap_puzzle_matrix5_btn);

        matrix3Btn.setOnClickListener(this);
        matrix4Btn.setOnClickListener(this);
        matrix5Btn.setOnClickListener(this);

        RecyclerView imageSwapRecyclerView = findViewById(R.id.activity_image_swap_puzzle_recycler);
        ImageSwapRecyclerAdapter imageSwapRecyclerAdapter = new ImageSwapRecyclerAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);   // 각칸의 수를 나눌 수
        imageSwapRecyclerView.setLayoutManager(gridLayoutManager);
        imageSwapRecyclerView.setAdapter(imageSwapRecyclerAdapter);

        RecyclerView photoListRecyclerView = findViewById(R.id.activity_image_swap_photo_list_recycler);
        photoListRecyclerAdapter = new PhotoListRecyclerAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        photoListRecyclerView.setLayoutManager(linearLayoutManager);
        photoListRecyclerView.setAdapter(photoListRecyclerAdapter);

        photoListRecyclerAdapter.addItem(R.drawable.elec_pokemon);
        photoListRecyclerAdapter.addItem(R.drawable.fire_pokemon);
        photoListRecyclerAdapter.addItem(R.drawable.leaf_pokemon);
        photoListRecyclerAdapter.addItem(R.drawable.water_pokemon);
        photoListRecyclerAdapter.notifyDataSetChanged();

    }

    private void setMatrixBtnsEnabled() {
        matrix3Btn.setEnabled(true);
        matrix3Btn.setBackground(getDrawable(R.drawable.activity_image_swap_layout_background_round_black));
        matrix3Btn.setTextColor(getColor(R.color.black_000000));
        matrix4Btn.setEnabled(true);
        matrix4Btn.setBackground(getDrawable(R.drawable.activity_image_swap_layout_background_round_black));
        matrix4Btn.setTextColor(getColor(R.color.black_000000));
        matrix5Btn.setEnabled(true);
        matrix5Btn.setBackground(getDrawable(R.drawable.activity_image_swap_layout_background_round_black));
        matrix5Btn.setTextColor(getColor(R.color.black_000000));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_image_swap_puzzle_matrix3_btn:
                break;
            case R.id.activity_image_swap_puzzle_matrix4_btn:
                break;
            case R.id.activity_image_swap_puzzle_matrix5_btn:
                break;

        }
    }

    @Override
    public void onPhotoListItemClicked(View v, int position) {
        originalIv.setImageResource(photoListRecyclerAdapter.getItem(position));
        setMatrixBtnsEnabled();

    }
}
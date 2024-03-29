package com.calmdawnstudio.puzzleempire.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.calmdawnstudio.puzzleempire.R;
import com.calmdawnstudio.puzzleempire.adapter.CropImageRecyclerAdapter;
import com.calmdawnstudio.puzzleempire.adapter.PhotoListRecyclerAdapter;

public class ImageSwapPuzzleActivity extends AppCompatActivity implements View.OnClickListener, PhotoListRecyclerAdapter.PhotoListClickListener {

    ImageView backIv;
    ImageView originalIv;

    TextView gameStartTv;
    TextView countDownTv;
    TextView scoreResultTv;
    TextView returnSelectGameTv;

    Button matrix3Btn;
    Button matrix4Btn;
    Button matrix5Btn;

    RecyclerView cropImageSwapRecyclerView;
    CropImageRecyclerAdapter cropImageRecyclerAdapter;
    GridLayoutManager cropImgGridLayoutManager;

    RecyclerView photoListRecyclerView;
    PhotoListRecyclerAdapter photoListRecyclerAdapter;
    ConstraintLayout scoreConstLayout;


    CountDownTimer countDownTimer;

    int matrixCount = 0;    // 3 * 3, 5 * 5,  5 * 5 어떤 매트릭스인지 설정
    private int timer = 120;

    MediaPlayer mediaPlayer;
    ImageView volumeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_swap_puzzle);

        initUI();
        setupUI();
        setAdapter();

        photoListRecyclerAdapter.addItem(R.drawable.image_swap_puzzle_img_spring);
        photoListRecyclerAdapter.addItem(R.drawable.image_swap_puzzle_img_summer);
        photoListRecyclerAdapter.addItem(R.drawable.image_swap_puzzle_img_autumn);
        photoListRecyclerAdapter.addItem(R.drawable.image_swap_puzzle_img_winter);
        photoListRecyclerAdapter.addItem(R.drawable.image_swap_puzzle_img_fantasy);
        photoListRecyclerAdapter.notifyDataSetChanged();

        mediaPlayer = MediaPlayer.create(this, R.raw.main_theme);
        mediaPlayer.start();


        countDownTimer = new CountDownTimer(timer * 1000, 1000) {  //제한시간 120초, 1초씩 감소
            @Override
            public void onTick(long l) {
                countDownTv.setText("제한시간 : " + l / 1000 + "초");
                timer--;
            }

            @Override
            public void onFinish() {
                countDownTv.setText("제한시간 : " + 0 + "초");
            }
        };


        volumeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    volumeIv.setImageResource(R.drawable.common_ic_volume_up_24dp);
                    mediaPlayer = MediaPlayer.create(ImageSwapPuzzleActivity.this, R.raw.main_theme);
                    mediaPlayer.start();
                } else {
                    volumeIv.setImageResource(R.drawable.common_ic_volume_off_24dp);
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
            }
        });

    }

    private void setAdapter() {
        cropImageRecyclerAdapter = new CropImageRecyclerAdapter(this);
        cropImgGridLayoutManager = new GridLayoutManager(this, 1);   // 각칸의 수를 나눌 수
        cropImageSwapRecyclerView.setLayoutManager(cropImgGridLayoutManager);
        cropImageSwapRecyclerView.setAdapter(cropImageRecyclerAdapter);

        photoListRecyclerAdapter = new PhotoListRecyclerAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        photoListRecyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        photoListRecyclerView.setLayoutManager(linearLayoutManager);
        photoListRecyclerView.setAdapter(photoListRecyclerAdapter);

    }

    private void setupUI() {
        gameStartTv.setOnClickListener(this);
        matrix3Btn.setOnClickListener(this);
        matrix4Btn.setOnClickListener(this);
        matrix5Btn.setOnClickListener(this);
        returnSelectGameTv.setOnClickListener(this);
        backIv.setOnClickListener(this);

    }

    private void initUI() {
        backIv = findViewById(R.id.activity_image_swap_puzzle_back_iv);
        originalIv = findViewById(R.id.activity_image_swap_puzzle_original_iv);
        volumeIv = findViewById(R.id.activity_image_swap_puzzle_volume_iv);

        gameStartTv = findViewById(R.id.activity_image_swap_puzzle_game_start_tv);
        countDownTv = findViewById(R.id.activity_image_swap_puzzle_countdown_tv);
        scoreResultTv = findViewById(R.id.common_layout_puzzle_score_result_tv);
        returnSelectGameTv = findViewById(R.id.common_layout_puzzle_score_return_select_game_tv);


        matrix3Btn = findViewById(R.id.activity_image_swap_puzzle_matrix3_btn);
        matrix4Btn = findViewById(R.id.activity_image_swap_puzzle_matrix4_btn);
        matrix5Btn = findViewById(R.id.activity_image_swap_puzzle_matrix5_btn);
        scoreConstLayout = findViewById(R.id.activity_image_swap_puzzle_layout_game_set_score);
        cropImageSwapRecyclerView = findViewById(R.id.activity_image_swap_puzzle_recycler);
        photoListRecyclerView = findViewById(R.id.activity_image_swap_photo_list_recycler);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_image_swap_puzzle_matrix3_btn:
                if (cropImageRecyclerAdapter.getItemCount() > 0) {
                    cropImageRecyclerAdapter.clearItems();
                }
                matrixCount = 3;
                setPuzzleImage(matrixCount);
                gameStartTv.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_image_swap_puzzle_matrix4_btn:
                if (cropImageRecyclerAdapter.getItemCount() > 0) {
                    cropImageRecyclerAdapter.clearItems();
                }
                matrixCount = 4;
                setPuzzleImage(matrixCount);
                gameStartTv.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_image_swap_puzzle_matrix5_btn:
                if (cropImageRecyclerAdapter.getItemCount() > 0) {
                    cropImageRecyclerAdapter.clearItems();
                }
                matrixCount = 5;
                setPuzzleImage(matrixCount);
                gameStartTv.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_image_swap_puzzle_game_start_tv:
                cropImageRecyclerAdapter.shuffle(matrixCount);
                gameStartTv.setVisibility(View.GONE);
                setMatrixBtnsEnabled(false);
                setMatrixBtnsVisibility(View.GONE);
                photoListRecyclerView.setVisibility(View.GONE);
                countDownTimer.start();
                break;
            case R.id.common_layout_puzzle_score_return_select_game_tv:
            case R.id.activity_image_swap_puzzle_back_iv:
                finish();
                break;
        }
    }

    public void gameSet() {
        countDownTimer.cancel();
        setMatrixBtnsVisibility(View.VISIBLE);
        photoListRecyclerView.setVisibility(View.VISIBLE);
        scoreConstLayout.setVisibility(View.VISIBLE);
        scoreResultTv.setText("점수 : " + timer);
        setMatrixBtnsEnabled(false);
        cropImageRecyclerAdapter.setCropIvItemListEnable(false);
        timer = 120;
        Toast.makeText(this, "와우~! 정답입니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null){
            volumeIv.setImageResource(R.drawable.common_ic_volume_off_24dp);
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }

    private void setMatrixBtnsEnabled(boolean isEnable) {

        if (isEnable) {
            matrix3Btn.setEnabled(true);
            matrix3Btn.setBackground(getDrawable(R.drawable.image_swap_layout_background_round_black));
            matrix3Btn.setTextColor(getColor(R.color.black_000000));
            matrix4Btn.setEnabled(true);
            matrix4Btn.setBackground(getDrawable(R.drawable.image_swap_layout_background_round_black));
            matrix4Btn.setTextColor(getColor(R.color.black_000000));
            matrix5Btn.setEnabled(true);
            matrix5Btn.setBackground(getDrawable(R.drawable.image_swap_layout_background_round_black));
            matrix5Btn.setTextColor(getColor(R.color.black_000000));

        } else {
            matrix3Btn.setEnabled(false);
            matrix3Btn.setBackground(getDrawable(R.drawable.image_swap_layout_background_round_gray));
            matrix3Btn.setTextColor(getColor(R.color.gray_B9B9B9));
            matrix4Btn.setEnabled(false);
            matrix4Btn.setBackground(getDrawable(R.drawable.image_swap_layout_background_round_gray));
            matrix4Btn.setTextColor(getColor(R.color.gray_B9B9B9));
            matrix5Btn.setEnabled(false);
            matrix5Btn.setBackground(getDrawable(R.drawable.image_swap_layout_background_round_gray));
            matrix5Btn.setTextColor(getColor(R.color.gray_B9B9B9));
        }

    }

    private void setMatrixBtnsVisibility(int visibility) {
        matrix3Btn.setVisibility(visibility);
        matrix4Btn.setVisibility(visibility);
        matrix5Btn.setVisibility(visibility);
    }

    //이미지 사이즈 재설정
    private Bitmap resizeBitmap(Bitmap originalBitmap) {

        int resizeWidth = cropImageSwapRecyclerView.getWidth();
        int resizeHeight = cropImageSwapRecyclerView.getHeight();
        Log.d("크롭 비트맵", "리사이즈 너비 = " + resizeWidth + " , " + "리사이즈 높이 = " + resizeHeight);
        return Bitmap.createScaledBitmap(originalBitmap, resizeWidth, resizeHeight, true);
    }

    //클릭한 Matrix 칸만큼 이미지를 자르고 리사이클러뷰에 추가한다
    private void setPuzzleImage(int matrixCount) {
        Bitmap resizeBitmap = resizeBitmap(((BitmapDrawable) originalIv.getDrawable()).getBitmap());

        int resizeBitmapSize = resizeBitmap.getWidth();
        int cropBitmapSize = resizeBitmapSize / matrixCount - dpToPx(4);    // 리서이클러뷰의 패딩값만큼 빼줌
        Log.d("크롭 비트맵", "조각 높이 = " + cropBitmapSize);
        cropImgGridLayoutManager.setSpanCount(matrixCount);

        //비트맵 잘라서 생성하기 (기존 비트맵, 자를 x위치, 자를 y위치, 잘라낸 너비, 잘라넨 높이)
        for (int x = 0; x < matrixCount; x++) {
            for (int y = 0; y < matrixCount; y++) {
                Bitmap cropBitmapResult = Bitmap.createBitmap(resizeBitmap, y * cropBitmapSize, x * cropBitmapSize, cropBitmapSize, cropBitmapSize);
                cropImageRecyclerAdapter.addItem(cropBitmapResult);
                Log.d("크롭 비트맵", "x위치 = " + y * cropBitmapSize + " , " + "y위치 = " + y * cropBitmapSize);

            }
        }
        cropImageRecyclerAdapter.notifyDataSetChanged();
    }


    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


    @Override
    public void onPhotoListItemClicked(View v, int position) {
        if (originalIv.getDrawable() != null) {   //기존에 맞출 그림이 세팅되어있던경우
            cropImageRecyclerAdapter.clearItems();
            gameStartTv.setVisibility(View.GONE);
        }
        originalIv.setImageResource(photoListRecyclerAdapter.getItem(position));
        timer = 120;
        countDownTv.setText("제한시간 : " + timer + "초");
        scoreConstLayout.setVisibility(View.GONE);
        setMatrixBtnsEnabled(true);
    }


}

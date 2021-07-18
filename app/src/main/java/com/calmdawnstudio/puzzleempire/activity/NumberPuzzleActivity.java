package com.calmdawnstudio.puzzleempire.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.calmdawnstudio.puzzleempire.R;

import java.util.ArrayList;

public class NumberPuzzleActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<Button> btnArrayList;
    ArrayList<Button> remainArrayList;  //정답까지 남은 버튼들

    int currentNum;
    int timer = 60;

    TextView gameStartTv;
    TextView countDownTv;
    TextView scoreResultTv;
    ConstraintLayout scoreConstLayout;
    CountDownTimer countDownTimer;

    MediaPlayer mainMediaPlayer;
    MediaPlayer wrongMediaPlayer;

    ImageView volumeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_number_puzzle);

        btnArrayList = new ArrayList<>();
        remainArrayList = new ArrayList<>();
        NumberBtnOnClickListener numberBtnOnClickListener = new NumberBtnOnClickListener();
        mainMediaPlayer = MediaPlayer.create(this, R.raw.main_theme);

        GridLayout gridLayout = findViewById(R.id.activity_number_puzzle_gridlayout);
        scoreConstLayout = findViewById(R.id.activity_number_puzzle_layout_game_set_score);

        countDownTv = findViewById(R.id.activity_number_puzzle_countdown_tv);
        gameStartTv = findViewById(R.id.activity_number_puzzle_gamestart_tv);
        TextView returnSelectGameTv = findViewById(R.id.common_layout_puzzle_score_return_select_game_tv);
        scoreResultTv = findViewById(R.id.common_layout_puzzle_score_result_tv);

        ImageView backIv = findViewById(R.id.activity_number_puzzle_back_iv);
        volumeIv = findViewById(R.id.activity_number_puzzle_volume_iv);

        gameStartTv.setOnClickListener(this);
        returnSelectGameTv.setOnClickListener(this);
        backIv.setOnClickListener(this);

        for (int i = 1; i <= 25; i++) { //초기 1 ~ 25 버튼 세팅
            Button childBtn = createButton(String.valueOf(i));
            childBtn.setOnClickListener(numberBtnOnClickListener);
            gridLayout.addView(childBtn);
            btnArrayList.add(childBtn);
        }

        mainMediaPlayer.start();
        mainMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
        countDownTimer = new CountDownTimer(timer * 1000, 1000) {   // 1초씩 줄어듬
            @Override
            public void onTick(long l) {    //0초시 자동으로 onFinish() 호출함
                countDownTv.setText("제한시간 : " + l / 1000 + "초");
                timer--;
            }

            @Override
            public void onFinish() {

            }
        };


        volumeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainMediaPlayer == null) {
                    volumeIv.setImageResource(R.drawable.common_ic_volume_up_24dp);
                    mainMediaPlayer = MediaPlayer.create(NumberPuzzleActivity.this, R.raw.main_theme);
                    mainMediaPlayer.start();
                } else {
                    volumeIv.setImageResource(R.drawable.common_ic_volume_off_24dp);
                    mainMediaPlayer.stop();
                    mainMediaPlayer = null;
                }
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mainMediaPlayer != null) {
            volumeIv.setImageResource(R.drawable.common_ic_volume_off_24dp);
            mainMediaPlayer.stop();
            mainMediaPlayer = null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_number_puzzle_gamestart_tv:
                scoreConstLayout.setVisibility(View.GONE);
                setBtnArrayInit(btnArrayList);
                currentNum = 1;
                shuffle(btnArrayList);
                setBtnArrayClickable(btnArrayList);
                v.setEnabled(false);
                countDownTimer.start();
                v.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_number_puzzle_back_iv:
            case R.id.common_layout_puzzle_score_return_select_game_tv:
                finish();
                break;
        }
    }

    private void setBtnArrayInit(ArrayList<Button> btnArrayList) {
        for (Button btn : btnArrayList) {
            btn.setBackground(getDrawable(R.drawable.number_puzzle_layout_background_btn_init));
            btn.setTextColor(getColor(R.color.light_black_272727));
        }

        remainArrayList.clear();
        remainArrayList.addAll(btnArrayList);

    }


    class NumberBtnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (((Button) v).getText().equals(String.valueOf(currentNum))) {    //버튼 숫자가 현재 숫자와 같은 경우
                ((Button) v).setTextColor(getColor(R.color.right_puzzle_blue_43B8EE));
                v.setBackground(getDrawable(R.drawable.number_puzzle_layout_background_btn_right));
                v.setEnabled(false);
                remainArrayList.remove(((Button) v));

                if (currentNum == 25) {   // 모두 다 맞춘경우
                    gameSet();
                } else {
                    currentNum++;
                }

            } else {   //버튼 숫자와 현재 숫자가 틀린 경우
                v.setBackground(getDrawable(R.drawable.number_puzzle_layout_background_btn_wrong));
                pausePuzzle(v);
            }
        }
    }

    private void gameSet() {
        countDownTimer.cancel();
        scoreConstLayout.setVisibility(View.VISIBLE);
        scoreResultTv.setText("점수 : " + timer);
        gameStartTv.setVisibility(View.VISIBLE);
        gameStartTv.setText("재시작");
        gameStartTv.setEnabled(true);
        setBtnArrayUnClickable(btnArrayList);
        timer = 60;
        Toast.makeText(this, "게임이 종료되었습니다~!", Toast.LENGTH_SHORT).show();
    }

    private void pausePuzzle(final View v) {  // 틀린 경우 빨간색 표시후 2초간 클릭불가 이후 정상작동
        wrongMediaPlayer = MediaPlayer.create(this, R.raw.sound_button_wrong);
        wrongMediaPlayer.start();
        setBtnArrayUnClickable(remainArrayList);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wrongMediaPlayer.stop();
                wrongMediaPlayer = null;
                v.setBackground(getDrawable(R.drawable.number_puzzle_layout_background_btn_init));
                setBtnArrayClickable(remainArrayList);

            }
        }, 2000);

    }

    private Button createButton(String text) {  //1 ~ 25까지 각각 버튼 생성
        Button button = new Button(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(    //행렬의 weight를 1로부여함
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f));
        params.height = 0;
        params.width = 0;
        params.setMargins(8, 8, 8, 8);
        button.setLayoutParams(params);
        button.setText(text);   //텍스트 입력
        button.setTextColor(getColor(R.color.light_black_272727));
        button.setBackground(getDrawable(R.drawable.number_puzzle_layout_background_btn_init));    //기본 버튼배경
        button.setEnabled(false);
        return button;
    }


    public void shuffle(ArrayList<Button> btnArrayList) { //1~25까지의 수를 랜덤으로 버튼에 세팅
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25"};

        for (int i = 0; i < numbers.length; i++) {
            int a = (int) (Math.random() * numbers.length);
            int b = (int) (Math.random() * numbers.length);

            String temp = numbers[a];
            numbers[a] = numbers[b];
            numbers[b] = temp;
        }

        for (int i = 0; i < btnArrayList.size(); i++) {
            btnArrayList.get(i).setText(numbers[i]);
        }

    }


    //버튼 전체의 활성/비활성을 컨트롤하기 위한 클래스
    private void setBtnArrayClickable(ArrayList<Button> btnArrayList) {
        for (Button btn : btnArrayList) {
            btn.setEnabled(true);
        }
    }

    private void setBtnArrayUnClickable(ArrayList<Button> btnArrayList) {
        for (Button btn : btnArrayList) {
            btn.setEnabled(false);
        }
    }


}
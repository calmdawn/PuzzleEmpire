package com.example.shufflepuzzle.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shufflepuzzle.R;

public class NumberPuzzleActivity extends AppCompatActivity {

    String sNum = "1";
    int timer = 60;
    int numberGameResult;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_number_puzzle);

        final RelativeLayout scoreLayout = findViewById(R.id.activity_number_ly_score);

        final TextView numberScoreResult = findViewById(R.id.activity_number_tv_result);

        Button btn1 = findViewById(R.id.activity_number_btn_num1); Button btn14 = findViewById(R.id.activity_number_btn_num14);
        Button btn2 = findViewById(R.id.activity_number_btn_num2); Button btn15 = findViewById(R.id.activity_number_btn_num15);
        Button btn3 = findViewById(R.id.activity_number_btn_num3); Button btn16 = findViewById(R.id.activity_number_btn_num16);
        Button btn4 = findViewById(R.id.activity_number_btn_num4); Button btn17 = findViewById(R.id.activity_number_btn_num17);
        Button btn5 = findViewById(R.id.activity_number_btn_num5); Button btn18 = findViewById(R.id.activity_number_btn_num18);
        Button btn6 = findViewById(R.id.activity_number_btn_num6); Button btn19 = findViewById(R.id.activity_number_btn_num19);
        Button btn7 = findViewById(R.id.activity_number_btn_num7); Button btn20 = findViewById(R.id.activity_number_btn_num20);
        Button btn8 = findViewById(R.id.activity_number_btn_num8); Button btn21= findViewById(R.id.activity_number_btn_num21);
        Button btn9 = findViewById(R.id.activity_number_btn_num9); Button btn22= findViewById(R.id.activity_number_btn_num22);
        Button btn10 = findViewById(R.id.activity_number_btn_num10); Button btn23 = findViewById(R.id.activity_number_btn_num23);
        Button btn11 = findViewById(R.id.activity_number_btn_num11); Button btn24 = findViewById(R.id.activity_number_btn_num24);
        Button btn12 = findViewById(R.id.activity_number_btn_num12); Button btn25= findViewById(R.id.activity_number_btn_num25);
        Button btn13 = findViewById(R.id.activity_number_btn_num13);

        final Button numberStart = findViewById(R.id.activity_number_btn_start);

        Button btnReturnMenu = findViewById(R.id.activity_number_btn_returnMenu);

        btnReturnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final String[] ascNum = {  "1","2","3","4","5", "6","7","8","9","10",
                "11","12","13","14","15", "16","17","18","19","20",
                "21","22","23","24","25"};

        final Button[] buttonArray = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,
                btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
                btn21, btn22, btn23, btn24, btn25};

        for(int i=0; i<25; i++){
            buttonArray[i].setText(ascNum[i]);
            buttonArray[i].setBackground(getResources().getDrawable(R.drawable.custom_button_init_background));
        }



        final ButtonArrayClickableControl btnArrayClickableControl = new ButtonArrayClickableControl(buttonArray);
        btnArrayClickableControl.setBtnArrayUnClickable();

        final CountDownTimer puzzleTimer = new CountDownTimer(60* 1000, 1000) {
            @Override
            public void onTick(long l) {
                timer--;
                TextView timeTextView = findViewById(R.id.activity_image_swap_puzzle_countdown_tv);
                timeTextView.setText("제한시간 : " + timer+ "초" );

                if(timer ==0){
                    numberGameResult = timer;
                    scoreLayout.setVisibility(View.VISIBLE);
                    numberScoreResult.setText(numberGameResult+"점");
                }

            }

            @Override
            public void onFinish() {
                TextView timeTextView = findViewById(R.id.activity_image_swap_puzzle_countdown_tv);
                timeTextView.setText("제한시간 : " + timer+ "초" );
            }
        };


        numberStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnArrayClickableControl.setBtnArrayAllClickable();
                String[] randomNum = shuffle(ascNum);

                for(int i=0; i<25; i++){
                    buttonArray[i].setText(randomNum[i]);
                }

                puzzleTimer.cancel();
                timer = 60;
                puzzleTimer.start();
                numberStart.setEnabled(false);
            }
        });

        View.OnClickListener btnOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                for(int i =0; i<buttonArray.length; i++){
                    if(id == buttonArray[i].getId()){
                        if(sNum.equals(buttonArray[i].getText())) {
                            buttonArray[i].setBackground(getResources().getDrawable(R.drawable.custom_button_right_background));
                            buttonArray[i].setEnabled(false);
                            if(sNum.equals("25")){
                                Toast.makeText(getApplicationContext(),"정답입니다", Toast.LENGTH_LONG).show();
                                puzzleTimer.cancel();
                                puzzleTimer.onFinish();

                                //게임결과값이 나와야함
                                numberGameResult = timer;
                                scoreLayout.setVisibility(View.VISIBLE);
                                numberScoreResult.setText(numberGameResult+"점");


                            }
                            sNum = Integer.toString(Integer.parseInt(sNum) + 1);

                        }else {
                            buttonArray[i].setBackground(getResources().getDrawable(R.drawable.custom_button_wrong_background));
                            mediaPlayer = MediaPlayer.create(NumberPuzzleActivity.this, R.raw.sound_button_wrong);
                            mediaPlayer.start();
                            Runnable pauseRunnable = new PauseRunnable(buttonArray, i);
                            Thread pauseThread = new Thread(pauseRunnable);
                            pauseThread.start();
                        }
                    }
                }

            }
        };

        //향상된 for문을 통해 btnOnClickListener에 연결
        for(Button btn: buttonArray){
            btn.setOnClickListener(btnOnClickListener);
        }


    }


    public String[] shuffle(String[] strArray){

        for(int i=0; i<strArray.length; i++){
            int a = (int)(Math.random()*strArray.length);
            int b = (int)(Math.random()*strArray.length);

            String temp = strArray[a];
            strArray[a] = strArray[b];
            strArray[b] = temp;
        }

        return strArray;

    }


    //버튼 전체의 활성/비활성을 컨트롤하기 위한 클래스
    class ButtonArrayClickableControl{
        Button[] btnArray;


        public ButtonArrayClickableControl(Button[] btnArray){
            this.btnArray = btnArray;
        }

        public void setBtnArrayAllClickable(){
            for(Button btn: btnArray){
                btn.setEnabled(true);
            }
        }

        public void setBtnArrayUnClickable(){
            for(Button btn: btnArray){
                btn.setEnabled(false);
            }
        }

    }



    // 틀렸을시 빨간색 표시후 3초간 클릭불가 이후 정상작동
    class PauseRunnable implements Runnable {
        Button[] btnArray;
        int i;


        public PauseRunnable(Button[] btnArray, int i){
            this.btnArray = btnArray;
            this.i = i;

        }

        @Override
        public void run() {
            try {
                for(Button btn: btnArray){
                    btn.setClickable(false);
                }
                Thread.sleep(3 * 1000);
                btnArray[i].setBackground(getResources().getDrawable(R.drawable.custom_button_init_background));
                for(Button btn: btnArray){
                    btn.setClickable(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }

    }


}
package com.example.shufflepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ImagePuzzleActivity extends AppCompatActivity {


    //이미지 Swap을 위한 변수들

    int countClickedImgView = 0;
    ImageView[] saveSwapImgView = new ImageView[2];

    // 원본이미지와 같은지확인하기 위한 변수
    int equalImgCount = 0;
    // Timer 점수
    int timer = 120;

    int imageGameResult1;
    int imageGameResult2;
    int imageGameResult3;

    // 어떤게임이 실행중인지
    int gameNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_puzzle);

        final RelativeLayout scoreLayout = findViewById(R.id.activity_image_ly_score);

        //그리드 레이아웃
        final GridLayout imageSwapLayout = findViewById(R.id.activity_image_ly_swap);

        //이미지뷰들
        ImageView oriImageView = findViewById(R.id.ori_image_view);

        //텍스트뷰들

        final TextView matrix3ScoreResult = findViewById(R.id.matrix3_score_result_text_view);
        final TextView matrix4ScoreResult = findViewById(R.id.matrix4_score_result_text_view);
        final TextView matrix5ScoreResult = findViewById(R.id.matrix5_score_result_text_view);

        final ImageView imageView1 = findViewById(R.id.activity_image_iv_num1);
        ImageView imageView14 = findViewById(R.id.activity_image_iv_num14);
        ImageView imageView2 = findViewById(R.id.activity_image_iv_num2);
        ImageView imageView15 = findViewById(R.id.activity_image_iv_num15);
        ImageView imageView3 = findViewById(R.id.activity_image_iv_num3);
        ImageView imageView16 = findViewById(R.id.activity_image_iv_num16);
        ImageView imageView4 = findViewById(R.id.activity_image_iv_num4);
        ImageView imageView17 = findViewById(R.id.activity_image_iv_num17);
        ImageView imageView5 = findViewById(R.id.activity_image_iv_num5);
        ImageView imageView18 = findViewById(R.id.activity_image_iv_num18);
        ImageView imageView6 = findViewById(R.id.activity_image_iv_num6);
        ImageView imageView19 = findViewById(R.id.activity_image_iv_num19);
        ImageView imageView7 = findViewById(R.id.activity_image_iv_num7);
        ImageView imageView20 = findViewById(R.id.activity_image_iv_num20);
        ImageView imageView8 = findViewById(R.id.activity_image_iv_num8);
        ImageView imageView21 = findViewById(R.id.activity_image_iv_num21);
        ImageView imageView9 = findViewById(R.id.activity_image_iv_num9);
        ImageView imageView22 = findViewById(R.id.activity_image_iv_num22);
        ImageView imageView10 = findViewById(R.id.activity_image_iv_num10);
        ImageView imageView23 = findViewById(R.id.activity_image_iv_num23);
        ImageView imageView11 = findViewById(R.id.activity_image_iv_num11);
        ImageView imageView24 = findViewById(R.id.activity_image_iv_num24);
        ImageView imageView12 = findViewById(R.id.activity_image_iv_num12);
        ImageView imageView25 = findViewById(R.id.activity_image_iv_num25);
        ImageView imageView13 = findViewById(R.id.activity_image_iv_num13);

        final ImageView[][] imageViewArray = {{imageView1, imageView2, imageView3, imageView4, imageView5},
                {imageView6, imageView7, imageView8, imageView9, imageView10},
                {imageView11, imageView12, imageView13, imageView14, imageView15},
                {imageView16, imageView17, imageView18, imageView19, imageView20},
                {imageView21, imageView22, imageView23, imageView24, imageView25}};

        final int[][] originalImgViewArrayId = {{R.id.activity_image_iv_num1, R.id.activity_image_iv_num2, R.id.activity_image_iv_num3, R.id.activity_image_iv_num4, R.id.activity_image_iv_num5},
                {R.id.activity_image_iv_num6, R.id.activity_image_iv_num7, R.id.activity_image_iv_num8, R.id.activity_image_iv_num9, R.id.activity_image_iv_num10},
                {R.id.activity_image_iv_num11, R.id.activity_image_iv_num12, R.id.activity_image_iv_num13, R.id.activity_image_iv_num14, R.id.activity_image_iv_num15},
                {R.id.activity_image_iv_num16, R.id.activity_image_iv_num17, R.id.activity_image_iv_num18, R.id.activity_image_iv_num19, R.id.activity_image_iv_num20},
                {R.id.activity_image_iv_num21, R.id.activity_image_iv_num22, R.id.activity_image_iv_num23, R.id.activity_image_iv_num24, R.id.activity_image_iv_num25}};

        //버튼들

        final Button btnMatrix3 = findViewById(R.id.matrix3_button);
        final Button btnMatrix4 = findViewById(R.id.matrix4_button);
        final Button btnMatrix5 = findViewById(R.id.matrix5_button);

        Button btnReturnMenu = findViewById(R.id.activity_image_btn_returnMenu);

        //이미지 리소스들
        int[] idImages = {R.drawable.elec_pokemon, R.drawable.fire_pokemon, R.drawable.leaf_pokemon, R.drawable.water_pokemon};

        //원본이미지 세팅
        final Bitmap bitmap = resizeBitmap(getOverlayBitmap(this, idImages));

        oriImageView.setImageBitmap(bitmap);
        oriImageView.setScaleType(ImageView.ScaleType.FIT_XY);


        final ImageViewArrayClickableControl imageViewArrayClickableControl = new ImageViewArrayClickableControl(imageViewArray);
        imageViewArrayClickableControl.setIvArrayUnClickable();


        final CountDownTimer countUpTimer = new CountDownTimer(120 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                timer--;
                TextView timer_TextView = findViewById(R.id.swap_timer_text_view);
                timer_TextView.setText("제한시간 : " + timer + "초 ");
            }

            @Override
            public void onFinish() {

            }
        };

        // Matrix버튼 리스너

        View.OnClickListener matrixListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int imgSize = bitmap.getHeight();
                int row = 0, col = 0;
                int i = 0, j = 0;
                int id = v.getId();


                //버튼에 맞는 행렬값 넣기
                switch (id) {
                    case R.id.matrix3_button:
                        imageViewArrayClickableControl.setIvArrayAllClickable();
                        gameNum = 3;
                        clearImageView(imageViewArray);
                        row = 3;
                        col = 3;
                        goneImageView(imageViewArray, row, col);
                        countUpTimer.cancel();
                        countUpTimer.onFinish();
                        timer = 120;
                        countUpTimer.start();
                        btnMatrix3.setEnabled(false);
                        break;
                    case R.id.matrix4_button:
                        imageViewArrayClickableControl.setIvArrayAllClickable();
                        gameNum = 4;
                        clearImageView(imageViewArray);
                        row = 4;
                        col = 4;
                        goneImageView(imageViewArray, row, col);
                        countUpTimer.cancel();
                        countUpTimer.onFinish();
                        timer = 120;
                        countUpTimer.start();
                        btnMatrix4.setEnabled(false);
                        break;
                    case R.id.matrix5_button:
                        imageViewArrayClickableControl.setIvArrayAllClickable();
                        gameNum = 5;
                        clearImageView(imageViewArray);
                        row = 5;
                        col = 5;
                        countUpTimer.cancel();
                        countUpTimer.onFinish();
                        timer = 120;
                        countUpTimer.start();
                        btnMatrix5.setEnabled(false);
                        break;
                    default:
                        break;
                }

                // 해당하는 행렬만큼 잘라서 Gridlayout에 붙여넣는다
                for (int y = 0; y < imgSize; y = y + (imgSize / row)) {
                    for (int x = 0; x < imgSize; x = x + (imgSize / col)) {
                        imageViewArray[i][j].setImageBitmap(cropBitmap(bitmap, x, y, row, col));
                        imageViewArray[i][j].setScaleType(ImageView.ScaleType.FIT_XY);
                        if (j < row) {
                            j++;
                        }
                    }
                    j = 0;
                    i++;
                }

                //잘라서 붙인 이미지들을 랜덤으로 섞어준다
                shuffle(imageViewArray, row, col);


            }
        };

        btnMatrix3.setOnClickListener(matrixListener);
        btnMatrix4.setOnClickListener(matrixListener);
        btnMatrix5.setOnClickListener(matrixListener);


        //swapImageView리스너

        View.OnClickListener swapListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();


                for (int i = 0; i < imageViewArray[0].length; i++) {
                    for (int j = 0; j < imageViewArray.length; j++) {

                        if (id == imageViewArray[i][j].getId()) {
                            imageViewArray[i][j].setColorFilter(R.color.colorFillerImageView, PorterDuff.Mode.DARKEN);
                            saveSwapImgView[countClickedImgView] = imageViewArray[i][j];
                            countClickedImgView++;
                        }

                    }
                }
                String che = Integer.toString(equalImgCount);

                if (countClickedImgView == 2) {
                    swapImageView(saveSwapImgView[0], saveSwapImgView[1]);
                    saveSwapImgView[0].setColorFilter(null);
                    saveSwapImgView[1].setColorFilter(null);
                    countClickedImgView = 0;

                    //처음과 같은지 확인
                    equalImgCount = 0;
                    for (int i = 0; i < imageViewArray[0].length; i++) {
                        for (int j = 0; j < imageViewArray.length; j++) {
                            if (originalImgViewArrayId[i][j] == imageViewArray[i][j].getId()) {
                                equalImgCount++;
                            }
                        }
                    }


                }


                if (equalImgCount == (imageViewArray[0].length * imageViewArray.length)) {
                    Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_SHORT).show();
                    equalImgCount++;
                    countUpTimer.cancel();
                    countUpTimer.onFinish();

                    if (gameNum == 3) {
                        imageGameResult1 = timer;
                        imageViewArrayClickableControl.setIvArrayUnClickable();
                        btnMatrix4.setEnabled(true);
                    } else if (gameNum == 4) {
                        imageGameResult2 = timer;
                        imageViewArrayClickableControl.setIvArrayUnClickable();
                        btnMatrix5.setEnabled(true);
                    } else if (gameNum == 5) {
                        imageGameResult3 = timer;
                        imageViewArrayClickableControl.setIvArrayUnClickable();
                        scoreLayout.setVisibility(View.VISIBLE);
                        matrix3ScoreResult.setText("3 X 3 퍼즐 : " + imageGameResult1);
                        matrix4ScoreResult.setText("4 X 4 퍼즐 : " + imageGameResult2);
                        matrix5ScoreResult.setText("5 X 5 퍼즐 : " + imageGameResult3);
                    }


                }


            }
        };

        //imageView들 리스너에 추가
        for (int i = 0; i < imageViewArray[0].length; i++) {
            for (int j = 0; j < imageViewArray.length; j++) {
                imageViewArray[i][j].setOnClickListener(swapListener);
            }
        }


        btnReturnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    //이미지를 랜덤으로 가져와서 Bitmap으로 변환
    public static Bitmap getOverlayBitmap(Context context, int[] idImages) {
        int idImg = randImg(idImages);
        return BitmapFactory.decodeResource(context.getResources(), idImg);
    }

    //이미지 사이즈 재설정
    public static Bitmap resizeBitmap(Bitmap originalBitmap) {

        int resizeWidth = 300;
        int resizeHeight = 300;

        return Bitmap.createScaledBitmap(originalBitmap, resizeWidth, resizeHeight, true);
    }

    //비트맵 잘라서 생성하기 (기존 비트맵, 자를 x위치, 자를 y위치, 잘라낸 너비, 잘라넨 높이)
    public static Bitmap cropBitmap(Bitmap originalBitmap, int x, int y, int row, int col) {
        Bitmap cropResult = Bitmap.createBitmap(originalBitmap, x, y, originalBitmap.getWidth() / row, originalBitmap.getHeight() / col);
        return cropResult;

    }


    //이미지 리소스들을 전달받아 랜덤bitmap 사진선택
    public static int randImg(int[] idImages) {
        Random random = new Random();
        int x = random.nextInt(idImages.length);
        int id = idImages[x];

        return id;
    }


    //이미지뷰 초기화 후 Visible
    public static void clearImageView(ImageView[][] v) {
        for (int i = 0; i < v[0].length; i++) {
            for (int j = 0; j < v.length; j++) {
                v[i][j].setImageBitmap(null);
                v[i][j].setColorFilter(null);
                v[i][j].setVisibility(View.VISIBLE);

            }
        }
    }


    //이미지뷰 Gone으로 제거 레이아웃 각셀에 weigt를 주어 자동으로 크기정렬시킴
    public static void goneImageView(ImageView[][] v, int row, int col) {

        while (row < v.length) {
            for (int i = 0; i < v[0].length; i++) {
                v[i][row].setVisibility(View.GONE);

            }
            row++;
        }

        while (col < v[0].length) {
            for (int j = 0; j < v.length; j++) {
                v[col][j].setVisibility(View.GONE);

            }
            col++;
        }

    }


    //이미지와 id를 바꿈
    public static void swapImageView(ImageView first, ImageView second) {

        int temp;

        BitmapDrawable firstBitmapDrawable = (BitmapDrawable) first.getDrawable();
        BitmapDrawable secondBitmapDrawable = (BitmapDrawable) second.getDrawable();
        Bitmap firstBitmap = firstBitmapDrawable.getBitmap();
        Bitmap secondBitmap = secondBitmapDrawable.getBitmap();
        first.setImageBitmap(secondBitmap);
        second.setImageBitmap(firstBitmap);

        temp = first.getId();
        first.setId(second.getId());
        second.setId(temp);

    }


    public static void shuffle(ImageView[][] imageViewArray, int row, int col) {

        for (int j = 0; j < imageViewArray[0].length; j++) {
            for (int i = 0; i < imageViewArray.length; i++) {

                int a = (int) (Math.random() * row);
                int b = (int) (Math.random() * col);
                int c = (int) (Math.random() * row);
                int d = (int) (Math.random() * col);
                swapImageView(imageViewArray[a][b], imageViewArray[c][d]);

            }
        }
    }

    //이미지뷰 전체의 활성/비활성을 컨트롤하기 위한 클래스
    class ImageViewArrayClickableControl {
        ImageView[][] ivArray;

        public ImageViewArrayClickableControl(ImageView[][] ivArray) {
            this.ivArray = ivArray;
        }

        public void setIvArrayAllClickable() {
            for (ImageView[] iv : ivArray) {
                for (ImageView iv2 : iv)
                    iv2.setEnabled(true);
            }
        }

        public void setIvArrayUnClickable() {
            for (ImageView[] iv : ivArray) {
                for (ImageView iv2 : iv)
                    iv2.setEnabled(false);
            }
        }
    }
}

package com.example.shufflepuzzle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shufflepuzzle.R;
import com.example.shufflepuzzle.activity.ImageSwapPuzzleActivity;

import java.util.ArrayList;

public class CropImageRecyclerAdapter extends RecyclerView.Adapter<CropImageRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<Bitmap> cropImageViewItems;
    SparseBooleanArray clickStateItems; //이미지들의 클릭상태 저장
    ArrayList<ImageView> clickedCropIvItems;//클릭된 이미지뷰를 보관
    ArrayList<ImageView> originalCropIvItemList;

    public CropImageRecyclerAdapter(Context context) {
        this.context = context;
        cropImageViewItems = new ArrayList<>();
        clickStateItems = new SparseBooleanArray();
        clickedCropIvItems = new ArrayList<>();
        originalCropIvItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CropImageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.activity_image_swap_puzzle_layout_item_crop_imagelist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CropImageRecyclerAdapter.ViewHolder holder, int position) {
        Bitmap item = getItem(position);
        holder.itemView.getLayoutParams().width = item.getWidth();
        holder.itemView.getLayoutParams().height = item.getHeight();
        holder.itemView.requestLayout();
        holder.setItem(item, position);

    }

    @Override
    public int getItemCount() {
        return cropImageViewItems.size();
    }

    public Bitmap getItem(int position) {
        return cropImageViewItems.get(position);
    }

    public void addItem(Bitmap item) {
        cropImageViewItems.add(item);
    }

    public void clearItems() {
        cropImageViewItems.clear();
        originalCropIvItemList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<ImageView> getOriginalCropIvItemList() {
        return originalCropIvItemList;
    }

    public void setCropIvItemListEnable(boolean isEnable){
        for(int i=0; i<originalCropIvItemList.size(); i++){
            originalCropIvItemList.get(i).setEnabled(isEnable);
        }
    }

    public void swapCropImage(ImageView firstIv, ImageView secondIv) {//이미지와 태그내용 바꿈
        Drawable tempDrawable = firstIv.getDrawable();
        String tempTag = firstIv.getTag().toString();
        firstIv.setImageDrawable(secondIv.getDrawable());
        firstIv.setTag(secondIv.getTag());
        secondIv.setImageDrawable(tempDrawable);
        secondIv.setTag(tempTag);
    }

    public void shuffle(int matrixCount) {
        int maxIdx = matrixCount * matrixCount;
        int itemSize = originalCropIvItemList.size();

        for (int i = 0; i < itemSize; i++) {  //클릭 다시 활성화
            originalCropIvItemList.get(i).setEnabled(true);
        }

        for (int i = 0; i < itemSize; i++) {
            int firstNum = (int) (Math.random() * maxIdx);
            int secondNum = (int) (Math.random() * maxIdx);
            swapCropImage(originalCropIvItemList.get(firstNum), originalCropIvItemList.get(secondNum));
            Log.d("셔플함수", "매트릭스넘버 : " + maxIdx);
            Log.d("셔플함수", firstNum + "번과 " + secondNum + "번을 바꿈");
        }

    }


    private boolean checkGameClear() {
        for (int i = 0; i < originalCropIvItemList.size(); i++) {
            if (!originalCropIvItemList.get(i).getTag().toString().equals(String.valueOf(i))) {
                return false;
            }
        }
        return true;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cropIv;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.cropIv = itemView.findViewById(R.id.activity_image_swap_puzzle_layout_item_crop_iv);

            cropIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickStateItems.get(getAdapterPosition())) {  //이미지가 선택되어 있는 경우
                        cropIv.setColorFilter(null);
                        clickStateItems.delete(getAdapterPosition());
                        clickedCropIvItems.remove(cropIv);
                    } else {                                          //이미지를 처음 선택하는 경우
                        cropIv.setColorFilter(R.color.gray_B9B9B9, PorterDuff.Mode.DARKEN);
                        clickStateItems.put(getAdapterPosition(), true);
                        clickedCropIvItems.add(cropIv);
                    }

                    if (clickedCropIvItems.size() == 2) {   //이미지가 2개 선택되어 서로 바뀌는 경우
                        swapCropImage(clickedCropIvItems.get(0), clickedCropIvItems.get(1));
                        clickedCropIvItems.get(0).setColorFilter(null);
                        clickedCropIvItems.get(1).setColorFilter(null);
                        clickStateItems.clear();
                        clickedCropIvItems.clear();

                        if (checkGameClear()) {
                            ((ImageSwapPuzzleActivity) context).gameSet();
                        }

                    }
                }
            });

        }

        public void setItem(Bitmap item, int position) {
            cropIv.setImageBitmap(item);
            cropIv.setTag(position);
            originalCropIvItemList.add(cropIv);
            cropIv.setEnabled(false);
        }
    }


}

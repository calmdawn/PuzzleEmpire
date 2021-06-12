package com.example.shufflepuzzle.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shufflepuzzle.R;

import java.util.ArrayList;

public class PhotoListRecyclerAdapter extends RecyclerView.Adapter<PhotoListRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<Integer> imgResItems;

    private PhotoListClickListener photoListClickListener;

    public interface PhotoListClickListener{
        void onPhotoListItemClicked(View v, int position);
    }

    public PhotoListRecyclerAdapter(Context context,PhotoListClickListener photoListClickListener) {
        this.context = context;
        this.photoListClickListener = photoListClickListener;
        imgResItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public PhotoListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.activity_image_swap_puzzle_layout_item_photolist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListRecyclerAdapter.ViewHolder holder, int position) {
        int imgRes = imgResItems.get(position);
        holder.setItem(imgRes);
    }

    @Override
    public int getItemCount() {
        return imgResItems.size();
    }

    public void addItem(int imgRes){
        imgResItems.add(imgRes);
    }

    public int getItem(int position){
        return imgResItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.photoIv = itemView.findViewById(R.id.activity_image_swap_puzzle_layout_item_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photoListClickListener.onPhotoListItemClicked(view, getAdapterPosition());
                }
            });

        }

        public void setItem(int imgRes) {
            photoIv.setImageResource(imgRes);
        }
    }
}

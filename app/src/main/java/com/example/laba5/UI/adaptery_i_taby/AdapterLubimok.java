package com.example.laba5.UI.adaptery_i_taby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laba5.API.elements.Photo;
import com.example.laba5.R;

import java.util.List;



public class AdapterLubimok extends RecyclerView.Adapter<AdapterLubimok.ItemViewHolder> {
        private Context context;
        private List<Photo> list;
        private ItemViewHolder viewHolder;

        public AdapterLubimok(Context context, List<Photo> arrayPhoto) {
            this.context = context;
            this.list = arrayPhoto;
            System.out.println("array " + arrayPhoto.size());
        }

        @NonNull
        @Override
        public AdapterLubimok.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab2_item, parent, false);
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
            final Photo currentItem = list.get(position);
            viewHolder = (ItemViewHolder) holder;

            String imageUrl = currentItem.getImageUrl();
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.photo)
                    .into(viewHolder.imageView);
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.tab2_item_image);
            }

        }
    }

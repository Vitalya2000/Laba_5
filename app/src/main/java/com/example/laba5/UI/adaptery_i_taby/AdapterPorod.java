package com.example.laba5.UI.adaptery_i_taby;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laba5.API.CatApi;
import com.example.laba5.API.elements.Photo;
import com.example.laba5.API.elements.SozdaniePosta;
import com.example.laba5.API.elements.LikeDislike;
import com.example.laba5.MainActivity;
import com.example.laba5.R;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdapterPorod extends RecyclerView.Adapter<AdapterPorod.ItemViewHolder> {
    private Context context;
    private List<Photo> list;
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ItemViewHolder viewHolder;
    private Retrofit retrofit;
    private CatApi api;


    public AdapterPorod(Context context, List<Photo> arrayPhotoDTO) {
        this.context = context;
        this.list = arrayPhotoDTO;
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(CatApi.class);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab1_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        viewHolder = (ItemViewHolder) holder;

        String imageUrl = list.get(position).getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.photo)
                .into(viewHolder.imageView);
        final SozdaniePosta sozdaniePosta = new SozdaniePosta(MainActivity.USER_ID, list.get(position).getImageId());

        holder.imageButton_like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (list.get(position).isLike() == -1 || list.get(position).isLike() == 0) {
                    list.get(position).setLike(1);
                    sozdaniePosta.setValue(1);
                    Call<LikeDislike> call = api.setPostFavourites(sozdaniePosta);
                    call.enqueue(new Callback<LikeDislike>() {
                        @Override
                        public void onResponse(Call<LikeDislike> call, Response<LikeDislike> response) {
                            if (response.isSuccessful()) {
                                System.out.println("ЛАЙК ПЕРЕДАН В СИСТЕМУ" + response.code());
                                Toast.makeText(context, "Отметка Мне Нравится поставлена!", Toast.LENGTH_SHORT).show();
                                list.get(position).setId(response.body().getVote_id());
                            }
                        }

                        @Override
                        public void onFailure(Call<LikeDislike> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                else if (list.get(position).isLike() == 1) {
                    list.get(position).setLike(-1);
                    sozdaniePosta.setValue(-1);
                    Call<Void> call = api.delVote(list.get(position).getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("daniel", "ЛАЙК ОТМЕНЕН " + response.code());
                                Toast.makeText(context, "Отметка Мне Нравится снята!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
        holder.imageButton_dislike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (list.get(position).isLike() == -1 || list.get(position).isLike() == 1) {
                    list.get(position).setLike(0);
                    sozdaniePosta.setValue(0);
                    Call<LikeDislike> call = api.setPostFavourites(sozdaniePosta);
                    call.enqueue(new Callback<LikeDislike>() {
                        @Override
                        public void onResponse(Call<LikeDislike> call, Response<LikeDislike> response) {
                            if (response.isSuccessful()) {
                                System.out.println("ДИЗЛАЙК В СИСТЕМУ ПЕРЕДАН" + response.code());
                                Toast.makeText(context, "Отметка Мне Не Нравится поставлена!", Toast.LENGTH_SHORT).show();
                                list.get(position).setId(response.body().getVote_id());
                            }
                        }

                        @Override
                        public void onFailure(Call<LikeDislike> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                else if (list.get(position).isLike() == 0) {
                    list.get(position).setLike(-1);
                    sozdaniePosta.setValue(-1);
                    Call<Void> call = api.delVote(list.get(position).getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("daniel", "Дизайк отменен" + response.code());
                                Toast.makeText(context, "Отметка Мне Не Нравится снята!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addImages(List<Photo> photoDTOArrayList) {
        for (Photo p : photoDTOArrayList) {
            list.add(p);
        }
        notifyDataSetChanged();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageButton imageButton_like;
        public ImageButton imageButton_dislike;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tab1_item_image);
            imageButton_like = itemView.findViewById(R.id.tab1_item_image_bottom_like);
            imageButton_dislike = itemView.findViewById(R.id.tab1_item_image_bottom_dislike);
        }

    }
}

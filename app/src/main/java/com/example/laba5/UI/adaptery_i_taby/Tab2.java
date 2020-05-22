package com.example.laba5.UI.adaptery_i_taby;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laba5.API.CatApi;
import com.example.laba5.API.elements.Photo;
import com.example.laba5.API.elements.PolucheniePosta;
import com.example.laba5.MainActivity;
import com.example.laba5.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
    private TextView textView;
    private AdapterLubimok adapterLubimok;
    private RecyclerView recyclerView;
    private ArrayList<PolucheniePosta> posty;
    private ArrayList<Photo> photos;
    private GridLayoutManager layoutManager;
    private Retrofit retrofit;
    private CatApi api;

    public Tab2() {
        posty = new ArrayList<>();
        photos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        textView = (TextView) view.findViewById(R.id.tab2_text);
        recyclerView = (RecyclerView) view.findViewById(R.id.tab2_recycle_view);
        recyclerView.setVisibility(View.GONE);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(CatApi.class);
        api.getVotes(MainActivity.USER_ID).enqueue(new retrofit2.Callback<List<PolucheniePosta>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PolucheniePosta>> call, retrofit2.Response<List<PolucheniePosta>> response) {
                if (response.isSuccessful()) {
                    Log.d("daniel", "история " + response.code());
                    photos.clear();
                    posty.clear();
                    posty = new ArrayList<PolucheniePosta> (response.body());
                    getPhotos();
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<PolucheniePosta>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        textView.setText(R.string.tab_text_2);
        return view;
    }

    public void getPhotos() {
        for (int i = posty.size() - 1, j = 0; i >= 0 && j < 10; i--) {
            if (posty.get(i).getValue() == 1) {
                j++;
                api.getVotesLike(posty.get(i).getImageId()).enqueue(new retrofit2.Callback<Photo>() {
                    @Override
                    public void onResponse(retrofit2.Call<Photo> call, retrofit2.Response<Photo> response) {
                        if (response.isSuccessful()) {
                            Log.d("daniel", "url " + response.code());
                            photos.add(response.body());
                            createRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Photo> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    private void createRecyclerView() {
        if (photos.size() != 0) {
            textView.setText(R.string.last_like);
            layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
            adapterLubimok = new AdapterLubimok(getActivity(), photos);
            recyclerView.setAdapter(adapterLubimok);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        photos.clear();
        posty.clear();
    }
}

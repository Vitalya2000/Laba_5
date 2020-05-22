package com.example.laba5.API;

import com.example.laba5.API.elements.LikeDislike;
import com.example.laba5.API.elements.Photo;
import com.example.laba5.API.elements.PolucheniePosta;
import com.example.laba5.API.elements.Poroda;
import com.example.laba5.API.elements.SozdaniePosta;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface CatApi {
    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("breeds")
    Call<List<Poroda>> getBreeds();

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("images/search?mime_types=gif,jpg,png")
    Call<List<Photo>> getPhotoForBreed(@Query("breed_ids") String breed,
                                          @Query("limit") int limit,
                                          @Query("order") String desc,
                                          @Query("page") int page
    );

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("votes")
    Call<List<PolucheniePosta>> getVotes(@Query("sub_id") String sub_id);

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("images/{image_id}")
    Call<Photo> getVotesLike(@Path("image_id") String image_id
    );

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @DELETE("votes/{vote_id}")
    Call<Void> delVote(@Path("vote_id") int vote_id
    );

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @POST("votes")
    Call<LikeDislike> setPostFavourites(@Body SozdaniePosta sozdaniePosta);

}
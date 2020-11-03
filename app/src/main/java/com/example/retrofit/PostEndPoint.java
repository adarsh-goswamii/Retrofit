package com.example.retrofit;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostEndPoint
{
    /**
     * if we want to get a particular post we can write @GET("posts/{id}")
     * ans you will get the post with that id.
     *
     * Lets say we want a post which was uploaded by a particular person or lets say
     * with a particular userId. then
     */
    @GET("posts")
    Call<ArrayList<Post>> getPost();

    @GET("posts")
    Call<ArrayList<Post>> getPost(@Query("userId") int id);

    @POST("posts")
    Call<Post> newPost(@Header ("token")String token, @Body Post post);

    /**
     * In real life when communicating with web servers there is a concept of tokens while posting something on the
     * web. Basically token is first generated and stored inside some database and when someone tries to
     * post something if the token matches to one saved in their database then only we get a response.
     */
}

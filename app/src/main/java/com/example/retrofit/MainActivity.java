package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    /**
     * Retrofit requires 21+ API and 8+ java.
     * Retrofit is also used for networking in android like the volley
     * But advantage of retrofit over volley is that it is much more flexible
     * and easy to use than volley.
     *
     * We will see the usage of retrofit in this code and how we can make query too.
     */

    private EditText editText;
    private TextView textView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllPosts();
            }

            private void getAllPosts()
            {
                /**
                 * So for debugging purposes if we could log the activities that's happening with our web server
                 * that would be really helpful in order to do so we have
                 * Okhttp logging interceptor.
                 *
                 * There are different levels of interceptor which basically means how much information we want to
                 * log.
                 * (i). Level.BASIC (ii). HEADERS (iii). BODY: logs everything (iv). NONE
                 */

                OkHttpClient.Builder clientBuilder= new OkHttpClient.Builder();
                HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                clientBuilder.addInterceptor(interceptor);

                Retrofit retrofit= new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(clientBuilder.build())
                        .build();

                PostEndPoint endPoint= retrofit.create(PostEndPoint.class);
//                Call<ArrayList<Post>> call= endPoint.getPost();
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Call call, Response response) {
//                        ArrayList<Post> posts= (ArrayList<Post>) response.body();
//                        for(Post p: posts)
//                            textView.setText(textView.getText()+"\n"+p.getTitle());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Throwable t) {
//                        t.getStackTrace();
//                    }
//                });

//                Call<ArrayList<Post>> call2= endPoint.getPost(Integer.parseInt(editText.getText().toString()));
//                call2.enqueue(new Callback<ArrayList<Post>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
//                        ArrayList<Post> posts= (ArrayList<Post>) response.body();
//                        for(Post p: posts)
//                            textView.setText(textView.getText()+"\n"+p.getTitle());
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
//
//                    }
//                });

                /**
                 * There are many Http codes that we see while communicating with a web server few of them are
                 * (i). 404 no server with that address found.
                 * (ii). 201 when we successfully post something on the web server.
                 * (iii). 500 some internal error occurred.
                 * There are many more codes when we try to interact with a web server we get one of these
                 * we can retrieve this code by response.code().
                 */

                String token ="As this is not a real application we will use this as our token in many applications we get this token from some database";


                Post post= new Post(209, 12, "Title of the post", "Body of the post");
                Call<Post> call= endPoint.newPost(token, post);
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        Toast.makeText(MainActivity.this, response.code()+"", Toast.LENGTH_SHORT).show();
                        if(response.isSuccessful())
                        {
                            Post post= response.body();
                            textView.setText(post.getTitle());
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        t.getStackTrace();
                    }
                });
            }
        });
    }
}
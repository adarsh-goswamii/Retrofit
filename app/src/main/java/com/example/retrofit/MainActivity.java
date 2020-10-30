package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

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
                Retrofit retrofit= new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .addConverterFactory(GsonConverterFactory.create())
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

                Call<ArrayList<Post>> call2= endPoint.getPost(Integer.parseInt(editText.getText().toString()));
                call2.enqueue(new Callback<ArrayList<Post>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                        ArrayList<Post> posts= (ArrayList<Post>) response.body();
                        for(Post p: posts)
                            textView.setText(textView.getText()+"\n"+p.getTitle());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Post>> call, Throwable t) {

                    }
                });
            }
        });
    }
}
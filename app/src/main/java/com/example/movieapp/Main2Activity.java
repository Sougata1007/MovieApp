package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
ImageView imageView2;
TextView textView;
String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    imageView2=findViewById(R.id.imageView2);
    textView=findViewById(R.id.textView2);
        Intent intent = getIntent();
                int id = intent.getIntExtra("movie_id",0);
        String url= "https://api.themoviedb.org/3/movie/" +id+"?api_key=e38c2da81597eb58df0eef26681eab75&language=en-us";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentobject = new JSONObject(response);
                    String poster_url = "https://image.tmdb.org/t/p/w500//" + parentobject.getString("poster_path");
                    Picasso.with(Main2Activity.this).load(poster_url).into(imageView2);
                    textView.setText(parentobject.getString("overview"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(Main2Activity.this).add(stringRequest);
    }
}

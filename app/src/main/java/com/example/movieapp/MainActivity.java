package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String[] movie_name = new String[10];
    String[] movie_poster = new String[10];
    String[] movie_rating = new String[10];
    Integer[] movie_id = new Integer[10];
    String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=e38c2da81597eb58df0eef26681eab75&language=en-US&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
            try {
                JSONObject parentobject = new JSONObject(response);
                JSONArray results = parentobject.getJSONArray("results");
                for(int i = 0;i<10;i++){
                    JSONObject temp = results.getJSONObject(i);
                    movie_id[i] = temp.getInt("id");
                    movie_name[i]=temp.getString("title");
                    movie_rating[i]=temp.getString("vote_average");
                    movie_poster[i]= "https://image.tmdb.org/t/p/w500//"+temp.getString("poster_path");
                }
                CustomAdapter cadapter = new CustomAdapter();
                lv.setAdapter(cadapter);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

            });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent j = new Intent(MainActivity.this,Main2Activity.class);
                j.putExtra("movie_id",movie_id[position]);
                startActivity(j);
            }
        });



    }

    class CustomAdapter extends BaseAdapter {

        ImageView imageView;
        TextView textView, textView2;

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.single_movie, null);
            imageView = convertView.findViewById(R.id.imageView);
            textView = convertView.findViewById(R.id.textView);
            textView2 = convertView.findViewById(R.id.textView2);


            textView.setText(movie_name[position]);
            textView2.setText(movie_rating[position]);
            Picasso.with(MainActivity.this).load(movie_poster[position]).into(imageView);

            return convertView;
        }
    }
}
package com.im2nadif.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView lvItem;

    private String[] title = new String[]{};
    private String[] date  = new String[]{};
    private String[] desc  = new String[]{};
    private String[] rate  = new String[]{};
    public String[] img    = new String[]{};
    public String[] cover  = new String[]{};
    public String[] views  = new String[]{};
    public String[] voter  = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyAsync().execute();

        FloatingActionButton aboutMe = (FloatingActionButton) findViewById(R.id.buttonAbout);
        aboutMe.setOnClickListener(v->{
           startActivity(new Intent(MainActivity.this, ProfileActivity.class));
       });
    }

    private class MyAsync extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object[] objects) {

            String url = "https://api.themoviedb.org/3/trending/movie/week?api_key=7625100ef7bde8dded1f24cbee4d01a0";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.failure_connect), Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseData = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONObject objData = new JSONObject(responseData);
                                JSONArray array = objData.getJSONArray("results");

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject c = array.getJSONObject(i);

                                    String j_title = c.getString("title");
                                    String j_desc = c.getString("overview");
                                    String j_rate = c.getString("vote_average");
                                    String j_date = c.getString("release_date");
                                    String j_img = c.getString("poster_path");
                                    String j_cover = c.getString("backdrop_path");
                                    String j_views = c.getString("popularity");
                                    String j_voter = c.getString("vote_count");

                                    ArrayList<String> titleList = new ArrayList<String>(Arrays.asList(title));
                                    titleList.add(j_title);
                                    title = titleList.toArray(title);

                                    ArrayList<String> descList = new ArrayList<String>(Arrays.asList(desc));
                                    descList.add(j_desc);
                                    desc = descList.toArray(desc);

                                    ArrayList<String> rateList = new ArrayList<String>(Arrays.asList(rate));
                                    rateList.add(j_rate);
                                    rate = rateList.toArray(rate);

                                    ArrayList<String> dateList = new ArrayList<String>(Arrays.asList(date));
                                    dateList.add(j_date);
                                    date = dateList.toArray(date);

                                    ArrayList<String> imgList = new ArrayList<String>(Arrays.asList(img));
                                    imgList.add(j_img);
                                    img = imgList.toArray(img);

                                    ArrayList<String> coverList = new ArrayList<String>(Arrays.asList(cover));
                                    coverList.add(j_cover);
                                    cover = coverList.toArray(cover);

                                    ArrayList<String> viewsList = new ArrayList<String>(Arrays.asList(views));
                                    viewsList.add(j_views);
                                    views = viewsList.toArray(views);

                                    ArrayList<String> voterList = new ArrayList<String>(Arrays.asList(voter));
                                    voterList.add(j_voter);
                                    voter = voterList.toArray(voter);
                                }

                                lvItem = findViewById(R.id.listFilm);
                                FilmAdapter adapter = new FilmAdapter(MainActivity.this, title, date, desc, rate, img, cover, views,voter);
                                lvItem.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

            return null;

        }
    }
}
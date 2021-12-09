package com.im2nadif.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class FilmDetails extends AppCompatActivity {
    private TextView judulFilm, tanggalRilis, deskripsi, rate, jumlahVote, jumlahViews;
    RatingBar ratBintang;
    ImageView imgPoster, imgCover;
    ProgressBar views;
    private int jumView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        judulFilm = (TextView) findViewById(R.id.judulFilm);
        judulFilm.setText(getIntent().getStringExtra(FilmAdapter.EXTRA_TITLE));

        tanggalRilis = (TextView) findViewById(R.id.tanggalRilis);
        tanggalRilis.setText(getResources().getString(R.string.date) + " " + getIntent().getStringExtra(FilmAdapter.EXTRA_DATE));

        deskripsi = (TextView) findViewById(R.id.deskripsiFilm);
        deskripsi.setText(getIntent().getStringExtra(FilmAdapter.EXTRA_DESC));

        jumlahViews = (TextView) findViewById(R.id.jumlahViews);
        jumView = Integer.parseInt(getIntent().getStringExtra(FilmAdapter.EXTRA_VIEWS).replace(".",""));
        views = (ProgressBar) findViewById(R.id.popularitas);
        views.setProgress(jumView);
        new MyAsync().execute(50);

        jumlahVote = (TextView) findViewById(R.id.jumlahVote);
        jumlahVote.setText(getIntent().getStringExtra(FilmAdapter.EXTRA_VOTER) + " " + getResources().getString(R.string.vote));

        String rates = getIntent().getStringExtra(FilmAdapter.EXTRA_RATE);
        rate = (TextView) findViewById(R.id.ratingAngka);
        rate.setText(String.format("%.1f", Double.parseDouble(rates)).replace(",", "."));

        ratBintang = (RatingBar) findViewById(R.id.rateBintang);
        ratBintang.setRating(Float.parseFloat(rates) / 2);

        String url = "https://themoviedb.org/t/p/w500/";
        imgPoster = (ImageView) findViewById(R.id.poster);
        String urlPoster = url + getIntent().getStringExtra(FilmAdapter.EXTRA_POSTER);
        Glide.with(this).load(urlPoster).apply(RequestOptions.bitmapTransform(new RoundedCorners(25))).into(imgPoster);

        imgCover = (ImageView) findViewById(R.id.cover);
        String urlCover = url + getIntent().getStringExtra(FilmAdapter.EXTRA_COVER);
        Glide.with(this).load(urlCover).apply(RequestOptions.bitmapTransform(new RoundedCorners(25))).into(imgCover);

    }

    private class MyAsync extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {

            try{
                int count = integers[0];
                for(int i=0; i<count; i++){
                    Thread.sleep(10);
                    int value = (int)(((i+1)/(float)count)*jumView);
                    publishProgress(value);
                }

            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values[0]);
            views.setProgress(values[0]);
            jumlahViews.setText(values[0] + " "+ getResources().getString(R.string.views));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
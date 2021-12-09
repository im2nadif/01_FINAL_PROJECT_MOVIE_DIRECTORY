package com.im2nadif.uts;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.Map;


public class FilmAdapter extends BaseAdapter {
    protected static final String EXTRA_TITLE = "extra_title";
    protected static final String EXTRA_DATE = "extra_date";
    protected static final String EXTRA_RATE = "extra_rate";
    protected static final String EXTRA_POSTER = "extra_poster";
    protected static final String EXTRA_DESC = "extra_desc";
    protected static final String EXTRA_COVER = "extra_cover";
    protected static final String EXTRA_VIEWS = "extra_views";
    protected static final String EXTRA_VOTER = "extra_voter";

    Context context;
    String[] title;
    String[] date;
    String[] desc;
    String[] rate;
    String[] img;
    String[] cover;
    String[] views;
    String[] voter;
    LayoutInflater inflater;

    public FilmAdapter(Context context, String[] title, String[] date, String[] desc, String[] rate, String[] img,  String[] cover,  String[] views, String [] voter) {
        this.context = context;
        this.title = title;
        this.date = date;
        this.desc = desc;
        this.rate = rate;
        this.img = img;
        this.cover = cover;
        this.views = views;
        this.voter = voter;
        this.inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return title.length;
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
    public View getView(int pos, View v, ViewGroup parent) {

        v = inflater.inflate(R.layout.list_film, null);
        TextView titles = (TextView) v.findViewById(R.id.judulFilm);
        TextView dates = (TextView) v.findViewById(R.id.tanggalRilis);
        TextView descs = (TextView) v.findViewById(R.id.deskripsiSingkat);
        TextView rates = (TextView) v.findViewById(R.id.RatingAngka);
        RatingBar ratBars = (RatingBar) v.findViewById(R.id.RatingBintang);
        ImageView imgs = (ImageView) v.findViewById(R.id.posterFilm);

        titles.setText(title[pos]);
        dates.setText(context.getString(R.string.date) + " " + date[pos]);
        descs.setText(context.getString(R.string.description) + " " + desc[pos]);
        rates.setText(String.format("%.1f", Double.parseDouble(rate[pos])).replace(",", "."));
        ratBars.setRating(Float.parseFloat(rate[pos]) / 2);
        String urlPoster = "https://themoviedb.org/t/p/w500/" + img[pos];
        Glide.with(context).load(urlPoster).apply(RequestOptions.bitmapTransform(new RoundedCorners(25))).into(imgs);

        v.setOnClickListener(view->{

            Intent intent=new Intent(context.getApplicationContext(), FilmDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra(EXTRA_TITLE, title[pos]);
            intent.putExtra(EXTRA_DATE, date[pos]);
            intent.putExtra(EXTRA_RATE, rate[pos]);
            intent.putExtra(EXTRA_POSTER, img[pos]);
            intent.putExtra(EXTRA_DESC, desc[pos]);
            intent.putExtra(EXTRA_COVER, cover[pos]);
            intent.putExtra(EXTRA_VIEWS, views[pos]);
            intent.putExtra(EXTRA_VOTER, voter[pos]);

            context.getApplicationContext().startActivity(intent);
        });
        return v;
    }
}

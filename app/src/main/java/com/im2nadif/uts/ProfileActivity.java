package com.im2nadif.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView imageProfile = (ImageView) findViewById(R.id.profilePage);
        Glide.with(ProfileActivity.this)
                .load(R.drawable.pprofile)
                .apply(RequestOptions.circleCropTransform())
                .into(imageProfile);

        Button tel = (Button) findViewById(R.id.tel);

        tel.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:085888448566"));
            startActivity(intent);
        });

        Button email = (Button) findViewById(R.id.email);
        email.setOnClickListener(view -> {
            String mailto = "mailto:ilham.mafani.nadif@students.amikom.ac.id" +
                    "?cc=" +
                    "&subject=" + Uri.encode("I want to ask about: ") +
                    "&body=" + Uri.encode("Dear Ilham Mafani Nadif");
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(mailto));

            try {
                startActivity(emailIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Error to open email app", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
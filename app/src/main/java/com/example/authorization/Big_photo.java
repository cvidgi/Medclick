package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Big_photo extends AppCompatActivity {

    private ImageView BigPhoto;
    private String number;
    private Bitmap bitmap;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big__photo);

        BigPhoto = findViewById(R.id.Big_Image);
        back = findViewById(R.id.back_in_bigphoto);

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");

        if(getIntent().hasExtra("byteArray")) {
            ImageView imv= new ImageView(this);
            bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            imv.setImageBitmap(bitmap);
        }

        BigPhoto.setImageBitmap(bitmap);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Big_photo.super.finish();
            }
        });
    }
}
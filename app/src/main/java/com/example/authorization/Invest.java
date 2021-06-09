package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Invest extends AppCompatActivity {

    private String number;
    private ImageView back;
    private GridLayout gridlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");
        back = findViewById(R.id.back_btn_in_invest);
        gridlayout = findViewById(R.id.Grid_layout);

//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//
//        int width_display = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
//
//        int height_display = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());


//        for (int i = 0; i<25; i++){
//            ImageView imageView = new ImageView(Invest.this);
//            imageView.setImageResource(R.drawable.ic_profile);
//            RelativeLayout.LayoutParams imageViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            imageView.setLayoutParams(imageViewLayoutParams);
//
//            gridlayout.addView(imageView);
//        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }

}
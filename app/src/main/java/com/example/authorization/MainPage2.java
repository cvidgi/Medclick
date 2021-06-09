package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainPage2 extends AppCompatActivity {
    private ConstraintLayout cs;
    private ConstraintLayout dialogs;
    private ConstraintLayout profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page2);

        Intent intent = getIntent();
        String number = (String) intent.getExtras().get("number");

        cs=findViewById(R.id.center_button_videochat);
        dialogs = findViewById(R.id.cs_to_dial);
        profile = findViewById(R.id.profile_in_main_page);

        ConstraintLayout constraintLayout1 = findViewById(R.id.constraintLayout_to_animation_in_main_page);
        ConstraintLayout constraintLayout2 = findViewById(R.id.constraintLayout_to_animation_in_dialogs);

        Animation an1 = AnimationUtils.loadAnimation(this, R.anim.main_left);
        Animation an2 = AnimationUtils.loadAnimation(this, R.anim.second_left);

        dialogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dialogs.class);
                intent.putExtra("number", number);
//                constraintLayout1.startAnimation(an1);
//                constraintLayout2.startAnimation(an2);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }
}
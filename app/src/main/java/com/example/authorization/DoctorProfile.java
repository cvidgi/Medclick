package com.example.authorization;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static android.widget.Toast.LENGTH_SHORT;

public class DoctorProfile extends AppCompatActivity {
    String number;
    private ImageView back;
    private boolean flag;
    private Doctor doctor;
    private ConstraintLayout profile;
    private ConstraintLayout dialogs;
    private ConstraintLayout main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        getSupportActionBar().hide();
        back = findViewById(R.id.back_doctor_card);
        profile = findViewById(R.id.profile_in_doctorprofile);
        dialogs = findViewById(R.id.cs_to_dial);
        main = findViewById(R.id.main_cs_in_doct);

        Intent intent = getIntent();
        doctor = (Doctor) intent.getExtras().get("doctor");
        number = (String) intent.getExtras().get("number");


        try {
            flag = (boolean) intent.getExtras().get("flag");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), LENGTH_SHORT).show();
        }

        SQLiteDatabase doctorsDataBase = openOrCreateDatabase("doctors", MODE_PRIVATE, null);
        doctorsDataBase.execSQL("create table if not exists doctors\n" +
                "(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tDoctorName text, \n" +
                "\tDoctorProfession text, \n" +
                "\tDoctorPrice int, \n" +
                "\tDoctorPhone text, \n" +
                "\tDoctorStaj int \n" +
                ");");
        Cursor c = doctorsDataBase.rawQuery("select * from doctors where DoctorPhone=?", new String[]{doctor.getPhone()});
        c.moveToFirst();

        int DoctorProfIndex = c.getColumnIndex("DoctorProfession");
        int DoctorNameIndex = c.getColumnIndex("DoctorName");
        int DoctorPriceIndex = c.getColumnIndex("DoctorPrice");
        int DoctorStajIndex = c.getColumnIndex("DoctorStaj");

        TextView textViewName = findViewById(R.id.doctor_card_name);
        textViewName.setText(c.getString(DoctorNameIndex));

        TextView textViewProf = findViewById(R.id.doctor_card_proffession_title);
        textViewProf.setText(c.getString(DoctorProfIndex));

        TextView textViewstaj = findViewById(R.id.exp_year_doctor_profile);
        int staj = c.getInt(DoctorStajIndex);
        staj = staj%100;
        if (staj%10==1 && (staj<10 || staj>20)){textViewstaj.setText(c.getString(DoctorStajIndex) + " год");}
        else if ((staj%10<5 && staj%10>1) && (staj<10 || staj>20)){textViewstaj.setText(c.getString(DoctorStajIndex) + " года");}
        else{textViewstaj.setText(c.getString(DoctorStajIndex) +" лет");}

        TextView textViewPrice = findViewById(R.id.price_doctor_profile);
        textViewPrice.setText(c.getString(DoctorPriceIndex) + " ₽");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("number", number);
                intent.putExtra("doctor", doctor);
                intent.putExtra("flag", flag);
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

        dialogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dialogs.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPage2.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }


    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
        intent.putExtra("number", number);
        intent.putExtra("flag", flag);
        intent.putExtra("doctor", doctor);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
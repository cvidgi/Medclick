package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button next;
    private ImageView sogl;
    private ConstraintLayout cs;
    public List<Map.Entry<String, String>> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = findViewById(R.id.next_btn);
        sogl = findViewById(R.id.sogl);
        cs = findViewById(R.id.sogl_cs);

        list = new LinkedList<>();

        SQLiteDatabase lastuser = openOrCreateDatabase("lastuser", MODE_PRIVATE, null);
        lastuser.execSQL("create table if not exists lastuser\n" +
                "(\n" +
                "\tUserPhone varchar(10) \n" +
                ");");

        Cursor c = lastuser.rawQuery("select * from lastuser", null);
        int id = c.getColumnIndex("UserPhone");
        if(c.moveToFirst()){
            Intent intent_dial = new Intent(getApplicationContext(), MainPage2.class);
            intent_dial.putExtra("number", c.getString(id));
            intent_dial.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent_dial);
            overridePendingTransition(0, 0);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), auth2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Soglashenie.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

    }
}
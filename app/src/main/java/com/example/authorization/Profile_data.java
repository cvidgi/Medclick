package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Profile_data extends AppCompatActivity {

    private String number;
    private EditText name;
    private EditText surname;
    private EditText middlename;
    private TextView date;
    private Button save;
    private Button back;
    private TextView error;
    private EditText polis;
    Calendar dateAndTime=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");

        name = findViewById(R.id.Name);
        surname = findViewById(R.id.Surname);
        middlename = findViewById(R.id.middle_name);
        date = findViewById(R.id.date);
        save = findViewById(R.id.Save_data);
        back = findViewById(R.id.back_btn5);
        error = findViewById(R.id.error);
        polis = findViewById(R.id.Polis_OMC);

        SQLiteDatabase usersDataBase = openOrCreateDatabase("users", MODE_PRIVATE, null);
        usersDataBase.execSQL("create table if not exists users\n" +
                "(\n" +
                "\tUserPhone varchar(10), \n" +
                "\tUserMiddlename text, \n" +
                "\tUserDopInfo text, \n" +
                "\tUserName text, \n" +
                "\tUserSurname text, \n" +
                "\tUserBirthday varchar(1000), \n" +
                "\tUserPolis varchar(1000) \n" +
                ");");


        Cursor cper = usersDataBase.rawQuery("select * from users where UserPhone=?", new String[]{number});
        cper.moveToFirst();

        try {
            int UserNameIndex = cper.getColumnIndex("UserName");
            int UserBirthdayIndex = cper.getColumnIndex("UserBirthday");
            int UserSurnameIndex = cper.getColumnIndex("UserSurname");
            int UserMiddlenameIndex = cper.getColumnIndex("UserMiddlename");
            int UserPolisIndex = cper.getColumnIndex("UserPolis");

            name.setText(cper.getString(UserNameIndex));
            surname.setText(cper.getString(UserSurnameIndex));
            date.setText(cper.getString(UserBirthdayIndex));
            middlename.setText(cper.getString(UserMiddlenameIndex));
            polis.setText(cper.getString(UserPolisIndex));
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !name.getText().toString().equals("") && !surname.getText().toString().equals("") && !date.getText().toString().equals("") && !middlename.getText().toString().equals("")) {
                    ContentValues values = new ContentValues();
                    values.put("UserName", name.getText().toString());
                    values.put("UserSurname", surname.getText().toString());
                    values.put("UserBirthday", date.getText().toString());
                    values.put("UserMiddlename", middlename.getText().toString());
                    values.put("UserPolis", polis.getText().toString());
                    usersDataBase.update("users", values, "UserPhone=?", new String[]{number});

                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    intent.putExtra("number", number);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                else{
                    error.setText(getString(R.string.Не_все_поля));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(date);
            }
        });

    }

    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {

        date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
}
package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class auth3 extends AppCompatActivity  {

    private Button next2;
    private Button back2;
    private EditText reg;
    private TextView errortext;
    private EditText input;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth3);

        Button next2 = findViewById(R.id.next_btn2);
        Button back2 = findViewById(R.id.back_btn2);
        EditText reg = findViewById(R.id.region_input);
        EditText phone = findViewById(R.id.phone_input);
        phone.setText("9999999999");
        TextView errortext = findViewById(R.id.errortext);


        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().equals("") || reg.getText().equals("")) {
                    errortext.setText("Вы не ввели номер");
                }else {
                    if (reg.getText().length() <= 4 & reg.getText().charAt(0) == '+') {
                        if (phone.length() == 10) {
                            String number = reg.getText().toString() + phone.getText().toString();

                            Intent intent_dialogs = new Intent(getApplicationContext(), Dialogs.class);
                            intent_dialogs.putExtra("number", number);

                            Intent intent = new Intent(getApplicationContext(), auth4.class);
                            intent.putExtra("number", number);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        } else {
                            errortext.setText("Неверный номер телефона");
                        }
                    } else {
                        errortext.setText("Неверный код страны");
                    }
                }
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), auth2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), auth2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class DoctorsList extends AppCompatActivity {

    private ListView listView;
    int count = 0;
    int count2 = 0;
    private ImageView filter;
    private ConstraintLayout top;
    private TextView professions;
    private TextView doctors_tv;
    private ConstraintLayout dialogs;
    private boolean flag;
    private String number;
    private ConstraintLayout profile;
    private EditText input;
    private List<String> proffessions_array = new ArrayList<>();
    private List<Doctor> filtredDoctors = new ArrayList<>();
    private ImageView back;
    private TextView text;
    private ConstraintLayout main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        getSupportActionBar().hide();


        listView = findViewById(R.id.list_of_professions);
        dialogs = findViewById(R.id.cs_to_dial);
        filter = findViewById(R.id.filter);
        top = findViewById(R.id.top_doctors);
        professions = findViewById(R.id.professions_text);
        doctors_tv = findViewById(R.id.doctors_text);
        profile = findViewById(R.id.profile_in_doctorlist);
        input = findViewById(R.id.input);
        back = findViewById(R.id.back_in_doctorlist);
        text = findViewById(R.id.textView14);
        main = findViewById(R.id.main_cs_in_doct);

        Intent intent = getIntent();
        number = (String) intent.getExtras().get("number");
        flag = true;
        Doctor doctor = null;

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

        doctorsDataBase.execSQL("insert into doctors (DoctorName, DoctorProfession, DoctorPrice, DoctorPhone, DoctorStaj) values ('Ivan Ivanov', 'Хирург', 1000, '1234567890', 7)");

        try{
            flag = (boolean) intent.getExtras().get("flag");
            doctor = (Doctor) intent.getExtras().get("doctor");
        } catch(Exception e){}


        ProfAdapter adapterProfs = new ProfAdapter(this, R.layout.profession_card, proffessions_array);
        DoctorAdapter adapterDoctor = new DoctorAdapter(this, R.layout.person, filtredDoctors);

        proffessions_array.add("Окулист");
        proffessions_array.add("Хирург");
        proffessions_array.add("Психолог");
        proffessions_array.add("Кардиолог");

        if(flag == true){

            listView.setAdapter(adapterProfs);
            text.setText("Получить консультацию");
            back.setVisibility(View.INVISIBLE);

        }
        else{
            filtredDoctors.clear();

            Cursor cprof = doctorsDataBase.rawQuery("select * from doctors where DoctorProfession=?", new String[]{doctor.getProffession()});
            cprof.moveToFirst();
            while(!cprof.isAfterLast()){

                int DoctorProfIndex = cprof.getColumnIndex("DoctorProfession");
                int DoctorNameIndex = cprof.getColumnIndex("DoctorName");
                int DoctorPriceIndex = cprof.getColumnIndex("DoctorPrice");
                int DoctorStajIndex = cprof.getColumnIndex("DoctorStaj");
                int DoctorPhoneIndex = cprof.getColumnIndex("DoctorPhone");

                Doctor doctor1 = new Doctor(cprof.getString(DoctorNameIndex), cprof.getString(DoctorProfIndex), cprof.getInt(DoctorPriceIndex), cprof.getInt(DoctorStajIndex), cprof.getString(DoctorPhoneIndex));
                filtredDoctors.add(doctor);
                cprof.moveToNext();
            }
            text.setText(doctor.getProffession());
            listView.setAdapter(adapterDoctor);
            professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
            doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag == true) {
                    filtredDoctors.clear();
                    Cursor cprof = doctorsDataBase.rawQuery("select * from doctors where DoctorProfession=?", new String[]{proffessions_array.get(position)});
                    cprof.moveToFirst();
                    while(!cprof.isAfterLast()){

                        int DoctorProfIndex = cprof.getColumnIndex("DoctorProfession");
                        int DoctorNameIndex = cprof.getColumnIndex("DoctorName");
                        int DoctorPriceIndex = cprof.getColumnIndex("DoctorPrice");
                        int DoctorStajIndex = cprof.getColumnIndex("DoctorStaj");
                        int DoctorPhoneIndex = cprof.getColumnIndex("DoctorPhone");

                        Doctor doctor = new Doctor(cprof.getString(DoctorNameIndex), cprof.getString(DoctorProfIndex), cprof.getInt(DoctorPriceIndex), cprof.getInt(DoctorStajIndex), cprof.getString(DoctorPhoneIndex));
                        filtredDoctors.add(doctor);
                        cprof.moveToNext();
                    }

                    professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
                    doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
                    filter.setVisibility(View.INVISIBLE);
                    text.setText(proffessions_array.get(position));
                    back.setVisibility(View.VISIBLE);

                    int margin112inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 112, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin112inDp);
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    top.setLayoutParams(layoutParams);

                    try {
                        int width16inDp = (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());

                        ConstraintLayout.LayoutParams long_input = (ConstraintLayout.LayoutParams) input.getLayoutParams();
                        long_input.rightMargin = width16inDp;
                        input.setLayoutParams(long_input);
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), LENGTH_LONG).show();
                    }

                    filter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filter));

                    listView.setAdapter(adapterDoctor);
                    flag = false;
                }else {
                    try {
                        Intent intent = new Intent(getApplicationContext(), DoctorProfile.class);
                        intent.putExtra("doctor", filtredDoctors.get(position));
                        intent.putExtra("number", number);
                        intent.putExtra("flag", flag);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), LENGTH_SHORT).show();
                    }
                    flag = true;
                }
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.setVisibility(View.VISIBLE);

                Intent intent2 = new Intent(getApplicationContext(), DoctorsList.class);
                intent2.putExtra("number", number);
                filter.setVisibility(View.VISIBLE);
                text.setText("Получить консультацию");

                int width68inDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 68, getResources().getDisplayMetrics());

                ConstraintLayout.LayoutParams long_input= (ConstraintLayout.LayoutParams) input.getLayoutParams();
                long_input.rightMargin = width68inDp;
                input.setLayoutParams(long_input);

                ListView listView = findViewById(R.id.list_of_professions);
                listView.setAdapter(adapterProfs);
                flag = true;
                professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_color_10));
                doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));

                int margin112inDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 112, getResources().getDisplayMetrics());
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                        (ConstraintLayout.LayoutParams.MATCH_PARENT, margin112inDp);
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                top.setLayoutParams(layoutParams);
                filter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filter));

                back.setVisibility(View.INVISIBLE);
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

        professions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_color_10));
                doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
                listView.setAdapter(adapterProfs);
                flag = true;
                input.setHint("Поиск по специальностям");
            }
        });

        doctors_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtredDoctors.clear();
                doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_color_10));
                professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));

                listView.setAdapter(adapterDoctor);
                flag = false;
                input.setHint("Поиск среди врачей");
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2++;
                if (count2%2==1) {
                    int margin164inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 164, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin164inDp);
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    top.setLayoutParams(layoutParams);
                    filter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filter_used));
                }
                else{
                    int margin112inDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 112, getResources().getDisplayMetrics());
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, margin112inDp);
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    top.setLayoutParams(layoutParams);
                    filter.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filter));
                }
            }
        });

    }
    class DoctorAdapter extends ArrayAdapter<Doctor> {
        public DoctorAdapter(@NonNull Context context, int resource, @NonNull List<Doctor> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Doctor doctor = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.person, null);


            DoctorHolder holderName = new DoctorHolder();
            holderName.text = convertView.findViewById(R.id.user);
            holderName.text.setText(doctor.getName());

            DoctorHolder holderProf = new DoctorHolder();
            holderProf.text = convertView.findViewById(R.id.dopinfo_text);
            holderProf.text.setText(doctor.getProffession());

            convertView.setTag(holderProf);
            convertView.setTag(holderName);


            return convertView;
        }
    }
    class ProfAdapter extends ArrayAdapter<String> {

        public ProfAdapter(@NonNull Context context, int resource, @NonNull List<String> str) {
            super(context, resource, str);
        }

        @NonNull
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String string = getItem(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profession_card, null);

            DoctorHolder holder = new DoctorHolder();
            holder.text = convertView.findViewById(R.id.profession_text);
            holder.text.setText(string);

            convertView.setTag(holder);

            return convertView;
        }


    }
    private static class DoctorHolder {
        public TextView text;
    }

    public void onBackPressed(){
        if(flag==true){
            Intent intent = new Intent(getApplicationContext(), MainPage2.class);
            intent.putExtra("number", number);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }else {
            Intent intent1 = getIntent();
            Intent intent2 = new Intent(getApplicationContext(), DoctorsList.class);
            String number = (String) intent1.getExtras().get("number");
            intent2.putExtra("number", number);
            filter.setVisibility(View.VISIBLE);
            text.setText("Получить консультацию");

            int width68inDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 68, getResources().getDisplayMetrics());

            ListView listView = findViewById(R.id.list_of_professions);
            ProfAdapter adapterProfs = new ProfAdapter(this, R.layout.profession_card, proffessions_array);

            ConstraintLayout.LayoutParams long_input= (ConstraintLayout.LayoutParams) input.getLayoutParams();
            long_input.rightMargin = width68inDp;
            input.setLayoutParams(long_input);

            listView.setAdapter(adapterProfs);
            flag = true;
            professions.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back_color_10));
            doctors_tv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.flow_shape_white));
            /*Intent intent = new Intent(getApplicationContext(), MainPage2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);*/
        }
    }

}


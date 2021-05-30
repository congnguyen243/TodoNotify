package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.notify.models.Noty;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    private EditText title,content,date,id;
    private Button btPick,btUpdate,btDelete, btCancel;
    private SQLiteNote notySQLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        notySQLite = new SQLiteNote(this);
        Intent intent = getIntent();
        Noty noty = (Noty) intent.getSerializableExtra("note");
        title.setText(noty.getTitle());
        content.setText(noty.getContent());
        date.setText(noty.getDate());
        try{
            id.setText(noty.getId());
        }
        catch (Exception e){
            System.out.println(e);
        }
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Noty n = new Noty(noty.getId(),
                        title.getText().toString(),
                        content.getText().toString(),
                        date.getText().toString());
                notySQLite.updateNote(n);
                Toast.makeText(getApplicationContext(),"updated success",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notySQLite.deleteById(noty.getId());
                Toast.makeText(getApplicationContext(),"deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hh = c.get(Calendar.HOUR);
                int mm = c.get(Calendar.MINUTE);
                int ss = c.get(Calendar.SECOND);
                TimePickerDialog dialog = new TimePickerDialog(DetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.setText(hourOfDay+":"+minute);
                    }
                },hh,mm,true);
                dialog.show();
            }
        });

    }

    private void initView() {
        id = findViewById(R.id.dId);
        title = findViewById(R.id.dTitle);
        content = findViewById(R.id.dContent);
        date = findViewById(R.id.dTime);
        btPick = findViewById(R.id.dPickTime);
        btUpdate = findViewById(R.id.dUpdate);
        btDelete = findViewById(R.id.dDelete);
        btCancel = findViewById(R.id.dBack);

    }
}
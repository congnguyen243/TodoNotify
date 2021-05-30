package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.notify.models.Noty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_1_ID = "channel 1";
    
    private RecyclerView rev;
    private FloatingActionButton fab;
    private RevAdapter revAdapter;
    //private NotySQLite notySQLite;
    private SQLiteNote sqLiteNote;
    private List<Noty> list;

    //alarm
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        createNotificationChannel();
        revAdapter = new RevAdapter(this);
        //notySQLite = new NotySQLite(this);
        //list = notySQLite.getAll();
        sqLiteNote = new SQLiteNote(this);
        list = sqLiteNote.getAll();
        for (Noty i:list){
            System.out.println("---id:"+i.getId());
        }
        initView();
        //list = new ArrayList<>();
        //setdatalistdemo
        //list.add(new Noty(1,"Title","content","date"));
        revAdapter.setData(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rev.setLayoutManager(layoutManager);
        rev.setAdapter(revAdapter);

        //fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID,"channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("this is channel 1");

            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void initView() {
        rev = findViewById(R.id.rev);
        fab = findViewById(R.id.fab);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list = sqLiteNote.getAll();
        revAdapter.setData(list);
        rev.setAdapter(revAdapter);
    }
}
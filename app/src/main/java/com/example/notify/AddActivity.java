package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.notify.models.Noty;

import java.sql.Time;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText title,content,date;
    private Button btPick,btAdd,btCancel;
    //private NotySQLite notySQLite;
    private SQLiteNote sqLiteNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        //notySQLite = new NotySQLite(this);
        sqLiteNote = new SQLiteNote(this);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sqLiteNote.addNote(
                            new Noty(
                                    title.getText().toString(),
                                    content.getText().toString(),
                                    date.getText().toString()));

                    String time_date = date.getText().toString();
                    String [] time_spilt=time_date.split("/");
                    int dd = Integer.parseInt(time_spilt[0]);
                    int mm = Integer.parseInt(time_spilt[1])-1;
                    int yy = Integer.parseInt(time_spilt[2]);
                    System.out.println("timepicker "+ dd+"/"+mm+"/"+yy);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(yy,mm,dd);
                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(AddActivity.this,MyReceiver.class);

                    StateManager.NotificationData notificationData = StateManager.getInstance().notificationData;
                    notificationData.title = title.getText().toString();
                    notificationData.content = content.getText().toString();
                    intent.putExtra("myAction","mDoNotify");
                    intent.putExtra("Title",title.getText().toString());
                    intent.putExtra("Description",content.getText().toString());
                    //System.out.println(content.getText().toString());
                    Log.e("des truoc receiver",intent.getStringExtra("Description"));
                    //pending intent de goi myreciever chay ngam
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this,0,intent,0);//dat thong bao
                    am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    Toast.makeText(getApplicationContext(),"added success",Toast.LENGTH_SHORT).show();
                    finish();
                }
                catch (Exception e){

                }
            }
        });
        btPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int yy = c.get(Calendar.YEAR);
                int mm = c.get(Calendar.MONTH);
                int dd = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },yy,mm,dd);
                datePickerDialog.show();
            }
        });
    }

    private void initView() {
        title = findViewById(R.id.aTitle);
        content = findViewById(R.id.aContent);
        date = findViewById(R.id.aTime);
        btPick = findViewById(R.id.aPickTime);
        btAdd = findViewById(R.id.aAdd);
        btCancel = findViewById(R.id.aBack);
    }
}
package com.example.notify;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notify.models.Noty;

import java.util.ArrayList;
import java.util.List;

public class SQLiteNote extends SQLiteOpenHelper {
    private static final String DB_Name = "noteDB.db";
    private static final int DB_Version = 1;

    public SQLiteNote(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao bang
        String sqlCreateDatabase="CREATE TABLE notes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "date TEXT)";
        db.execSQL(sqlCreateDatabase);
    }
    //truy van khong tra kq
    //add

    public void addNote(Noty c){
        String sql = "INSERT INTO notes(title,content,date)"+
                "VALUES(?,?,?)";
        String[] args = {c.getTitle(),c.getContent(),c.getDate()};
        SQLiteDatabase stm = getWritableDatabase();
        stm.execSQL(sql,args);
    }
    public void updateNote(Noty c) {
        String sql = "UPDATE notes SET title = ?, content = ?,date=? WHERE id = ?";
        String[] args = {c.getTitle(),c.getContent(),c.getDate(),String.valueOf(c.getId())};
        SQLiteDatabase stm = getWritableDatabase();
        stm.execSQL(sql,args);
    }
    public void deleteById(int id){
        String sql = "DELETE FROM notes WHERE id = ?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase stm = getWritableDatabase();
        stm.execSQL(sql, args);
    }
    //truy van tra qk
    //getAll
    public List<Noty> getAll(){
        List<Noty> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("notes", null, null, null, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));

            list.add(new Noty(id,title,content,date));
        }

        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

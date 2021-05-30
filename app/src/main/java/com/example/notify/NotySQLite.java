package com.example.notify;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notify.models.Noty;

import java.util.ArrayList;
import java.util.List;

public class NotySQLite extends SQLiteOpenHelper {
    private static final String DB_NAME = "Note.db";
    private static final int DB_VERSION = 2;
    private static int id =1;
    public NotySQLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create db
        String sql = "CREATE TABLE notes("
                + "id INTEGER,"
                + "title TEXT,"
                + "content TEXT,"
                + "date TEXT)";
        db.execSQL(sql);
    }
    //truy van khong tra kq (add,update,delete,)
    public void addNote(Noty noty){
        id++;
        String sql = "INSERT INTO notes(id,title,content,date)"
                + "VALUES(?,?,?,?)";
        String[] args = {String.valueOf(id),noty.getTitle(),noty.getContent(),noty.getDate()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        System.out.println("added note success");
        sqLiteDatabase.execSQL(sql,args);
    }
    public void updateNote(Noty noty){
        String sql = "UPDATE notes SET title = ?,content = ?, date = ? WHERE id = ?";
        String[] args = {noty.getTitle(),noty.getContent(),noty.getDate(),String.valueOf(noty.getId())};
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        System.out.println("updated note id: "+ noty.getId());
        sqLiteDatabase.execSQL(sql,args);
    }
    public void deleteNote(int id){
        String sql = "DELETE FROM notes WHERE id = ?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        System.out.println("deleted note id = "+id);
        sqLiteDatabase.execSQL(sql,args);
    }
    public void deleteAllNote(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("notes", null, null);
    }

    //truy van tra kq(getAll, getByName)
    public List<Noty> getAll(){
        List<Noty> ls = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor= sqLiteDatabase.query("notes",null,null,null,null,null,null);
        while(cursor!=null && cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            ls.add(new Noty(title,content,date));
        }
        return ls;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

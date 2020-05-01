package com.tilda.sozluk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.StringSearch;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class Veritabani extends SQLiteOpenHelper {

    public Veritabani(@Nullable Context context) {
        super(context, "sozluk.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS kelime(id INTEGER PRIMARY KEY AUTOINCREMENT, turkce TEXT, ingilizce TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS kelime");
        onCreate(db);
    }

    public long kelimeEkle(Kelime k){
        ContentValues icerik = new ContentValues();
        icerik.put("turkce", k.getTurkce());
        icerik.put("ingilizce", k.getIngilizce());

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.insert("kelime", null, icerik);db.update("kelime", icerik, "id=" + k.getId(), null);
        return cevap;
    }

    public ArrayList<Kelime> listele(){
        ArrayList<Kelime> kelimeArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, turkce, ingilizce FROM kelime", null);
        if(c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex("id"));
                String t = c.getString(c.getColumnIndex("turkce"));
                String i = c.getString(c.getColumnIndex("ingilizce"));
                kelimeArrayList.add(new Kelime(id,t,i));
            }while(c.moveToNext());
        }
        c.close();
        return kelimeArrayList;
    }

    public ArrayList<Kelime> kelimeAra(String aranan){

        String[] args = {aranan+"%" , aranan+"%"};

        ArrayList<Kelime> kelimeArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, turkce, ingilizce FROM kelime WHERE turkce LIKE ? or ingilizce LIKE ? ", args);
        if(c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex("id"));
                String t = c.getString(c.getColumnIndex("turkce"));
                String i = c.getString(c.getColumnIndex("ingilizce"));
                kelimeArrayList.add(new Kelime(id,t,i));
            }while(c.moveToNext());
        }
        c.close();
        return kelimeArrayList;
    }

    public long kelimeDuzelt(Kelime k){

        ContentValues icerik = new ContentValues();
        icerik.put("turkce", k.getTurkce());
        icerik.put("ingilizce", k.getIngilizce());

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.update("kelime", icerik, "id=" + k.getId(), null);
        return cevap;
    }

    public long kelimeSil(int silinecek_id){
        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.delete("kelime", "id=" + silinecek_id, null);
        return cevap;
    }

}

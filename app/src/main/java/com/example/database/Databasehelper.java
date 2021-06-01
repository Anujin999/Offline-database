package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {

    private static final String database_name = "student.db";
    private static final String table_name = "student";
    private static final String col_id = "ID";
    private static final String col_name = "NAME";
    private static final String col_age = "AGE";


    public Databasehelper( Context context) {
        super(context, database_name, null, 1);

        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ table_name + " ("
                +col_id+"INTEGER PRIMARY KEY AUTOINCREMENT, "
                +col_name+ " TEXT, "
                +col_age+ "INTEGER"
                +")"

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);

    }

    boolean insertData (String name, String age ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_name, name);
        values.put(col_age, age);
        long result = db.insert(table_name, null, values);
        return result != -1;
    }
    Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM" + table_name, null);
        return res;
    }

    boolean updateData (String id, String name, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_id, id);
        values.put(col_name, name);
        values.put(col_age, age);
        long result = db.update(table_name, values, "id = ?", new String[]{id});
        return result != -1;

    }

    Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, "id = ?", new String[]{id});
    }


}

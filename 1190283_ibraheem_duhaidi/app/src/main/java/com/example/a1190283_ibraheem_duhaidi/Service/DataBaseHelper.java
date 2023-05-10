package com.example.a1190283_ibraheem_duhaidi.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.a1190283_ibraheem_duhaidi.Entity.UserScore;
import java.util.UUID;

public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER_SCORES(ID TEXT PRIMARY KEY,NAME TEXT, SCORE LONG)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertScore(UserScore userScore) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", UUID.randomUUID().toString());
        contentValues.put("NAME", userScore.getName());
        contentValues.put("SCORE", userScore.getScore());
        sqLiteDatabase.insert("USER_SCORES", null, contentValues);
    }

    public Cursor getTopFivePlayers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT NAME, SCORE FROM USER_SCORES ORDER BY SCORE DESC LIMIT 5", null);
    }
}

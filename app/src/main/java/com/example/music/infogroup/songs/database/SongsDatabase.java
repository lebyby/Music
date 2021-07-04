package com.example.music.infogroup.songs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.music.Groups;
import com.example.music.infogroup.songs.Songs;

import java.util.ArrayList;


public class SongsDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String DATABASE_NAME = "song";
    private	static final String TABLE_SONGS = "songs";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GROUP_ID = "g_id";
    private static final String COLUMN_NAME = "songname";
    private static final String COLUMN_LENGTH = "length";


    public SongsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONGS_TABLE = "CREATE	TABLE " + TABLE_SONGS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_GROUP_ID + " INTEGER," + COLUMN_NAME + " TEXT," + COLUMN_LENGTH + " INTEGER" + ")";
        db.execSQL(CREATE_SONGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }

    public ArrayList<Songs> listSongs(){
        String sql = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Songs> storeSongs = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int g_id = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                String length = cursor.getString(3);
                storeSongs.add(new Songs(id, g_id, name, length));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeSongs;
    }

    public void addSongs(Songs songs){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, songs.getName());
        values.put(COLUMN_LENGTH, songs.getLength());
        values.put(COLUMN_GROUP_ID, songs.getG_Id());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SONGS, null, values);
    }

    public void updateSongs(Songs songs){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, songs.getName());
        values.put(COLUMN_LENGTH, songs.getLength());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_SONGS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(songs.getId())});
    }


    public ArrayList<Songs> listSongsGroup(int group_id){
        String sql = "SELECT * FROM "	+ TABLE_SONGS + " WHERE " + COLUMN_GROUP_ID + " = " + group_id;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Songs> storeSongs = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int g_id = Integer.parseInt(cursor.getString(1));
                String songsName = cursor.getString(2);
                String songsLength = cursor.getString(3);

                storeSongs.add(new Songs(id, g_id, songsName, songsLength));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeSongs;
    }







    public void deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONGS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}

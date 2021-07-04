package com.example.music.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.example.music.Groups;


public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String DATABASE_NAME = "group";
    private	static final String TABLE_GROUPS = "groups";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "groupname";
    private static final String COLUMN_PLACE = "place";
    private static final String COLUMN_YEAR = "year";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROUPS_TABLE = "CREATE	TABLE " + TABLE_GROUPS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_PLACE + " INTEGER," + COLUMN_YEAR + " INTEGER" +")";
        db.execSQL(CREATE_GROUPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        onCreate(db);
    }

    public ArrayList<Groups> listGroups(){
        String sql = "SELECT * FROM " + TABLE_GROUPS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Groups> storeGroups = new ArrayList<>();
        Cursor cursor = db.query(TABLE_GROUPS, null, null, null, null, null, COLUMN_PLACE+" ASC");
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String place = cursor.getString(2);
                String year = cursor.getString(3);
                storeGroups.add(new Groups(id, name, place, year));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeGroups;
    }

    public void addGroups(Groups groups){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, groups.getName());
        values.put(COLUMN_PLACE, groups.getPlace());
        values.put(COLUMN_YEAR, groups.getYear());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_GROUPS, null, values);
    }

    public void updateGroups(Groups groups){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, groups.getName());
        values.put(COLUMN_PLACE, groups.getPlace());
        values.put(COLUMN_YEAR, groups.getYear());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_GROUPS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(groups.getId())});
    }

    public Groups findGroups(String name){
        String query = "Select * FROM "	+ TABLE_GROUPS + " WHERE " + COLUMN_NAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Groups groups = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String groupsName = cursor.getString(1);
            String groupsPlace = cursor.getString(2);
            String groupsYear = cursor.getString(3);
            groups = new Groups(id, groupsName, groupsPlace, groupsYear);
        }
        cursor.close();
        return groups;
    }

    public void deleteGroup(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}

package com.example.music.infogroup.composition.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.music.infogroup.composition.Composition;

import java.util.ArrayList;


public class CompositionDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String DATABASE_NAME = "composition";
    private	static final String TABLE_COMPOSITION = "composition";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GROUP_ID = "g_id";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_FORENAME = "forename";
    private static final String COLUMN_ROLE = "role";


    public CompositionDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COMPOSITION_TABLE = "CREATE	TABLE " + TABLE_COMPOSITION + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_GROUP_ID + " INTEGER," + COLUMN_SURNAME + " TEXT," + COLUMN_FORENAME + " TEXT," + COLUMN_ROLE +" TEXT" + ")";
        db.execSQL(CREATE_COMPOSITION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPOSITION);
        onCreate(db);
    }

    public ArrayList<Composition> listComposition(){
        String sql = "SELECT * FROM " + TABLE_COMPOSITION;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Composition> storeComposition = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int g_id = Integer.parseInt(cursor.getString(1));
                String sname = cursor.getString(2);
                String fname = cursor.getString(3);
                String role = cursor.getString(4);
                storeComposition.add(new Composition(id, g_id, sname, fname, role));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeComposition;
    }

    public void addComposition(Composition composition){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SURNAME, composition.getSurname());
        values.put(COLUMN_FORENAME, composition.getForename());
        values.put(COLUMN_ROLE, composition.getRole());
        values.put(COLUMN_GROUP_ID, composition.getG_Id());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_COMPOSITION, null, values);
    }

    public void updateComposition(Composition composition){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SURNAME, composition.getSurname());
        values.put(COLUMN_FORENAME, composition.getForename());
        values.put(COLUMN_ROLE, composition.getRole());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_COMPOSITION, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(composition.getId())});
    }


    public ArrayList<Composition> listCompositionGroup(int group_id){
        String sql = "SELECT * FROM "	+ TABLE_COMPOSITION + " WHERE " + COLUMN_GROUP_ID + " = " + group_id;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Composition> storeComposition = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int g_id = Integer.parseInt(cursor.getString(1));
                String compositionSurname = cursor.getString(2);
                String compositionForename = cursor.getString(3);
                String compositionRole = cursor.getString(4);

                storeComposition.add(new Composition(id, g_id, compositionSurname, compositionForename, compositionRole));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeComposition;
    }

    public void deleteComposition(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPOSITION, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}

package com.example.music.infogroup.report.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.music.Groups;
import com.example.music.infogroup.report.Report;

import java.util.ArrayList;


public class ReportDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String DATABASE_NAME = "report";
    private	static final String TABLE_REPORT = "report";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GROUP_ID = "g_id";
    private static final String COLUMN_CITY = "cityname";
    private static final String COLUMN_COUNT = "count";


    public ReportDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REPORT_TABLE = "CREATE	TABLE " + TABLE_REPORT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_GROUP_ID + " INTEGER," + COLUMN_CITY + " TEXT," + COLUMN_COUNT + " INTEGER" + ")";
        db.execSQL(CREATE_REPORT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        onCreate(db);
    }

    public ArrayList<Report> listReport(){
        String sql = "SELECT * FROM " + TABLE_REPORT;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Report> storeReport = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int g_id = Integer.parseInt(cursor.getString(1));
                String city = cursor.getString(2);
                String count = cursor.getString(3);
                storeReport.add(new Report(id, g_id, city, count));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeReport;
    }

    public void addReport(Report report){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, report.getCity());
        values.put(COLUMN_COUNT, report.getCount());
        values.put(COLUMN_GROUP_ID, report.getG_Id());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_REPORT, null, values);
    }

    public void updateReport(Report report){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, report.getCity());
        values.put(COLUMN_COUNT, report.getCount());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_REPORT, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(report.getId())});
    }


    public ArrayList<Report> listReportGroup(int group_id){
        String sql = "SELECT * FROM "	+ TABLE_REPORT + " WHERE " + COLUMN_GROUP_ID + " = " + group_id;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Report> storeReport = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int g_id = Integer.parseInt(cursor.getString(1));
                String reportCity = cursor.getString(2);
                String reportCount = cursor.getString(3);

                storeReport.add(new Report(id, g_id, reportCity, reportCount));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeReport;
    }

    public void deleteReport(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}

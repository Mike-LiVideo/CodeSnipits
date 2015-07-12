package com.dataticket.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;

import com.dataticket.db.tables.RfidPermitTable;
import com.dataticket.util.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by michael.wheeler on 6/25/2015.
 */
public class RfidPermitDatabaseHelper
        extends BaseHelper{

    private static final String DATABASE_NAME = "rfid_permit.db";
    private static final int VERSION = 1;

    public RfidPermitDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        RfidPermitTable mRfidPermitTable = new RfidPermitTable();
        gIsNew = true;
        db.execSQL(mRfidPermitTable.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        RfidPermitTable mRfidPermitTable = new RfidPermitTable();
        gIsNew = true;
        db.execSQL(mRfidPermitTable.DELETE_TABLE);
        db.execSQL(mRfidPermitTable.CREATE_TABLE);
    }

    public void fillDatabase(){
        RfidPermitTable mRfidPermitTable = new RfidPermitTable();
        long start = System.currentTimeMillis();
        Log.i("MIKE", "Started loading " + Files.PLATE_INFO);
        File file = new File(Environment.getExternalStorageDirectory() + Files.RFID_PERMITS);
        SQLiteDatabase mSQLiteDatabase = this.getWritableDatabase();
        String mSQLiteString = CS_INSERT + mRfidPermitTable.TABLE_NAME + CS_OPEN_PARENTHESIS
                + mRfidPermitTable.COLUMN_NAME_ONE + CS_COMMA
                + mRfidPermitTable.COLUMN_NAME_TWO + CS_COMMA
                + mRfidPermitTable.COLUMN_NAME_THREE + CS_COMMA
                + mRfidPermitTable.COLUMN_NAME_FOUR + CS_COMMA
                + mRfidPermitTable.COLUMN_NAME_FIVE + CS_CLOSE_PARENTHESIS
                + CS_VALUES_FIVE;

        String[] mEntry;
        BufferedReader mBufferedReader = null;
        try{
            if(!this.gIsNew){
                mSQLiteDatabase.delete(mRfidPermitTable.TABLE_NAME, null, null);
            }
            String mCurrentLine;
            mBufferedReader = new BufferedReader(new FileReader(file));
            mSQLiteDatabase.beginTransactionNonExclusive();
            SQLiteStatement mSQLiteStatement = mSQLiteDatabase.compileStatement(mSQLiteString);
            while((mCurrentLine = mBufferedReader.readLine()) != null){
                mEntry = mCurrentLine.split("\t", -1);
                mSQLiteStatement.bindString(1, mEntry[0]);
                mSQLiteStatement.bindString(2, mEntry[1]);
                mSQLiteStatement.bindString(3, mEntry[2]);
                mSQLiteStatement.bindString(4, mEntry[3]);
                mSQLiteStatement.bindString(5, mEntry[4]);

                mSQLiteStatement.execute();
                mSQLiteStatement.clearBindings();
            }
            mSQLiteDatabase.setTransactionSuccessful();
            mSQLiteDatabase.endTransaction();
            mSQLiteDatabase.close();

        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try{
                if(mBufferedReader != null){
                    mBufferedReader.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }

        }
        long end = System.currentTimeMillis();

        double time = (end - start) / 1000.00;
        Log.i("MIKE", "Total time = " + time + "sec For " + Files.PLATE_INFO);
    }

    public Cursor getRfidPermitCursor(SQLiteDatabase db, String tagId){
        RfidPermitTable mRfidPermitTable = new RfidPermitTable();
        return db.query(mRfidPermitTable.TABLE_NAME, mRfidPermitTable.PROJECTION, mRfidPermitTable.WHERE_CLAUSE, new String[]{tagId}, null, null, null);
    }

}

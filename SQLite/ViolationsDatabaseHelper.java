import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;

import tables.ViolationsTables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by michael.wheeler on 4/10/2015.
 */
public class ViolationsDatabaseHelper
        extends BaseHelper{

    private static final String DATABASE_NAME = "violation.db";
    private static final int VERSION = 2;
    private String tableNameStub = "T_Violations";
    private ArrayList<File> violationFilesArrayLists = new ArrayList<File>();
    private ArrayList<String> violationTableNames = new ArrayList<String>();

    public ViolationsDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        gIsNew = true;
        fillViolationArrays();
        ViolationsTables mViolationsTables = new ViolationsTables();
        for(int i = 0; i < violationTableNames.size(); i++){
            db.execSQL(mViolationsTables.CREATE_TABLE + violationTableNames.get(i) + mViolationsTables.getCreateTableStatement());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        ViolationsTables mViolationsTables = new ViolationsTables();
        db.execSQL(mViolationsTables.DELETE_TABLE);
        fillViolationArrays();
        for(int i = 0; i < violationTableNames.size(); i++){
            db.execSQL(mViolationsTables.CREATE_TABLE + violationTableNames.get(i) + mViolationsTables.getCreateTableStatement());
        }
    }

    public void fillViolationArrays(){
        File mFile;
        try{
            for(int i = 1; i <= 11; i++){
                mFile = new File(Environment.getExternalStorageDirectory() + Files.getViolationFileName(i));

                if(mFile.exists()){
                    violationFilesArrayLists.add(mFile);
                    violationTableNames.add(tableNameStub + i);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void fillTables(){
        long start = System.currentTimeMillis();
        Log.i("MIKE", "Started loading Violations");
        ViolationsTables mViolationsTables = new ViolationsTables();
        SQLiteDatabase mSQLiteDatabase = this.getWritableDatabase();
        for(int i = 0; i < violationTableNames.size(); i++){

            String mSQLiteString = CS_INSERT + violationTableNames.get(i) + mViolationsTables.getInsertIntoTableStatement();
            SQLiteStatement mSQLiteStatement = mSQLiteDatabase.compileStatement(mSQLiteString);
            String[] mEntry;
            BufferedReader mBufferedReader = null;
            try{
//                if(!this.gIsNew){
//                    mSQLiteDatabase.delete(violationTableNames.get(i), null, null);
//                }
                String mCurrentLine;
                mBufferedReader = new BufferedReader(new FileReader(violationFilesArrayLists.get(i)));
                mSQLiteDatabase.beginTransactionNonExclusive();
                while((mCurrentLine = mBufferedReader.readLine()) != null){
                    mEntry = mCurrentLine.split("\t", -1);
                    mSQLiteStatement.bindString(1, mEntry[0]);
                    mSQLiteStatement.bindString(2, mEntry[1]);
                    mSQLiteStatement.bindString(3, mEntry[2]);
                    mSQLiteStatement.bindString(4, mEntry[3]);

                    mSQLiteStatement.execute();
                    mSQLiteStatement.clearBindings();
                }
                mSQLiteDatabase.setTransactionSuccessful();
                mSQLiteDatabase.endTransaction();
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
        }
        mSQLiteDatabase.close();
        long end = System.currentTimeMillis();
        double time = (end - start);
        Log.i("MIKE", "Total time = " + time + "ms For violations");
    }

    public String getViolationCodeFromDescription(SQLiteDatabase db, String description, String department){
        String code;
        ViolationsTables mViolationsTables = new ViolationsTables();
        Log.i("MIKE", department + "  " + description);

        Cursor c = db.query(tableNameStub + 1, mViolationsTables.PROJECTION, mViolationsTables.WHERE_CLAUSE, new String[]{description}, null, null, null);
        if(c.moveToFirst()){
            code = c.getString(c.getColumnIndex(mViolationsTables.getColumnName(4)));
        }
        else{
            code = null;
        }
        return code;
    }
}

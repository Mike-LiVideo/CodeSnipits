import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;

import tables.RepeatOffenderTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by michael.wheeler on 4/1/2015.
 */
public class RepeatOffendersDatabaseHelper
        extends BaseHelper{

    private static final String DATABASE_NAME = "repeat_offenders.db";
    private static final int VERSION = 2;

    public RepeatOffendersDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        RepeatOffenderTable mRepeatOffenderTable = new RepeatOffenderTable();
        gIsNew = true;
        db.execSQL(mRepeatOffenderTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        RepeatOffenderTable mRepeatOffenderTable = new RepeatOffenderTable();
        gIsNew = true;
        db.execSQL(mRepeatOffenderTable.DELETE_TABLE);
        db.execSQL(mRepeatOffenderTable.CREATE_TABLE);
    }

    /**
     * @param db        Database where the Query is run from
     * @param state     State the License Plate is Issued from
     * @param plate     License Plate number
     * @param violation Violation code
     * @return Amount of warnings this plate has for this violation
     */
    public int getWarningsCount(SQLiteDatabase db, String state, String plate, String violation){
        int amount;
        RepeatOffenderTable mRepeatOffenderTable = new RepeatOffenderTable();

        Log.i("MIKE", "state = " + state + " plate = " + plate + " violation = " + violation);
        Cursor c = db.query(mRepeatOffenderTable.TABLE_NAME, mRepeatOffenderTable.PROJECTION, mRepeatOffenderTable.SPECIFIC_VIOLATION_WHERE_CLAUSE, new String[]{state, plate, violation, "True"}, null, null, null);
        if(c.moveToFirst()){
            amount = c.getInt(c.getColumnIndex("amount"));
        }
        else{
            amount = 0;
        }
        return amount;
    }

    /**
     * @param db        Database where the Query is run from
     * @param state     State the License Plate is Issued from
     * @param plate     License Plate number
     * @param violation Violation code
     * @return Amount of citations this plate has for this violation
     */
    public int getCitationsCount(SQLiteDatabase db, String state, String plate, String violation){
        int amount;
        RepeatOffenderTable mRepeatOffenderTable = new RepeatOffenderTable();
        Cursor c = db.query(mRepeatOffenderTable.TABLE_NAME, mRepeatOffenderTable.PROJECTION, mRepeatOffenderTable.SPECIFIC_VIOLATION_WHERE_CLAUSE, new String[]{state, plate, violation, "False"}, null, null, null);
        if(c.moveToFirst()){
            amount = c.getInt(c.getColumnIndex(mRepeatOffenderTable.COLUMN_NAME_FOUR));
        }
        else{
            amount = 0;
        }
        return amount;
    }

    public int getViolationsCount(SQLiteDatabase db, String state, String plate){
        RepeatOffenderTable mRepeatOffenderTable = new RepeatOffenderTable();
        Cursor c = db.query(true, mRepeatOffenderTable.TABLE_NAME, mRepeatOffenderTable.PROJECTION, mRepeatOffenderTable.ALL_VIOLATIONS_WHERE_CLAUSE, new String[]{state, plate},mRepeatOffenderTable.COLUMN_NAME_THREE ,null, null, null);
        return c.getCount();
    }

    public Cursor getViolationsCursor(SQLiteDatabase db, String state, String plate){
        RepeatOffenderTable mRepeatOffenderTable = new RepeatOffenderTable();
        return db.query(true, mRepeatOffenderTable.TABLE_NAME, new String[] {mRepeatOffenderTable.COLUMN_NAME_THREE}, mRepeatOffenderTable.ALL_VIOLATIONS_WHERE_CLAUSE, new String[]{state, plate},mRepeatOffenderTable.COLUMN_NAME_THREE ,null, null, null);
    }

    public void fillDatabase(){
        RepeatOffenderTable mRepeatOffenderTable = new RepeatOffenderTable();
        long start = System.currentTimeMillis();
        File file = new File(Environment.getExternalStorageDirectory() +"RepeatOffenders.txt");
        SQLiteDatabase mSQLiteDatabase = this.getWritableDatabase();
        String mSQLiteString = CS_INSERT + mRepeatOffenderTable.TABLE_NAME + CS_OPEN_PARENTHESIS
                + mRepeatOffenderTable.COLUMN_NAME_ONE + CS_COMMA
                + mRepeatOffenderTable.COLUMN_NAME_TWO + CS_COMMA
                + mRepeatOffenderTable.COLUMN_NAME_THREE + CS_COMMA
                + mRepeatOffenderTable.COLUMN_NAME_FOUR + CS_COMMA
                + mRepeatOffenderTable.COLUMN_NAME_FIVE + CS_CLOSE_PARENTHESIS
                + CS_VALUES_FIVE;

        String[] mEntry;
        BufferedReader mBufferedReader = null;
        try{
            if(!this.gIsNew){
                mSQLiteDatabase.delete(mRepeatOffenderTable.TABLE_NAME, null, null);
            }
            String mCurrentLine;
            mBufferedReader = new BufferedReader(new FileReader(file));
            mSQLiteDatabase.beginTransactionNonExclusive();
            SQLiteStatement mSQLiteStatement = mSQLiteDatabase.compileStatement(mSQLiteString);
            while((mCurrentLine = mBufferedReader.readLine()) != null){
                mEntry = mCurrentLine.split("\t");
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
    }
}

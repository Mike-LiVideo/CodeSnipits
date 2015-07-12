import android.provider.BaseColumns;

import SQLiteStatementsInterface;

import java.util.ArrayList;

/**
 * Created by michael.wheeler on 4/2/2015.
 */
public abstract class SQLiteBaseTable
        implements BaseColumns, SQLiteStatementsInterface{

    public final String TABLE_NAME = getTableName();
    public final String COLUMN_NAME_ONE = getColumnNameOne();
    public final String COLUMN_NAME_TWO = getColumnNameTwo();
    public final String COLUMN_NAME_THREE = getColumnNameThree();
    public final String COLUMN_NAME_FOUR = getColumnNameFour();
    public final String COLUMN_NAME_FIVE = getColumnNameFive();
    public final String COLUMN_NAME_SIX = getColumnNameSix();
    public final String COLUMN_NAME_SEVEN = getColumnNameSeven();
    public final String COLUMN_NAME_EIGHT = getColumnNameEight();
    public final int NUMBER_OF_COLUMNS = numberOfUsedColumns();
    protected ArrayList<String> projectionList = fillList();
    public String[] PROJECTION = projectionList.toArray(new String[projectionList.size()]);

    public String DELETE_TABLE = CS_DELETE + TABLE_NAME + "; ";

    abstract String getTableName();

    abstract String getColumnNameOne();

    abstract String getColumnNameTwo();

    abstract String getColumnNameThree();

    abstract String getColumnNameFour();

    abstract String getColumnNameFive();

    abstract String getColumnNameSix();

    abstract String getColumnNameSeven();

    abstract String getColumnNameEight();

    abstract int numberOfUsedColumns();

    public String getColumnName(int integer){
        switch(integer){
            case 0:
                return COLUMN_NAME_ONE;
            case 1:
                return COLUMN_NAME_TWO;
            case 2:
                return COLUMN_NAME_THREE;
            case 3:
                return COLUMN_NAME_FOUR;
            case 4:
                return COLUMN_NAME_FIVE;
            case 5:
                return COLUMN_NAME_SIX;
            case 6:
                return COLUMN_NAME_SEVEN;
            case 7:
                return COLUMN_NAME_EIGHT;
            default:
                return null;
        }
    }

    private ArrayList<String> fillList(){
        ArrayList<String> mStringArrayList = new ArrayList<String>();
        for(int i = 0; i < numberOfUsedColumns(); i++){
            mStringArrayList.add(getColumnName(i));
        }

        return mStringArrayList;
    }

}

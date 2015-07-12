package com.dataticket.db.tables;

/**
 * Created by michael.wheeler on 4/1/2015.
 */
public class RfidPermitTable
        extends SQLiteBaseTable{

    public final String CREATE_TABLE = CS_CREATE_TABLE + TABLE_NAME + CS_OPEN_PARENTHESIS
            + COLUMN_NAME_ONE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_TWO + CS_TEXT
            + CS_COMMA + COLUMN_NAME_THREE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_FOUR + CS_TEXT
            + CS_COMMA + COLUMN_NAME_FIVE + CS_TEXT + CS_END_TABLE;

    public final String WHERE_CLAUSE = COLUMN_NAME_ONE + CS_WHERE_END;

    @Override
    String getTableName(){
        return "rfid_permit_table";
    }

    @Override
    String getColumnNameOne(){
        return "permit";
    }

    @Override
    String getColumnNameTwo(){
        return "type";
    }

    @Override
    String getColumnNameThree(){
        return "plate";
    }

    @Override
    String getColumnNameFour(){
        return "make";
    }

    @Override
    String getColumnNameFive(){
        return "year";
    }

    @Override
    String getColumnNameSix(){
        return null;
    }

    @Override
    String getColumnNameSeven(){
        return null;
    }

    @Override
    String getColumnNameEight(){
        return null;
    }

    @Override
    int numberOfUsedColumns(){
        return 6;
    }
}

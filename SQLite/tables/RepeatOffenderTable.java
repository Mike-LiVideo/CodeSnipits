package com.dataticket.db.tables;

/**
 * Created by michael.wheeler on 4/1/2015.
 */
public class RepeatOffenderTable
        extends SQLiteBaseTable{

    public final String CREATE_TABLE = CS_CREATE_TABLE + TABLE_NAME + CS_OPEN_PARENTHESIS
            + COLUMN_NAME_ONE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_TWO + CS_TEXT
            + CS_COMMA + COLUMN_NAME_THREE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_FOUR + CS_INTEGER
            + CS_COMMA + COLUMN_NAME_FIVE + CS_TEXT + CS_END_TABLE;
    public final String SPECIFIC_VIOLATION_WHERE_CLAUSE = COLUMN_NAME_ONE + CS_WHERE_MORE + COLUMN_NAME_TWO + CS_WHERE_MORE + COLUMN_NAME_THREE + CS_WHERE_MORE + COLUMN_NAME_FIVE + CS_WHERE_END;
    public final String ALL_VIOLATIONS_WHERE_CLAUSE = COLUMN_NAME_ONE + CS_WHERE_MORE + COLUMN_NAME_TWO + CS_WHERE_END;

    @Override
    String getTableName(){
        return "repeat_offender";
    }

    @Override
    String getColumnNameOne(){
        return "state";
    }

    @Override
    String getColumnNameTwo(){
        return "plate";
    }

    @Override
    String getColumnNameThree(){
        return "violation";
    }

    @Override
    String getColumnNameFour(){
        return "amount";
    }

    @Override
    String getColumnNameFive(){
        return "is_warning";
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
        return 5;
    }
}

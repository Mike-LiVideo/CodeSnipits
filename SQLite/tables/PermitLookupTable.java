/**
 * Created by michael.wheeler on 4/1/2015.
 */
public class PermitLookupTable
        extends SQLiteBaseTable{

    public final String CREATE_TABLE = CS_CREATE_TABLE + TABLE_NAME + CS_OPEN_PARENTHESIS
            + COLUMN_NAME_ONE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_TWO + CS_TEXT
            + CS_COMMA + COLUMN_NAME_THREE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_FOUR + CS_TEXT + CS_END_TABLE;

    public final String WHERE_CLAUSE = COLUMN_NAME_ONE + CS_WHERE_MORE + COLUMN_NAME_TWO + CS_WHERE_END;

    @Override
    String getTableName(){
        return "permit_lookup";
    }

    @Override
    String getColumnNameOne(){
        return "plate";
    }

    @Override
    String getColumnNameTwo(){
        return "state";
    }

    @Override
    String getColumnNameThree(){
        return "info_one";
    }

    @Override
    String getColumnNameFour(){
        return "info_two";
    }

    @Override
    String getColumnNameFive(){
        return null;
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
        return 4;
    }
}

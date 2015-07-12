/**
 * Created by michael.wheeler on 4/10/2015.
 */
public class ViolationsTables
        extends SQLiteBaseTable{

    public final String COLUMNS = CS_OPEN_PARENTHESIS
            + COLUMN_NAME_ONE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_TWO + CS_TEXT
            + CS_COMMA + COLUMN_NAME_THREE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_FOUR + CS_TEXT + CS_END_TABLE;
    public final String CREATE_TABLE = CS_CREATE_TABLE;

    public final String WHERE_CLAUSE = COLUMN_NAME_THREE + CS_WHERE_END;


    @Override
    String getTableName(){
        return null;
    }

    @Override
    String getColumnNameOne(){
        return "municipal";
    }

    @Override
    String getColumnNameTwo(){
        return "fine";
    }

    @Override
    String getColumnNameThree(){
        return "description";
    }

    @Override
    String getColumnNameFour(){
        return "code";
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

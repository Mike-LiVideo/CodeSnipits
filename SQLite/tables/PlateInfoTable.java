/**
 * Created by michael.wheeler on 4/1/2015.
 */
public class PlateInfoTable
        extends SQLiteBaseTable{

    public final String CREATE_TABLE = CS_CREATE_TABLE + TABLE_NAME + CS_OPEN_PARENTHESIS
            + COLUMN_NAME_ONE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_TWO + CS_TEXT
            + CS_COMMA + COLUMN_NAME_THREE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_FOUR + CS_TEXT
            + CS_COMMA + COLUMN_NAME_FIVE + CS_TEXT
            + CS_COMMA + COLUMN_NAME_SIX + CS_TEXT + CS_END_TABLE;

    public final String WHERE_CLAUSE = COLUMN_NAME_ONE + CS_WHERE_MORE + COLUMN_NAME_TWO + CS_WHERE_END;

    @Override
    String getTableName(){
        return "plate_info_table";
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
        return "vin";
    }

    @Override
    String getColumnNameFour(){
        return "make";
    }

    @Override
    String getColumnNameFive(){
        return "type";
    }

    @Override
    String getColumnNameSix(){
        return "primary_color";
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

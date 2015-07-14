import java.util.ArrayList;

/**
 * Created by michael.wheeler on 4/1/2015.
 */
public class RfidPermitTable
        extends SQLiteBaseTable{

    public final String CREATE_TABLE = CS_CREATE_TABLE + TABLE_NAME + CS_OPEN_PARENTHESIS
            + this.getColumnName(1) + CS_TEXT
            + CS_COMMA + this.getColumnName(2) + CS_TEXT
            + CS_COMMA + this.getColumnName(3) + CS_TEXT
            + CS_COMMA + this.getColumnName(4) + CS_TEXT
            + CS_COMMA + this.getColumnName(5) + CS_TEXT + CS_END_TABLE;

    public final String WHERE_CLAUSE = this.getColumnName(1) + CS_WHERE_END;

    @Override
    String getTableName(){
        return "rfid_permit_table";
    }

    @Override
    ArrayList<String> setColumnNames(){
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("permit");
        columns.add("type");
        columns.add("plate");
        columns.add("make");
        columns.add("year");
        return columns;
    }

    @Override
    int numberOfUsedColumns(){
        return this.TABLE_COLUMNS.size();
    }
}

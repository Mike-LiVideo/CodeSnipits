import java.util.ArrayList;

/**
 * Created by michael.wheeler on 4/10/2015.
 */
public class ViolationsTables
        extends SQLiteBaseTable{

    public final String CREATE_TABLE = CS_CREATE_TABLE;

    public final String WHERE_CLAUSE = this.getColumnName(3) + CS_WHERE_END;


    @Override
    String getTableName(){
        return null;
    }

    @Override
    ArrayList<String> setColumnNames(){
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("municipal");
        columns.add("fine");
        columns.add("description");
        columns.add("code");
        return columns;
    }

    @Override
    int numberOfUsedColumns(){
        return this.TABLE_COLUMNS.size();
    }

    @Override
    public String getCreateTableStatement(){
        StringBuilder sb = new StringBuilder();
        sb.append(CS_OPEN_PARENTHESIS);

        for (int i = 1; i < TABLE_COLUMNS.size(); i++){
            sb.append(getColumnName(i)).append(CS_TEXT).append(CS_COMMA);
        }
        sb.append(getColumnName(TABLE_COLUMNS.size())).append(CS_TEXT).append(CS_END_TABLE);

        return sb.toString();
    }

    @Override
    public String getInsertIntoTableStatement(){
        StringBuilder sb = new StringBuilder();
        sb.append(CS_OPEN_PARENTHESIS);

        for (int i = 1; i < TABLE_COLUMNS.size(); i++){
            sb.append(getColumnName(i)).append(CS_COMMA);
        }
        sb.append(getColumnName(TABLE_COLUMNS.size())).append(CS_CLOSE_PARENTHESIS).append(this.getValuesEndString());

        return sb.toString();
    }
}

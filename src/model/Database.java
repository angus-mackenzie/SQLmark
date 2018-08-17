package model;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class Database {
    private String lastMessage;
    private CompileStatus lastStatus;
    private String tableName;
    private ResultSet lastResultSet;
    private String currentSQL;
    private Connection dbConnection;
    List<String> columnNames;
    public enum CompileStatus {
        SUCCESS, FAILURE
    }

    public Database() {
        String url = "jdbc:postgresql://localhost:54321/postgres";
        try{
            dbConnection = DriverManager.getConnection(url, "root", "admin");
        } catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }
    }

    public Database(String databaseName) {
        String url = "jdbc:postgresql://localhost:54321/"+databaseName;
        try{
            dbConnection = DriverManager.getConnection(url, "root", "admin");
        } catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }
    }

    /**
     * Change a list of Strings into a prepared statement for creating a table
     * @param columnNames
     * @return string value
     */
    public void prepareCreate(List<String> columnNames, String tableName){
        this.columnNames = columnNames;
        this.tableName = tableName;
        StringBuilder createStatement = new StringBuilder();
        createStatement.append("CREATE TABLE ");
        createStatement.append(tableName);
        createStatement.append(" (");
        for(int i = 0; i< columnNames.size();i++){
            if(i==columnNames.size()-1){
                createStatement.append(columnNames.get(i));
                createStatement.append(" VARCHAR(100));");
            }else{
                createStatement.append(columnNames.get(i));
                createStatement.append(" VARCHAR(100), ");
            }
        }
        updateTableList(tableName);
        currentSQL = createStatement.toString();
    }

    /**
     * creates an insert into statement dependent on the column names, and list of strings given to it
     * @param row
     * @return a String of the insert into statement
     */
    public void  prepareInsert(List<String> row){
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("INSERT INTO ");
        insertStatement.append(tableName);
        insertStatement.append(" (");
        for(int i = 0; i< columnNames.size();i++) {
            if (i == columnNames.size() - 1) {
                insertStatement.append(columnNames.get(i));
                insertStatement.append(" )");
                insertStatement.append(" VALUES (");
            } else {
                insertStatement.append(columnNames.get(i));
                insertStatement.append(" , ");
            }
        }
        for(int i = 0; i < row.size(); i++){
            if(i == row.size()-1){
                insertStatement.append("'");
                insertStatement.append(row.get(i));
                insertStatement.append("'");
                insertStatement.append(" );");
            }else{
                insertStatement.append("'");
                insertStatement.append(row.get(i));
                insertStatement.append("'");
                insertStatement.append(", ");
            }
        }
        currentSQL= insertStatement.toString();
    }

    public void prepareSelect(String table) {
        prepareSelect(table, null);
    }

    public void prepareSelect(String table, Map<String, Object> where) {
        prepareSelect(table, where, -1);
    }

    public void execute(String sql) {
        currentSQL = sql;
        try{
            Statement statement = dbConnection.createStatement();
            lastResultSet = statement.executeQuery(sql);
            lastStatus = CompileStatus.SUCCESS;
        }catch(SQLException e){
            e.printStackTrace();
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }

    }


    public String getLastMessage() {
        return lastMessage;
    }

    public CompileStatus getLastStatus() {
        return lastStatus;
    }

    public ResultSet getResultSet() {
        return lastResultSet;
    }

    public void prepareSelect(String table, Map<String, Object> where, int limit) {
        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append("SELECT * FROM ");
        selectStatement.append(table);
        if(where!=null){
            selectStatement.append(" WHERE ");
            int counter = 0;
            int size = where.size();
            for(Map.Entry<String,Object> pair : where.entrySet()){
                if(counter==size-1){
                    selectStatement.append("'");
                    selectStatement.append(pair.getKey());
                    selectStatement.append("' = '");
                    selectStatement.append(pair.getValue());
                    selectStatement.append("'");
                }else{
                    selectStatement.append("'");
                    selectStatement.append(pair.getKey());
                    selectStatement.append("' = '");
                    selectStatement.append(pair.getValue());
                    selectStatement.append("' AND ");
                }
                counter++;
            }
        }
        if(limit!=-1){
            selectStatement.append(" LIMIT ");
            selectStatement.append(limit);
        }

        selectStatement.append(";");
        currentSQL= selectStatement.toString();
    }

    public void execute() {
        execute(currentSQL);
    }

    public String exportToSQL() {
        // TODO: Return the SQL command to recreate the table and contents of last query (null if no query run)
        // Use SHOW CREATE TABLE foobar
        // as well as some loop through the rows to create an insert statement
        throw new UnsupportedOperationException();
    }

    public void close() {
        try{
            dbConnection.close();
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }
    }
    public void createTableList(){
        String command = "CREATE TABLE table_list (VARCHAR(100) table_name);";
        currentSQL = command;
    }

    public void updateTableList(String tableName){
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("INSERT INTO table_list (table_name) VALUES (");
        insertStatement.append(tableName);
        insertStatement.append(");");
        currentSQL = insertStatement.toString();
    }
    public void closeRS() {
        try{
            lastResultSet.close();
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }
    }

    // TODO: Create way to duplicate database and then delete it after query has been run
}

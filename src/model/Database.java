package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Creates a connection to the database, and facilitates queries
 * @author Angus Mackenzie
 * @version 09/03/2018
 */
public class Database {
    private String lastMessage;
    private CompileStatus lastStatus;
    private String tableName;
    private ResultSet lastResultSet;
    private String currentSQL;
    private Connection dbConnection;
    List<String> columnNames;

    /**
     * Enum declaring different compile statuses
     */
    public enum CompileStatus {
        SUCCESS, FAILURE
    }

    /**
     * Constructor - creates a database connection with default database
     */
    public Database() {
        //TODO update this to point to the web server
            String url = "jdbc:mariadb://localhost:3306/data_store";
        try{
            dbConnection = DriverManager.getConnection(url, "root", "68(MNPq]+_9{fk>q");
        } catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            //TODO comment this out once finished finding error
            e.printStackTrace();

        }
    }

    /**
     * Creates a connection to the specified database
     * @param databaseName
     */
    public Database(String databaseName) {
        String url = "jdbc:mariadb://localhost:3306/"+databaseName;
        System.out.println("Attemptiong "+databaseName);
        try{
            dbConnection = DriverManager.getConnection(url, "root", "68(MNPq]+_9{fk>q");
            if(dbConnection.getMetaData().getMaxColumnsInTable()==0){
                //there are no other tables in the database
                System.out.println("What does this mean");
            }else {
                //there are other tables
                Statement statement = dbConnection.createStatement();
                prepareSelect(databaseName);
                lastResultSet = statement.executeQuery(currentSQL);
                ResultSetMetaData metaData = lastResultSet.getMetaData();
                int columns = metaData.getColumnCount();
                columnNames = new ArrayList<String>();
                for (int i = 0; i < columns; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }
            }
        } catch(SQLException e){
            System.out.println("FAILED");
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
        createStatement.append("CREATE TABLE IF NOT EXISTS ");
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
        if(!tableName.equals("table_list")){
            updateTableList(tableName);
        }
        System.out.println("USING "+createStatement);
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
        System.out.println("INSERTING "+insertStatement);
        currentSQL= insertStatement.toString();
    }

    /**
     * Prepares a select statement given a table name
     * @param table
     */
    public void prepareSelect(String table) {
        prepareSelect(table, null);
    }

    /**
     * Prepares a select statement given a table name, a map of columnames and objects for the where clause
     * @param table
     * @param where
     */
    public void prepareSelect(String table, Map<String, Object> where) {
        prepareSelect(table, where, -1);
    }

    /**
     * Executes a sql query on the database
     * @param sql
     */
    public void execute(String sql) {
        currentSQL = sql;
        try{
            Statement statement = dbConnection.createStatement();
            lastResultSet = statement.executeQuery(sql);
            lastStatus = CompileStatus.SUCCESS;
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }

    }

    /**
     * Returns the last message back from the database
     * @return last message
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Returns whether the last query compiled or not
     * @return
     */
    public CompileStatus getLastStatus() {
        return lastStatus;
    }

    /**
     * Returns whether the last result set
     * @return
     */
    public ResultSet getResultSet() {
        return lastResultSet;
    }

    /**
     * Takes in a table, where clause and a limit creates a query
     * @param table
     * @param where
     * @param limit
     */
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
                    //selectStatement.append("'");
                    selectStatement.append(pair.getKey());
                    //selectStatement.append("'");
                    selectStatement.append(" = '");
                    selectStatement.append(pair.getValue());
                    selectStatement.append("'");
                }else{
                    //selectStatement.append("'");
                    selectStatement.append(pair.getKey());
                    //selectStatement.append("'");
                    selectStatement.append(" = '");
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
        System.out.println("RUNNING "+selectStatement);
        currentSQL= selectStatement.toString();
    }

    /**
     * Executes the current query
     */
    public void execute() {
        execute(currentSQL);
    }

    public String exportToSQL() {
        // TODO: Return the SQL command to recreate the table and contents of last query (null if no query run)
        // Use SHOW CREATE TABLE foobar
        // as well as some loop through the rows to create an insert statement
        throw new UnsupportedOperationException();
    }



    /**
     * Updates the current list of tables
     * @param tableName
     */
    private void updateTableList(String tableName){
        Database tableDB = new Database("admin_data");
        List<String> header = new ArrayList<String>();
        header.add("table_name");
        tableDB.prepareCreate(header,"table_list");
        tableDB.execute();
        List<String> row = new ArrayList<String>();
        row.add(tableName);
        tableDB.prepareInsert(row);
        tableDB.execute();
    }

    /**
     * Closes the result set
     */
    public void closeRS() {
        try{
            if(lastResultSet!=null){
                lastResultSet.close();
            }
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }
    }

    /**
     * Closes connection to the database
     */
    public void close() {
        try{
            dbConnection.close();
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
        }
    }

    // TODO: Create way to duplicate database and then delete it after query has been run
}

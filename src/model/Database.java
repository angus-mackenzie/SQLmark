package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Creates a connection to the database, and facilitates queries
 * @author Angus Mackenzie
 * @version 04/09/2018
 */
public class Database {
    private String lastMessage;
    private CompileStatus lastStatus;
    private String tableName;
    private ResultSet lastResultSet;
    private String currentSQL;
    private Connection dbConnection;
    private List<String> columnNames;

    /**
     * Enum declaring different compile statuses
     */
    public enum CompileStatus {
        SUCCESS, FAILURE
    }

    /**
     * Constructor - creates a database connection with default database
     * @throws Error if it can't access the database
     */
    public Database() throws Error{
        //TODO update this to point to the web server
            String url = "jdbc:mariadb://localhost:3306/data_store";
        try{
            dbConnection = DriverManager.getConnection(url, "root", "68(MNPq]+_9{fk>q");
        } catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error(e.getCause());

        }
    }

    /**
     * Creates a connection to the specified database
     * @param databaseName to connect to
     * @throws Error if database connection fails
     */
    public Database(String databaseName) throws Error{
        String url = "";
        if(databaseName.equals("")){
            url = "jdbc:mariadb://localhost:3306";
        }else{
            url = "jdbc:mariadb://localhost:3306/"+databaseName;
        }
        try{
            dbConnection = DriverManager.getConnection(url, "root", "68(MNPq]+_9{fk>q");
        } catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error(e.getCause());
        }

    }

    //TODO Make this use types input by lecturer
    /**
     * Change a list of Strings into a prepared statement for creating a table
     * @param columnNames to create DB from
     * @param tableName the table to create
     * @throws Error if it cannot update the table_list table after creating the table
     */
    public void prepareCreate(List<String> columnNames, String tableName) throws Error{
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
        currentSQL = createStatement.toString();
    }

    //TODO make this adhere to type set out above
    /**
     * creates an insert into statement dependent on the column names, and list of strings given to it
     * @param row to be inserted
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

    /**
     * Prepares a select statement given a table name
     * @param table to select from
     */
    public void prepareSelect(String table) {
        prepareSelect(table, null);
    }

    /**
     * Prepares a select statement given a table name, a map of column names and objects for the where clause
     * @param table to select from
     * @param where clause map
     */
    public void prepareSelect(String table, Map<String, Object> where) {
        prepareSelect(table, where, -1);
    }

    /**
     * Executes a sql query on the database
     * @param sql string to execute
     * @throws Error if the statement fails
     */
    public void execute(String sql) throws Error{
        currentSQL = sql;
        try{
            Statement statement = dbConnection.createStatement();
            lastResultSet = statement.executeQuery(sql);
            lastStatus = CompileStatus.SUCCESS;
            lastMessage = "Executed Successfully";
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error(e.getCause());
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
     * @return CompileStatus the last status
     */
    public CompileStatus getLastStatus() {
        return lastStatus;
    }

    /**
     * Returns the last result set
     * @return last result set
     */
    public ResultSet getResultSet() {
        return lastResultSet;
    }

    /**
     * Takes in a table, where clause and a limit creates a query
     * @param table to select from
     * @param where clause
     * @param limit number of rows to limit by
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
                    selectStatement.append(pair.getKey());
                    selectStatement.append(" = '");
                    selectStatement.append(pair.getValue());
                    selectStatement.append("'");
                }else{
                    selectStatement.append(pair.getKey());
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
        currentSQL= selectStatement.toString();
    }


    /**
     * Executes the current query
     * @throws Error if query does not work
     */
    public void execute() throws Error{
        execute(currentSQL);
    }

    // TODO: Return the SQL command to recreate the table and contents of last query (null if no query run)
    public String exportToSQL() {
        // Use SHOW CREATE TABLE foobar
        // as well as some loop through the rows to create an insert statement
        return "Not done yet;";
        //throw new UnsupportedOperationException();
    }



    /**
     * Updates the current list of tables
     * @param tableName to add
     * @throws Error if the query fails
     */
    private void updateTableList(String tableName) throws Error{
        if(tableName.equals("data_store")) {
            Database tableDB = new Database("admin_data");
            List<String> header = new ArrayList<String>();
            header.add("table_name");
            tableDB.prepareCreate(header, "table_list");
            tableDB.execute();
            List<String> row = new ArrayList<String>();
            row.add(tableName);
            tableDB.prepareInsert(row);
            tableDB.execute();
        }
    }

    /**
     * Closes the result set
     * @throws Error if it cannot close RS
     */
    public void closeRS() throws Error{
        try{
            if(lastResultSet!=null){
                lastResultSet.close();
            }
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error("Couldn't close ResultSet",e.getCause());
        }
    }

    /**
     * Closes connection to the database
     * @throws Error if can't close connection
     */
    public void close() throws Error {
        try{
            dbConnection.close();
        }catch(SQLException e){
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error("Not able to close connection to the DB",e.getCause());
        }
    }

    /**
     * Pass the table name to be cleared
     * @param  tableName to be deleted
     * @return the last message
     * @throws Error if it query fails
     */
    public String clear(String tableName) throws Error{
        String tableToDelete = "";
        if(tableName.equals("data_store")){
            tableToDelete+="data_store."+tableName;
        }else{
            tableToDelete+="admin_data."+tableName;
        }
        String query = "DELETE FROM "+tableToDelete+";";
        execute(query);
        return lastMessage;
    }

    /**
     * Clears all the databases
     * @return the last message
     * @throws Error if the query fails
     */
    public String clearAll() throws Error{
        String[] queries = {
                "DELETE FROM admin_data.student_answers;",
                "DELETE FROM admin_data.questions;",
                "DELETE FROM admin_data.student_submissions;",
                "DELETE FROM admin_data.students;",
                "DELETE FROM admin_data.table_list;",
                "DELETE FROM data_store.data_store;"};
        try{
            Statement delete = dbConnection.createStatement();
            for(String query : queries){
                delete.executeQuery(query);
                execute(query);
                lastMessage = "Success";
            }
        }catch(Exception e){
            lastMessage = "Couldn't execute query";
            lastStatus = CompileStatus.FAILURE;
            throw new Error("Couldn't execute query ", e);
        }
        return lastMessage;
    }


    // TODO: Create way to duplicate database and then delete it after query has been run
}

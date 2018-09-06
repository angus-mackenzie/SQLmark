package model;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Creates a connection to the database, and facilitates queries
 *
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
    private List<String> columnTypes;
    private int lastUpdateCount;

    /**
     * Enum declaring different compile statuses
     */
    public enum CompileStatus {
        SUCCESS, FAILURE
    }

    /**
     * Constructor - creates a database connection with default database
     *
     * @throws Error if it can't access the database
     */
    public Database() throws Error {
        this("data_store");
    }

    /**
     * Creates a connection to the specified database
     *
     * @param databaseName to connect to
     * @throws Error if database connection fails
     */
    public Database(String databaseName) throws Error {
        String url = "";
        if (databaseName.equals("")) {
            url = "jdbc:mariadb://localhost:3306";
        } else {
            url = "jdbc:mariadb://localhost:3306/" + databaseName;
        }
        try {
            dbConnection = DriverManager.getConnection(url, "root", "68(MNPq]+_9{fk>q");
        } catch (SQLException e) {
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error(e.getCause());
        }

    }

    //TODO Make this use types input by lecturer

    /**
     * Change a list of Strings into a prepared statement for creating a table
     *
     * @param columnNames to create DB from
     * @param tableName   the table to create
     * @throws Error if it cannot update the table_list table after creating the table
     */
    public void prepareCreate(List<String> columnNames, List<String> columnTypes, String tableName) throws Error {
        if (columnNames.size() != columnTypes.size()) {
            throw new Error("Column Names Array and Column Types Array do not match");
        }
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
        this.tableName = tableName;
        StringBuilder createStatement = new StringBuilder();
        createStatement.append("CREATE TABLE IF NOT EXISTS ");
        createStatement.append(tableName);
        createStatement.append(" (");
        for (int i = 0; i < columnNames.size(); i++) {
            boolean isSpace = columnNames.get(i).contains(" ");
            if (isSpace) {
                createStatement.append("\"");
            }
            if (i == columnNames.size() - 1) {
                createStatement.append(columnNames.get(i));
                if (isSpace) createStatement.append("\"");
                createStatement.append(" ");
                createStatement.append(columnTypes.get(i));
                createStatement.append(");");
            } else {
                createStatement.append(columnNames.get(i));
                if (isSpace) createStatement.append("\"");
                createStatement.append(" ");
                createStatement.append(columnTypes.get(i));
                createStatement.append(", ");
            }
        }
        if (!tableName.equals("table_list")) {
            updateTableList(tableName);
        }
        currentSQL = createStatement.toString();
    }

    /**
     * Prepare insert
     *
     * @param tableName to insert into
     * @param row       to insert
     * @columns to insert into
     */
    public void prepareInsert(String tableName, List<String> columns, List<String> row) {
        this.tableName = tableName;
        this.columnNames = columns;
        prepareInsert(row);
    }

    /**
     * creates an insert into statement dependent on the column names, and list of strings given to it
     *
     * @param row     to be inserted
     * @param columns to be inserted into
     */
    public void prepareInsert(List<String> columns, List<String> row) {
        this.columnNames = columns;
        prepareInsert(row);
    }
    //TODO make this adhere to type set out above

    /**
     * creates an insert into statement dependent on the column names, and list of strings given to it
     *
     * @param row to be inserted
     */
    public void prepareInsert(List<String> row) {
        StringBuilder insertStatement = new StringBuilder();

        //TODO better implementation than using ignore to avoid primary key clashes?
        insertStatement.append("INSERT IGNORE INTO ");
        insertStatement.append(tableName);
        insertStatement.append(" (");
        for (int i = 0; i < columnNames.size(); i++) {
            if (i == columnNames.size() - 1) {
                insertStatement.append(columnNames.get(i));
                insertStatement.append(" )");
                insertStatement.append(" VALUES (");
            } else {
                insertStatement.append(columnNames.get(i));
                insertStatement.append(", ");
            }
        }
        for (int i = 0; i < row.size(); i++) {
            String valueToInsert = row.get(i);
            if (valueToInsert.contains("'")) {
                //then we must escape it
                valueToInsert = valueToInsert.replaceAll("'", "''");
            }
            if (i == row.size() - 1) {
                insertStatement.append("'");
                insertStatement.append(valueToInsert);
                insertStatement.append("'");
                insertStatement.append(" )");
            } else {
                insertStatement.append("'");
                insertStatement.append(valueToInsert);
                insertStatement.append("'");
                insertStatement.append(", ");
            }
        }
        insertStatement.append(";");

        currentSQL = insertStatement.toString();
    }

    /**
     * Prepares a select statement given a table name
     *
     * @param table to select from
     */
    public void prepareSelect(String table) {
        prepareSelect(table, null);
    }

    /**
     * Prepares a select statement given a table name, a map of column names and objects for the where clause
     *
     * @param table to select from
     * @param where clause map
     */
    public void prepareSelect(String table, Map<String, Object> where) {
        prepareSelect(table, where, -1);
    }

    /**
     * Executes a sql query on the database
     *
     * @param sql string to execute
     * @throws Error if the statement fails
     */
    public boolean execute(String sql) throws Error {
        currentSQL = sql;
        boolean type = false;
        try {
            Statement statement = dbConnection.createStatement();
            type = statement.execute(sql);
            if (type) {
                lastResultSet = statement.getResultSet();
            } else {
                lastUpdateCount = statement.getUpdateCount();
            }
            lastStatus = CompileStatus.SUCCESS;
            lastMessage = "Executed Successfully";
        } catch (SQLException e) {
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error(e);
        }
        return type;
    }

    /**
     * Returns the last message back from the database
     *
     * @return last message
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Returns whether the last query compiled or not
     *
     * @return CompileStatus the last status
     */
    public CompileStatus getLastStatus() {
        return lastStatus;
    }

    /**
     * Returns the last result set
     *
     * @return last result set
     */
    public ResultSet getResultSet() {
        return lastResultSet;
    }

    /**
     * Returns the last update count
     *
     * @return last update count
     */
    public int getUpdateCount() {
        return lastUpdateCount;
    }

    /**
     * Takes in a table, where clause and a limit creates a query
     *
     * @param table to select from
     * @param where clause
     * @param limit number of rows to limit by
     */
    public void prepareSelect(String table, Map<String, Object> where, int limit) {
        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append("SELECT * FROM ");
        selectStatement.append(table);
        if (where != null) {
            selectStatement.append(" WHERE ");
            int counter = 0;
            int size = where.size();
            for (Map.Entry<String, Object> pair : where.entrySet()) {
                if (counter == size - 1) {
                    selectStatement.append(pair.getKey());
                    selectStatement.append(" = '");
                    selectStatement.append(pair.getValue());
                    selectStatement.append("'");
                } else {
                    selectStatement.append(pair.getKey());
                    selectStatement.append(" = '");
                    selectStatement.append(pair.getValue());
                    selectStatement.append("' AND ");
                }
                counter++;
            }
        }
        if (limit != -1) {
            selectStatement.append(" LIMIT ");
            selectStatement.append(limit);
        }
        selectStatement.append(";");
        currentSQL = selectStatement.toString();
    }


    /**
     * Executes the current query
     *
     * @throws Error if query does not work
     */
    public void execute() throws Error {
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
     *
     * @param tableName to add
     * @throws Error if the query fails
     */
    private void updateTableList(String tableName) throws Error {
        if (tableName.equals("data_store")) {
            Database tableDB = new Database("admin_data");
            List<String> header = new ArrayList<String>();
            header.add("table_name");
            tableDB.prepareCreate(header, Collections.singletonList("VARCHAR(100)"), "table_list");
            tableDB.execute();
            List<String> row = new ArrayList<String>();
            row.add(tableName);
            tableDB.prepareInsert(row);
            tableDB.execute();
        }
    }

    /**
     * Closes the result set
     *
     * @throws Error if it cannot close RS
     */
    public void closeRS() throws Error {
        try {
            if (lastResultSet != null) {
                lastResultSet.close();
            }
        } catch (SQLException e) {
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error("Couldn't close ResultSet", e.getCause());
        }
    }

    /**
     * Closes connection to the database
     *
     * @throws Error if can't close connection
     */
    public void close() throws Error {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            lastStatus = CompileStatus.FAILURE;
            lastMessage = e.getStackTrace().toString();
            throw new Error("Not able to close connection to the DB", e.getCause());
        }
    }

    /**
     * Clears all the databases
     *
     * @return the last message
     * @throws Error if the delete query fails
     */
    //TODO implement an overarching table that keeps track of the tables
    public String clearAll() throws Error {

        List<String> data_storeTables = WorkingData.getTables();
        String[] admin_dataTables = {"admin_data.student_answers",
                "admin_data.questions",
                "admin_data.student_submissions",
                "admin_data.students",
                "admin_data.table_list"};
        String[] tables = new String[data_storeTables.size() + admin_dataTables.length];
        for (int i = 0; i < tables.length; i++) {
            if (i < data_storeTables.size()) {
                tables[i] = "data_store." + data_storeTables.get(i);
            } else {
                tables[i] = admin_dataTables[i - data_storeTables.size()];
            }
        }
        return clearAll(tables);
    }

    /**
     * Pass the table name to be cleared
     *
     * @param tableName to be deleted
     * @return the last message
     * @throws Error if it query fails
     */
    public String clear(String tableName) throws Error {
        List<String> data_storeTables = WorkingData.getTables();
        String tableToDelete = "";
        if (data_storeTables.contains(tableName)) {
            tableToDelete += "data_store." + tableName;
        } else {
            tableToDelete += "admin_data." + tableName;
        }
        String query = "DELETE FROM " + tableToDelete + ";";
        execute(query);
        return lastMessage;
    }

    /**
     * Clears admin_data database
     *
     * @return the last message
     * @throws Error if it cannot delete a database
     */
    public String clearAdmin() throws Error {
        String[] tables = {"admin_data.student_answers",
                "admin_data.questions",
                "admin_data.student_submissions",
                "admin_data.students",
                "admin_data.table_list"};
        return this.clearAll(tables);
    }

    /**
     * Clears all the databases
     *
     * @return the last message
     * @throws Error if the query fails
     */
    private String clearAll(String[] tables) throws Error {
        String queries[] = new String[tables.length];
        int counter = 0;
        for (String table : tables) {
            queries[counter] = "DELETE FROM " + table + ";";
            counter++;

        }
        try {
            Statement delete = dbConnection.createStatement();
            for (String query : queries) {
                delete.executeQuery(query);
                execute(query);
                lastMessage = "Success";
            }

        } catch (Exception e) {
            lastMessage = "Couldn't execute query";
            lastStatus = CompileStatus.FAILURE;
            throw new Error("Couldn't execute query ", e);
        }
        return lastMessage;
    }


    public String duplicateDB() throws Error {
        String randomDB = null;
        try {
            randomDB = "data_store_" + RandomStringUtils.randomAlphabetic(10);
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + randomDB);

            dbConnection.setCatalog("data_store");
            DatabaseMetaData md = dbConnection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);

            dbConnection.setCatalog(randomDB);
            Statement statement2;
            while (rs.next()) {
                String table = rs.getString(3);

                statement2 = dbConnection.createStatement();
                statement2.executeUpdate("CREATE TABLE " + table + " LIKE data_store." + table);
                statement2.executeUpdate("INSERT INTO " + table + " SELECT * FROM data_store." + table);
            }

            rs.close();

        } catch (SQLException e) {
            throw new Error("Error creating database!", e);
        }
        return randomDB;
    }

    public void deleteDB(String name) throws Error {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
            statement.executeUpdate("DROP DATABASE " + name);
        } catch (SQLException e) {
            throw new Error("Error deleting database!", e);
        }
    }

    public List<String> getAllTables() {
        List<String> list = new ArrayList<>();

        try {
            DatabaseMetaData md = dbConnection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                list.add(rs.getString(3));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

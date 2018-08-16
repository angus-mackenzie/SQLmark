package model;

import java.sql.ResultSet;
import java.util.Map;

public class Database {
    private String lastMessage;
    private CompileStatus lastStatus;
    private ResultSet lastResultSet;
    private String currentSQL;

    public enum CompileStatus {
        SUCCESS, FAILURE
    }

    public Database() {
        // TODO: Create new database connection with main database
        throw new UnsupportedOperationException();
    }

    public Database(String databaseName) {
        // TODO: Create new database connection with specified database
        throw new UnsupportedOperationException();
    }

    public void prepareSelect(String table) {
        prepareSelect(table, null);
    }

    public void prepareSelect(String table, Map<String, Object> where) {
        prepareSelect(table, where, -1);
    }

    public void execute(String sql) {
        // TODO: Execute single SQL statement against the database
        throw new UnsupportedOperationException();
    }

    public String getLastMessage() {
        // TODO: Get the last message from query (null if no query run)
        throw new UnsupportedOperationException();
    }

    public CompileStatus getLastStatus() {
        // TODO: Get the last status from query (null if no query run)
        throw new UnsupportedOperationException();
    }

    public ResultSet getResultSet() {
        // TODO: Return the resultset of the last run query
        throw new UnsupportedOperationException();
    }

    public void prepareSelect(String table, Map<String, Object> where, int limit) {
        // TODO: Prepare SQL statement for select
        throw new UnsupportedOperationException();
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
        // TODO: Close DB connection
        throw new UnsupportedOperationException();
    }

    public void closeRS() {
        // TODO: Close ResultSet
        throw new UnsupportedOperationException();
    }

    // TODO: Create way to duplicate database and then delete it after query has been run
}

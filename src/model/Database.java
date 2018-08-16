import java.sql.ResultSet;

public class Database {
    private String lastMessage;
    private CompileStatus lastStatus;
    private ResultSet lastResultSet;

    public enum CompileStatus {
        SUCCESS, FAILURE
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

    public void close() {
        // TODO: Close resultset and DB connection
        throw new UnsupportedOperationException();
    }
}

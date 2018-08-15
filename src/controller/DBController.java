import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 * Class to create the DB, populate it and manipulate the data
 * @author Angus Mackenzie
 * @version 14/08/2018
 */
public class DBController {
    Connection dbConnection = null;
    List<String> columnNames;

    /**
     * Constructor, takes in the columnNames for the table and makes database with them.
     * If the table exists, it continues as normal.
     * @param columnNames
     * @throws Exception
     */
    public DBController(List<String> columnNames) throws Exception {
        this.columnNames = columnNames;
        String url = "jdbc:postgresql://localhost:54321/postgres";
        //create connection for the server created with our vagrantfile, with a user "root" with password admin
        //TO DO - get rid of default credentials and have a secret manager
        try{
            dbConnection = DriverManager.getConnection(url, "root", "admin");
            System.out.println("Connected to database "+url);
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(-1);
        }
        try{
            String query = stringToCreateStatement(columnNames);
            System.out.println("Using the following create statement:");
            System.out.println(query);
            PreparedStatement create = dbConnection.prepareStatement(query);

            create.executeUpdate();
            System.out.println("Created a table called demoTable");
        }catch(Exception e){
            System.out.println( e.getLocalizedMessage());
            System.out.println("Getting data from demoTable");
            Statement statement = dbConnection.createStatement();
            System.out.println("Outputting the table -> demoTable");
            ResultSet results = statement.executeQuery("SELECT * FROM demoTable");
            outputResultSet(results);

        }
    }

    /**
     * Executes an insert statement
     * @param row to be insert
     * @return 1 for true, 0 for false
     * @throws Exception
     */
    public int insertRow(List<String> row) throws Exception{
        Statement statement = dbConnection.createStatement();
        int result = statement.executeUpdate(createInsertStatement(row));
        return result;
    }

    /**
     * creates an insert into statement dependent on the column names, and list of strings given to it
     * @param row
     * @return a String of the insert into statement
     */
    public String createInsertStatement(List<String> row){
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("INSERT INTO demoTable (");
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
        System.out.println(insertStatement);
        return insertStatement.toString();
    }
    /**
     * Change the list of Strings into a prepared statement for creating a table
     * @param columnNames
     * @return string value
     */
    public String stringToCreateStatement(List<String> columnNames){
        //TO DO - make this more generic
        StringBuilder createStatement = new StringBuilder();
        createStatement.append("CREATE TABLE demoTable");
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
        return  createStatement.toString();
    }

    /**
     * Prints out queries content - for testing only, do not use to output queries as it will fail
     * @param rs
     * @throws Exception
     * @see http://www.java2s.com/Tutorial/Java/0340__Database/CreateaTableUsingPreparedStatement.htm
     */
    public void outputResultSet(ResultSet rs) throws Exception{
        //TO Do, make this work for every resultset, not just create and inserts
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();
        for (int i = 1; i < numberOfColumns + 1; i++) {
            String columnName = rsMetaData.getColumnName(i);
            System.out.print(columnName + "   ");

        }
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

        while (rs.next()) {
            for (int i = 1; i < numberOfColumns + 1; i++) {
                System.out.print(rs.getString(i) + "   ");
            }
            System.out.println();
        }

    }

    /**
     * Deletes the table contents
     * @return a 1 for true a 0 for false
     * @throws SQLException
     */
    public int deleteTableContents() throws SQLException{
        //TO DO - make this work
        String statement = "DELETE * FROM demoTable;";
        PreparedStatement delete = dbConnection.prepareStatement(statement);
        int result = delete.executeUpdate();
        return result;
    }

    /**
     * Returns a connection to the database which can be used to perform queries in other classes
     * @return
     */
    public Connection getDbConnection(){
        return dbConnection;
    }

}
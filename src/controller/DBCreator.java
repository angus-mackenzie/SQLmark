import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 * Class to create the DB, and work with the data
 * @author Angus Mackenzie
 * @version 14/08/2018
 */
public class DBCreator{
    Connection dbConnection = null;
    List<String> columnNames;
    public DBCreator(List<String> columnNames) throws Exception {
        this.columnNames = columnNames;
        String url = "jdbc:postgresql://localhost:54321/postgres";
        //create connection for the server created with our vagrantfile, with a user "root" with password admin
        try{
            dbConnection = DriverManager.getConnection(url, "root", "admin");
            System.out.println("Connected to database "+url);
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(-1);
        }
        try{
            String query = stringToCreateStatement(columnNames);
            System.out.println(query);
            PreparedStatement create = dbConnection.prepareStatement(query);

            create.executeUpdate();
            System.out.println("Created a table called demoTable");
        }catch(Exception e){
            System.out.println( e.getLocalizedMessage());
            System.out.println("Getting data from demoTable");
            Statement statement = dbConnection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM demoTable");
            outputResultSet(results);

        }
    }
    public boolean insertRow(List<String> row) throws Exception{
        Statement statement = dbConnection.createStatement();
        int result = statement.executeUpdate(createInsertStatement(row));
        return intToBoolean(result);
    }
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
     * Change the list of Strings into a prepared statement
     * @param columnNames
     * @return string value
     */
    public String stringToCreateStatement(List<String> columnNames){
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
     * Prints out current table contents
     * @param rs
     * @throws Exception
     * @see http://www.java2s.com/Tutorial/Java/0340__Database/CreateaTableUsingPreparedStatement.htm
     */
    public void outputResultSet(ResultSet rs) throws Exception{
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
    public Connection getDbConnection(){
        return dbConnection;
    }
    public boolean intToBoolean(int val){
        if(val == 1){
            return true;
        }
        return false;
    }
}
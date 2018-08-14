import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 * Class to create the DB
 * @author Angus Mackenzie
 * @version 14/08/2018
 */
public class DBCreator{

    public DBCreator(List<String> columnNames) throws Exception {
        String url = "jdbc:postgresql://localhost:54321/postgres";
        //create connection for the server created with our vagrantfile, with a user "root" with password admin
        Connection dbConnection = null;
        try{
            dbConnection = DriverManager.getConnection(url, "root", "admin");
            System.out.println("Connected to database "+url);
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(-1);
        }
        try{
            String query = stringToStatement(columnNames);
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

    public void setup(Connection dbConnection){

    }

    /**
     * Change the list of Strings into a prepared statement
     * @param columnNames
     * @return string value
     */
    public String stringToStatement(List<String> columnNames){
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
}
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
    public DBCreator(List<String> columnNames) throws SQLException {
        String url = "jdbc:postgresql://localhost:54321/postgres";
        //create connection for a server installed in localhost, with a user "root" with no password
        try (Connection conn = DriverManager.getConnection(url, "root", "admin")) {
            System.out.println("Connectd");
            // create a Statement
            try (Statement stmt = conn.createStatement()) {
                List<String> tables = new ArrayList<String>();
                int hey=stmt.executeUpdate("INSERT INTO test "+"VALUES('AHHH');");
                System.out.println(hey); //result is "1" for true, "0" for false
            }
        }

    }
}
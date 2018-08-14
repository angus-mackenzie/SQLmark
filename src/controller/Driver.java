import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Driver for our demo
 */
public class Driver {
    public static void main(String[] args) {
        //create reader class
        List<String> columnNames = new ArrayList<String>();
        CSVReader csvReader = new CSVReader();
        try{
            Reader dataReader = new BufferedReader(new FileReader(new File("matricData.csv")));
            List<String> input = csvReader.parseLine(dataReader);
            columnNames = input;
            while(input !=null){
                System.out.println(input);
                input = csvReader.parseLine(dataReader);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        DBCreator db = new DBCreator(columnNames);

    }
}

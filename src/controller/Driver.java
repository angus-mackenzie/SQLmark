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
        int counter = 1;
        try{
            Reader dataReader = new BufferedReader(new FileReader(new File("matricData.csv")));
            List<String> input = csvReader.parseLine(dataReader);
            columnNames = input;
            DBCreator db = new DBCreator(columnNames);
            while(input !=null){
                System.out.println("Inserting row "+counter+" into db");
                input = csvReader.parseLine(dataReader);
                if(input!=null){
                    db.insertRow(input);
                }
                counter++;
            }
        }catch(Exception e){
            System.out.println("Failed to insert row: "+counter);
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

/**
 * Driver for our demo
 */
public class Driver {
    public static void main(String[] args) {
        //create reader class
        CSVReader csvReader = new CSVReader();
        try{
            Reader dataReader = new BufferedReader(new FileReader(new File("matricData.csv")));
            List<String> input = csvReader.parseLine(dataReader);
            while(input !=null){
                System.out.println(input);
                input = csvReader.parseLine(dataReader);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

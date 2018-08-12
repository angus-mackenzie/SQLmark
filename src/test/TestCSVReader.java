import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestCSVReader{
    CSVReader csvReader;
    Reader dataReader;

    @Before
    public void init(){
        String filename = "matricData.csv";
        try{
            csvReader = new CSVReader();
            dataReader = new BufferedReader(new FileReader(new File(filename)));
        }catch(Exception e){
            System.err.println("TestCSVReader Failed on initialisation, is the file \""+filename+"\" in the SQLmark directory");
            e.printStackTrace();
        }

    }
    @Test
    public void testAmountColumns() throws Exception{
        List<String> input = csvReader.parseLine(dataReader);
        int expectedColumns = input.size();
        while(input !=null){
            assertEquals("The size of this line is equal to the amount of columns ",expectedColumns, input.size());
            input = csvReader.parseLine(dataReader);
        }
    }
}
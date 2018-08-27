package model;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Testing for the CSV class
 * @Author Angus Mackenzie
 * @Version 12/08/2018
 */

public class TestCSV{
    private CSV csvReader;
    /**
     * Sets up test environment
     */
    @Before
    public void init(){
        String filename = "matricData.csv";
        try{
            csvReader = new CSV(filename);
        }catch(Exception e){
            System.err.println("TestCSVReader Failed on initialisation, is the file \""+filename+"\" in the SQLmark directory");
            e.printStackTrace();
        }

    }

    /**
     * Test that none of the other rows are larger than the initial amount of columns
     * @throws Exception
     */
    @Test
    public void testAmountColumns() throws Exception{
        List<String> input = csvReader.parseLine();
        int expectedColumns = input.size();
        while(input !=null){
            assertEquals("The size of this line is equal to the amount of columns ",expectedColumns, input.size());
            input = csvReader.parseLine();
        }
    }

    //TODO Test Write Function for Student Marks
}
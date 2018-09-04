package model;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Testing for the CSV class
 * @Author Angus Mackenzie
 * @Version 12/08/2018
 */

public class TestCSV{
    private CSV csvReader;
    /**
     * Sets up test environment for basic tests
     */
    @Before
    public void init(){
        String filename = "data.csv";
        try{
            csvReader = new CSV(filename);
        }catch(Exception e){
            System.err.println("TestCSVReader Failed on initialisation, is the file \""+filename+"\" in the SQLmark directory");
            e.printStackTrace();
        }

    }

    /**
     * Test that none of the other rows are larger than the initial amount of columns
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

    /**
     * Tests no file entered, expects Exception
     * @throws Exception
     */
    @Test(expected = FileNotFoundException.class)
    public void testNoFile() throws Exception{
        String filename = "";
        CSV csvReader = new CSV(filename);
        assertFalse("The file should not be open",csvReader.isOpen());
    }

    /**
     * Tests a file with no extension, will run as usual
     * @throws Exception
     */
    @Test
    public void testNoExtension() throws Exception{
        String filename = "matricData";
        CSV csvReader = new CSV(filename);
        assertTrue("The CSV should be open",csvReader.isOpen());
    }

    /**
     * Tests if the filename is invalid, expects exception
     * @throws Exception
     */
    @Test(expected = FileNotFoundException.class)
    public void testWrongFileName() throws Exception{
        String filename = "this is not valid";
        CSV csvReader = new CSV(filename);
        assertFalse("The file should not be open",csvReader.isOpen());
    }

    /**
     * Creates and populates file, checks file size
     */
    @Test
    public void testWriteFile() throws Exception{
        List<String> testHeadings = new ArrayList<>();
        testHeadings.add("testHeading1");
        testHeadings.add("testHeading2");
        CSV csvWriter = new CSV("testOutput.csv",testHeadings);
        for(int i = 0; i< 5; i++){
            List<String> fakeData = new ArrayList<>();
            fakeData.add("foo "+i);
            fakeData.add("bar "+i);
            csvWriter.writeLine(fakeData);
        }
        assertEquals("The number of lines in the file should equal 6",6,csvWriter.countLines());

    }

}
package model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestCSV{
    private CSV csvReader;

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


    @Test
    public void testAmountColumns() throws Exception{
        List<String> input = csvReader.parseLine();
        int expectedColumns = input.size();
        while(input !=null){
            assertEquals("The size of this line is equal to the amount of columns ",expectedColumns, input.size());
            input = csvReader.parseLine();
        }
    }


    @Test(expected = Error.class)
    public void testNoFile() throws Exception{
        String filename = "";
        CSV csvReader = new CSV(filename);
        assertFalse("The file should not be open",csvReader.isOpen());
    }


    @Test
    public void testNoExtension() throws Exception{
        String filename = "matricData";
        CSV csvReader = new CSV(filename);
        assertTrue("The CSV should be open",csvReader.isOpen());
    }


    @Test(expected = Error.class)
    public void testWrongFileName() throws Exception{
        String filename = "this is not valid";
        CSV csvReader = new CSV(filename);
        assertFalse("The file should not be open",csvReader.isOpen());
    }


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
        csvWriter.closeWriter();
        assertTrue("The file should be deleted",csvWriter.deleteFile());
    }

}
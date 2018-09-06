package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertTrue;

public class TestLecturer {
    Lecturer lecturer;
    @Before
    public void init(){
        try{
            lecturer = new Lecturer();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadData() throws Exception{
        String filename = "matricData.csv";
        lecturer.loadData(filename);
    }

    @Test
    public void testExportData() throws Exception {
        String filename = "output.csv";
        lecturer.exportStudents(filename);
        File file = new File("output.csv");
        assertTrue("The file should be deleted",file.delete());
    }
}
package view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static junit.framework.TestCase.assertTrue;

public class TestStudent {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    @Before
    public void init(){
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testCreateStudent() throws Exception {

        String data = ""+1;
        InputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);
        String expectedOutput = "Menu:\n" +
                " - Get questions (1)\n" +
                " - Get data (2)\n" +
                " - Start submission (3)\n" +
                " - Get previous submissions (4)\n" +
                " - Exit (9)\n" +
                "Enter option: \n" +
                "1: Output any one tuple from the matricData table.\n" +
                "2: Output the number of tuples in the matricData table.\n" +
                "3: Show all entries where the gender disagrees with the prefix (e.g. someone is “Mr” but their gender is “F”).\n" +
                "4: List students from school “1000929” giving first name and last name in alphabetical order of last name.\n" +
                "\n" +
                "\n" +
                "Menu:\n" +
                " - Get questions (1)\n" +
                " - Get data (2)\n" +
                " - Start submission (3)\n" +
                " - Get previous submissions (4)\n" +
                " - Exit (9)\n" +
                "Enter option: ";
        Student testStudent = new Student(new controller.Student("abrsas002"),sc);
        System.out.println(outContent.toString());
        assertTrue(expectedOutput.equals(outContent.toString()));
    }

    @Test
    public void testScannerReading() throws Exception {

        String data = ""+3+"\n"+"SELECT * FROM matricData;";
        InputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);
        Student testStudent = new Student(new controller.Student("abrsas002"),sc);
        //System.out.println(input);
    }


    @Test
    public void shouldTakeUserInput() throws Exception {

    }
    @Test
    public void testInput() throws Exception{
        String data = "\n"+1+"\n";
        InputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);
        controller.Student student = new controller.Student("abrsas002");
        Student testStudent = new Student(student,sc);
    }

    @After
    public void restore(){
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}

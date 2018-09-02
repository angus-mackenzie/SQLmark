package model;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
/**
 * Simple CSVReader for the Demo -> if we have time, implement SuperCSV
 * Reads in a CSV value, and understands information with commas
 * @Author Angus
 * @version 16/08/2018
 * @see https://agiletribe.wordpress.com/2012/11/23/the-only-class-you-need-for-csv-files/
 */

public class CSV {
    private String filename;
    private Reader dataReader;
    private boolean isOpen = false;

    public CSV(String filename) throws Exception{
        this.filename = filename;
        if(filename.equals("")){
            System.err.println("You did not enter in a filename, please restart and enter one");
            System.exit(1);
        }
        if(!filename.contains(".csv")){
            filename+=".csv";
        }
        //try{
            dataReader = new BufferedReader(new FileReader(new File(filename)));
            isOpen= true;
//        }catch(Exception e){
//            System.err.println(filename + " is not a valid file name.");
//            System.err.println(e.getMessage());
//            Throwable throwm = new Throwable(e);
//
//            System.exit(-1);
//        }
    }
    /**
     * Writes a line of a CSV file
     * @param w the writer used
     * @param values the values to be seperated by commas
     * @throws Exception
     */
    public static void writeLine(Writer w, List<String> values)
            throws Exception
    {
        boolean firstVal = true;
        for (String val : values)  {
            if (!firstVal) {
                w.write(",");
            }
            w.write("\"");
            for (int i=0; i<val.length(); i++) {
                char ch = val.charAt(i);
                if (ch=='\"') {
                    w.write("\"");  //extra quote
                }
                w.write(ch);
            }
            w.write("\"");
            firstVal = false;
        }
        w.write("\n");
    }

    /**
     * Returns a null when the input stream is empty
     * @throws Exception
     */
    public List<String> parseLine() throws Exception {
        Reader r = dataReader;
        int ch = r.read();
        while (ch == '\r') {
            ch = r.read();
        }
        if (ch<0) {
            return null;
        }
        Vector<String> store = new Vector<String>();
        StringBuffer curVal = new StringBuffer();
        boolean inquotes = false;
        boolean started = false;
        while (ch>=0) {
            if (inquotes) {
                started=true;
                if (ch == '\"') {
                    inquotes = false;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            else {
                if (ch == '\"') {
                    inquotes = true;
                    if (started) {
                        // if this is the second quote in a value, add a quote
                        // this is for the double quote in the middle of a value
                        curVal.append('\"');
                    }
                }
                else if (ch == ',') {
                    store.add(curVal.toString());
                    curVal = new StringBuffer();
                    started = false;
                }
                else if (ch == '\r') {
                    //ignore LF characters
                }
                else if (ch == '\n') {
                    //end of a line, break out
                    break;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            ch = r.read();
        }
        store.add(curVal.toString());
        return store;
    }

    public boolean isOpen(){
        return isOpen;
    }
}

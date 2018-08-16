package controller;
import java.io.BufferedReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Vector;

/**
 * Simple CSVReader for the Demo -> if we have time, implement SuperCSV
 * Reads in a CSV value, and understands information with commas
 * @Author Angus
 * @version 11/08/2018
 * @see https://agiletribe.wordpress.com/2012/11/23/the-only-class-you-need-for-csv-files/
 */
public class CSVReader
{
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
     * @param  r the reader that points to the file
     * @throws Exception
     */
    public static List<String> parseLine(Reader r) throws Exception {
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
}
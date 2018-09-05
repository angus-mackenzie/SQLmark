package model;

import java.io.*;
import java.util.List;
import java.util.Vector;
/**
 * Simple CSVReader. if we have time, implement SuperCSV
 * Reads in a CSV value, and understands information with commas
 * @author Angus Mackenzie
 * @version 16/08/2018
 * @see <a href="https://agiletribe.wordpress.com/2012/11/23/the-only-class-you-need-for-csv-files/">https://agiletribe.wordpress.com/2012/11/23/the-only-class-you-need-for-csv-files/</a>
 */

public class CSV {
    private String filename;
    private Reader dataReader;
    private boolean isOpen = false;
    private Writer dataWriter;
    private char delimiter;
    /**
     * Takes in filename, checks if it is correct, otherwise creates a reader
     * @param filename of the csv file to read
     * @throws Error if there is an issue opening the file
     */
    public CSV(String filename) throws Error{
        this.delimiter = ',';
        this.filename = checkFileName(filename);
        try{
            dataReader = new BufferedReader(new FileReader(new File(this.filename)));
            isOpen= true;
        }catch(Exception e){
            throw new Error("Problem opening file "+filename,e.getCause());
        }
    }

    /**
     * Takes in a file path, and writes the firstLine of the CSV
     * @param outputFile to file
     * @param heading of the CSV file to be written
     * @throws Error if can't write to file
     */
    public CSV(String outputFile, List<String> heading) throws Error{
        this.filename = checkFileName(outputFile);
        try{
            File file = new File(outputFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            dataWriter = new BufferedWriter(new FileWriter(new File(filename)));
            writeLine(heading);
            isOpen = true;
        }catch(Exception e){
            throw new Error("Could not write to file "+filename, e.getCause());
        }
    }

    /**
     * Creates the normal CSV reader, but uses the specified delimiter
     * @param delimiter to use
     * @param filename file to read
     * @throws Error if it can't read
     */
    public CSV(char delimiter, String filename) throws Error{
        this.delimiter = delimiter;
        this.filename = checkFileName(filename);
        try{
            dataReader = new BufferedReader(new FileReader(new File(this.filename)));
            isOpen= true;
        }catch(Exception e){
            throw new Error("Problem opening file "+filename,e.getCause());
        }
    }
    /**
     * Checks if the filename is blank or does not have the appropriate extension
     * @param filename to be checked
     * @return string
     * @throws Error if there is no filename
     */
    public String checkFileName(String filename) throws Error{
        if(filename.equals("")){
            throw new Error("You did not enter in a filename, please restart and enter one");
        }
        if(!filename.endsWith(".csv")){
            filename+=".csv";
        }
        return filename;
    }


    /**
     * Writes a line of a CSV file
     * @param values the values to be separated by commas
     * @throws Exception if can't write
     */
    public void writeLine(List<String> values) throws Exception {
        boolean firstVal = true;
        for (String val : values) {
            if (!firstVal) {
                dataWriter.write(",");
            }
            //dataWriter.write("\"");
            for (int i=0; i<val.length(); i++) {
                char ch = val.charAt(i);
                if (ch=='\"') {
                    dataWriter.write("\"");  //extra quote
                }
                dataWriter.write(ch);
            }
            //dataWriter.write("\"");
            firstVal = false;
        }
        dataWriter.write("\n");
        dataWriter.flush();
    }

    /**
     * Reads a line of the CSV
     * Returns a null when the input stream is empty
     * @return List of strings
     * @throws Exception if can't read line
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
                else if (ch == delimiter) {
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


    /**
     * Closes the file writer
     */
    public void closeWriter() throws IOException{
        if(isOpen){
            dataWriter.close();
        }
    }

    /**
     * Closes the file reader
     */
     public void closeReader() throws IOException{
         if(isOpen){
             dataReader.close();
         }

     }
    /**
     * Checks if the file is open
     * @return isOpen
     */
    public boolean isOpen(){
        return isOpen;
    }

    /**
     * Returns the number of lines in the specified file
     * @return number of lines
     * @throws IOException if it cant find the file
     * @see <a href="https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java">https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java</a>
     */
    public int countLines() throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i=0; i<1024;) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        } finally {
            is.close();
        }
    }
}

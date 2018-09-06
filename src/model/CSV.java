package model;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.List;
/**
 * Implementation of the OpenCSV CSVReader
 * @author Angus Mackenzie
 * @version 05/09/2018
 * @see CSVReader
 */

public class CSV {
    private String filename;
    private Reader dataReader;
    private CSVReader csvReader;
    private boolean isOpen = false;
    private Writer dataWriter;
    private char delimiter;
    private File file;
    /**
     * Takes in filename, checks if it is correct, otherwise creates a reader
     * @param filename of the csv file to read
     * @throws Error if there is an issue opening the file
     */
    public CSV(String filename) throws Error{
        this.delimiter = ',';
        this.filename = checkFileName(filename);
        try{
            Reader dataReader = new BufferedReader(new FileReader(new File(this.filename)));
            CSVParser parser = new CSVParserBuilder().withSeparator(delimiter).build();
            csvReader = new CSVReaderBuilder(dataReader).withCSVParser(parser).build();
            isOpen= true;
        }catch(Exception e){
            throw new Error("Problem opening file "+this.filename,e.getCause());
        }
    }

    /**
     * Creates the a CSV reader, but uses the specified delimiter
     * @param delimiter to use
     * @param filename file to read
     * @throws Error if it can't read
     */
    public CSV(char delimiter, String filename) throws Error{
        this.delimiter = delimiter;
        this.filename = checkFileName(filename);
        try{
            Reader dataReader = new BufferedReader(new FileReader(new File(this.filename)));
            CSVParser parser = new CSVParserBuilder().withSeparator(delimiter).build();
            csvReader = new CSVReaderBuilder(dataReader).withCSVParser(parser).build();
            isOpen= true;
        }catch(Exception e){
            throw new Error("Problem opening file "+this.filename,e.getCause());
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
            this.file = new File(outputFile);
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


    public List<String> parseLine() throws Exception{
        String[] output = csvReader.readNext();
        if(output==null){
            return null;
        }
        return Arrays.asList(output);
    }

    /**
     * Closes the file writer
     * @throws IOException if it cannot close the writer
     */
    public void closeWriter() throws IOException{
        if(isOpen){
            dataWriter.close();
        }
    }

    /**
     * Closes the file reader
     * @throws IOException if it cannot close the reader
     */
     public void closeReader() throws IOException{
         if(isOpen){
             csvReader.close();
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
    protected boolean deleteFile(){
        return file.delete();
    }
}

package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Runs the .SQL File setup scripts for the DB
 * @author Angus Mackenzie
 * @version 05/09/2018
 * @see @see <a href="http://www.tonyspencer.com/2005/01/20/execute-mysql-script-from-java/index.html">http://www.tonyspencer.com/2005/01/20/execute-mysql-script-from-java/index.html</a>
 */
public class Runner {
    private static String dbname ="";
    private static String dbuser = "";
    private static String dbpassword ="";
    private static String scriptpath = "";
    private static boolean verbose = false;

    /**
     * Takes in the data needed to run the script
     * @param databaseName to insert into
     * @param user to insert as
     * @param password for the database
     * @param pathToSql where the .SQL file is
     * @param verbose output on/off
     */
    public Runner(String databaseName, String user, String password, String pathToSql, boolean verbose){
        dbname = databaseName;
        dbuser = user;
        dbpassword =password;
        scriptpath = pathToSql;
        this.verbose = verbose;
    };

    public static String executeScript () {
        String output = null;
        try {
            String[] cmd = new String[]{"mysql",
                    dbname,
                    "--user=" + dbuser,
                    "--password=" + dbpassword,
                    "-e",
                    "\"source " + scriptpath + "\""

            };
            System.err.println(cmd[0] + " " + cmd[1] + " " + cmd[2] + " " + cmd[3] + " " + cmd[4] + " " + cmd[5]);
            Process proc = Runtime.getRuntime().exec(cmd);
            if (verbose) {
                InputStream inputstream = proc.getInputStream();
                InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
                BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
                String line;
                while ((line = bufferedreader.readLine()) != null) {
                    System.out.println(line);
                }
                try {
                    if (proc.waitFor() != 0) {
                        System.err.println("exit value = " +
                                proc.exitValue());
                    }
                }
                catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}

package view;

import model.Error;

import java.util.Scanner;

/**
 * View for the lecturer
 * This is empty as our Client requested that the lecturer input be via a CommandLine Interface (CLI)
 * @author Angus Mackenzie
 * @version 04/09/2018
 */
public class Lecturer {
    private final Scanner sc;
    private controller.Lecturer lecturer;
    /**
     * Constructs a view given a Controller.lecturer
     * @param lecturer to be viewed
     * @throws Error one of the methods doesn't work
     */
    public Lecturer(controller.Lecturer lecturer, Scanner sc) throws  Error{
        this.lecturer = lecturer;
        this.sc = sc;

        int option = 0;
        while(option != 9){
            option = showMenu();
            switch (option) {
                case 1:
                    setupAssignment();
                    break;
                case 2:
                    exportMarks();
                    break;
                case 3:
                    System.out.println("Clearing data");
                    clearData();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Please enter valid option!");
                    break;
            }
        }
        System.out.println("Goodbye!");
    }

    /**
     * Shows the Lecturer the menu
     * @return the selection they made
     */
    private int showMenu() {
        int option = 0;
        System.out.println();
        System.out.println("Menu:");
        System.out.println(" - Setup Assignment (1)");
        System.out.println(" - Export Current Marks (2)");
        System.out.println(" - Clear Data (3)");
        System.out.println(" - Exit (9)");
        System.out.print("Enter option: ");
        option = sc.nextInt();

        return option;
    }

    /**
     * Asks the user for the 3 CSV names, creates an assignment with them
     * @throws Error if the file isn't found, or the DB cannot be inserted into
     */
    private void setupAssignment() throws Error {
        System.out.println("Enter the data filename:");
        String filename = sc.nextLine();
        if(filename.equals("")){
            //TODO figure out why I have to do this
            filename = sc.nextLine();
        }
        lecturer.loadData(filename);
        System.out.println("Data loaded successfully!");
        System.out.println("Enter the students filename:");
        String studentFile = sc.nextLine();
        lecturer.loadStudents(studentFile);
        System.out.println("Loaded students successfully");
        System.out.println("Enter question and answer filename:");
        String qnaFile = sc.nextLine();
        lecturer.loadQuestions(qnaFile);
        System.out.println("Successful");
    }

    /**
     * Exports the contents of the studentMark table to a CSV
     * @throws Error if it cannot find the file, or fetch the values
     */
    private void exportMarks() throws Error{
        System.out.println("Enter the name of the file you want to export to:");
        String filename = sc.nextLine();
        if(filename.equals("")){
            //TODO figure out why I have to do this
            filename = sc.nextLine();
        }
        lecturer.exportStudents(filename);
    }

    /**
     * Clears the data from all or one of the databases
     * @throws Error if the deletion fails
     */
    private void clearData() throws Error{
        System.out.println("What do you want to clear?");
        System.out.println(" - One Database (1)");
        System.out.println(" - All Databases (2)");
        System.out.println(" - Cancel (9)");
        int option = 0;
        option = sc.nextInt();
        sc.nextLine();
        if(option == 1){
            System.out.println("Enter the name of the table you want to clear:");
            String tableName = sc.nextLine();
            System.out.println("Clearing "+tableName);
            System.out.println(lecturer.clear(tableName));
        }else if(option == 2){
            System.out.println("Clearing all databases");
            System.out.println("Query Status: "+lecturer.clearAll());
        }else{
            System.out.println("Cancelling");
            // do nothing
        }
    }

}

package view;

import model.Error;

import java.util.Scanner;

public class Student {
    private final Scanner sc;
    private controller.Student student;

    public Student(controller.Student student, Scanner sc) {
        this.student = student;
        this.sc = sc;

        int option = 0;
        while (option != 9) {
            option = showMenu();
            System.out.println();
            switch (option) {
                case 1:
                    System.out.println(this.student.loadAssignment());
                    break;
                case 2:
                    System.out.println(this.student.getData());
                    break;
                case 3:
                    runAssignment();
                    break;
                case 4:
                    try {
                        System.out.println(this.student.getPastSubmissions());
                    } catch (Error error) {
                        System.err.println(error.getMessage());
                    }
                    break;
                default:
                    System.out.println("Please enter valid option!");
                    break;
            }
        }

        System.out.println("Goodbye!");
    }

    private int showMenu() {
        int option = 0;
        System.out.println();
        System.out.println("Menu:");
        System.out.println(" - Get questions (1)");
        System.out.println(" - Get data (2)");
        System.out.println(" - Start submission (3)");
        System.out.println(" - Get passed submissions (4)");
        System.out.println(" - Exit (9)");
        System.out.print("Enter option: ");
        option = sc.nextInt();
        return option;
    }

    private void runAssignment() {
        student.createSubmission();

        String question;
        while ((question = student.getNextQuestion()) != null) {
            System.out.println();
            System.out.println(question);
            System.out.print("Enter answer: ");
            String answer = sc.nextLine();
            if(answer.equals("")){
                System.out.println("You did not enter any input, please enter input");
                sc.nextLine();
            }
            try {
                student.answerQuestion(answer);
            } catch (Error error) {
                System.err.println(error.getMessage());
            }
        }

        try {
            System.out.println("Your mark: " + student.getMark());
            System.out.println();
            System.out.println("Feedback:");
            System.out.println(student.getFeedback());
        } catch (Error error) {
            System.err.println(error.getMessage());
        }

        student.submitAssignment();
    }
}

package view;

import model.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Student {
    private controller.Student student;

    public Student(controller.Student student) {
        this.student = student;

        boolean running = true;
        while (running) {
            showMenu();
            String menu_item = getReturn();
            System.out.println("You entered: " + menu_item);
            switch (menu_item) {
                case "1":
                    System.out.println(this.student.loadAssignment());
                    break;
                case "2":
                    System.out.println(this.student.getData());
                    break;
                case "3":
                    runAssignment();
                    break;
                case "9":
                    running = false;
                    break;
                default:
                    System.out.println("Please enter valid option!");
                    break;
            }
        }

        System.out.println("Goodbye!");
    }

    private void showMenu() {
        System.out.println();
        System.out.println("Menu:");
        System.out.println(" - Get questions (1)");
        System.out.println(" - Get data (2)");
        System.out.println(" - Start submission (3)");
        System.out.println(" - Exit (9)");
        System.out.print("Enter option: ");
    }

    private String getReturn() {
        String line = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private void runAssignment() {
        student.createSubmission();

        String question;
        while ((question = student.getNextQuestion()) != null) {
            System.out.println();
            System.out.println(question);
            System.out.print("Enter answer: ");

            String answer = getReturn();

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

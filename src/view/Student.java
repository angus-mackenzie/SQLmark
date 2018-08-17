package view;

import model.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Student {
    private controller.Student student;
    private BufferedReader br;

    public Student(controller.Student student) {
        this.student = student;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            this.br = br;

            boolean running = true;
            while (running) {
                showMenu();
                int menuItem = Integer.parseInt(getReturn());
                switch (menuItem) {
                    case 1:
                        System.out.println(this.student.loadAssignment());
                        break;
                    case 2:
                        System.out.println(this.student.getData());
                        break;
                    case 3:
                        runAssignment();
                        break;
                    case 9:
                        running = false;
                        break;
                }
            }

            System.out.println("Goodbye!");


        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private String getReturn() throws IOException {
        return br.readLine();
    }

    private void runAssignment() throws IOException {
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

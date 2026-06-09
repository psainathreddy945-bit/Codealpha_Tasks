import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class Student {
    private String name;
    private int marks;

    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public int getMarks() {
        return marks;
    }
}

public class StudentGradeTracker {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        System.out.println("===== STUDENT GRADE TRACKER =====");
        loadStudentsFromFile();
        int choice;

        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewReport();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 4);
    }

    static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add Student");
        System.out.println("2. View Report");
        System.out.println("3. Search Student");
        System.out.println("4. Exit");
    }

    static void addStudent() {
        System.out.print("\nEnter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter marks (0-100): ");
        int marks = sc.nextInt();
        sc.nextLine();

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks! Please enter marks between 0 and 100.");
            return;
        }

        students.add(new Student(name, marks));
        saveStudentToFile(name, marks);
        System.out.println("Student added successfully!");
    }

    static void viewReport() {
        if (students.isEmpty()) {
            System.out.println("\nNo students to display!");
            return;
        }

        int total = 0;
        int highest = students.get(0).getMarks();
        int lowest = students.get(0).getMarks();

        for (Student s : students) {
            total += s.getMarks();

            if (s.getMarks() > highest) {
                highest = s.getMarks();
            }

            if (s.getMarks() < lowest) {
                lowest = s.getMarks();
            }
        }

        double average = (double) total / students.size();

        System.out.println("\n===== STUDENT REPORT =====");

        for (Student s : students) {
            System.out.println("----------------------");
            System.out.println("Student Name : " + s.getName());
            System.out.println("Marks        : " + s.getMarks());
            if (s.getMarks() >= 90)
                System.out.println("Grade: A");
            else if (s.getMarks() >= 75)
                System.out.println("Grade: B");
            else if (s.getMarks() >= 50)
                System.out.println("Grade: C");
            else
                System.out.println("Grade: Fail");
        }

        System.out.printf("\nAverage Marks: %.2f\n", average);
        System.out.println("Highest Marks: " + highest);
        System.out.println("Lowest Marks: " + lowest);

        students.sort((s1, s2) -> s2.getMarks() - s1.getMarks());

        System.out.println("\n===== STUDENT RANKING =====");
        for (int i = 0; i < students.size(); i++) {
            System.out.println(
                "Rank " + (i + 1) + ": " +
                students.get(i).getName() +
                " - " + students.get(i).getMarks()
            );
        }
    }

    static void searchStudent() {
        if (students.isEmpty()) {
            System.out.println("\nNo students to search!");
            return;
        }

        System.out.print("\nEnter student name to search: ");
        String searchName = sc.nextLine();

        boolean found = false;
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(searchName)) {
                System.out.println("\n---- Student Found ----");
                System.out.println("Name: " + s.getName());
                System.out.println("Marks: " + s.getMarks());
                if (s.getMarks() >= 90)
                    System.out.println("Grade: A");
                else if (s.getMarks() >= 75)
                    System.out.println("Grade: B");
                else if (s.getMarks() >= 50)
                    System.out.println("Grade: C");
                else
                    System.out.println("Grade: Fail");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found!");
        }
    }

    static void saveStudentToFile(String name, int marks) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            fw.write(name + "," + marks + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving student to file: " + e.getMessage());
        }
    }

    static void loadStudentsFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int marks = Integer.parseInt(parts[1]);
                    students.add(new Student(name, marks));
                }
            }
            br.close();
            if (students.size() > 0) {
                System.out.println("Loaded " + students.size() + " students from file.");
            }
        } catch (IOException e) {
            System.out.println("No existing student file found. Starting fresh.");
        }
    }
}

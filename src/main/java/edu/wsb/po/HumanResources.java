package edu.wsb.po;

import java.util.Scanner;

public class HumanResources {
    private final Scanner scanner;
    private final DoctorManager doctorManager;

    public HumanResources(DoctorManager doctorManager) {
        this.scanner = new Scanner(System.in);
        this.doctorManager = doctorManager;
    }

    public void start() {
        while (true) {
            // Menu options
            System.out.println("-----Doctor Manager-----");
            System.out.println("1. Add a Doctor's profile");
            System.out.println("2. Add a specialty to a doctor");
            System.out.println("3. Exit");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    doctorManager.interactiveAddDoctor();
                    break;
                    case 2:
                        doctorManager.interactiveAddSpecialty();
                        break;
                        case 3:
                            System.out.println("Exiting...");
                            return;
                            default:
                                System.out.println("Invalid choice, try again.");

            }

        }
    }
}

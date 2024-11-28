package edu.wsb.po;

import java.util.Scanner;

public class PatientManagerApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final PatientManager manager = new PatientManager();

    public static void main(String[] args) {
        while (true) {
            // Menu options
            System.out.println("-----Patient Manager-----");
            System.out.println("1. Add Patient");
            System.out.println("2. Search for a patient by PESEL");
            System.out.println("3. Search for patients by surname");
            System.out.println("4. Exit");
            System.out.println("Choose an option: ");

            int choice = sc.nextInt(); //Read users choice
            sc.nextLine();

            switch (choice) {
                case 1:
                    manager.interactiveAddPatient();
                    break;
                    case 2:
                        manager.findPatientByPesel();
                        break;
                        case 3:
                            manager.findPatientsBySurname();
                            break;
                            case 4:
                                System.out.println("Exiting...");
                                return;
                                default:
                                    System.out.println("Invalid choice, try again");


            }




        }
    }
}

package edu.wsb.po;

import java.util.Scanner;

public class Reception {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientManager patientManager = new PatientManager();
    private static final DoctorManager doctorManager = new DoctorManager();

    public static void main(String[] args) {
        while (true) {
            // Menu options
            System.out.println("-----Reception Management Interface-----");
            System.out.println("1. Add Patient");
            System.out.println("2. Search for a patient by PESEL");
            System.out.println("3. Search for patients by surname");
            System.out.println("4. Search for a doctor by ID");
            System.out.println("5. Search for doctors by specialty");
            System.out.println("6. Exit");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt(); //Read users choice
            scanner.nextLine();

            switch (choice) {
                case 1:
                    patientManager.interactiveAddPatient();
                    break;
                    case 2:
                        patientManager.findPatientByPesel();
                        break;
                        case 3:
                            patientManager.findPatientsBySurname();
                            break;
                            case 4:
                                doctorManager.findDoctorById();
                                break;
                                case 5:
                                    doctorManager.findDoctorsBySpecialty();
                                    break;
                            case 6:
                                System.out.println("Exiting...");
                                return;
                                default:
                                    System.out.println("Invalid choice, try again");


            }
        }
    }
}

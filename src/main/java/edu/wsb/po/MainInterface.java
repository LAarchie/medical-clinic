package edu.wsb.po;

import java.nio.file.Paths;
import java.util.Scanner;

public class MainInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String DOCTORS_FILE = "resources/doctors.csv";
    private static final String PATIENTS_FILE = "resources/patients.csv";
    private static final String SCHEDULES_FILE = "resources/schedules.csv";
    public static void main(String[] args) {

        DoctorManager doctorManager = new DoctorManager(Paths.get(DOCTORS_FILE));
        PatientManager patientManager = new PatientManager(Paths.get(PATIENTS_FILE));
        ScheduleManager scheduleManager = new ScheduleManager(Paths.get(SCHEDULES_FILE));


        //Menu options
        while(true){
            System.out.println("Choose the interface to open: ");
            System.out.println("1. Reception");
            System.out.println("2. Human Resources");
            System.out.println("3. Management");
            System.out.println("4. Exit");


            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Reception receptionInterface = new Reception(doctorManager, patientManager, scheduleManager);
                    receptionInterface.start();
                    break;
                    case 2:
                        HumanResources humanResourcesInterface = new HumanResources(doctorManager);
                        humanResourcesInterface.start();
                        break;
                        case 3:
                            Management managementInterface = new Management(doctorManager, scheduleManager);
                            managementInterface.start();
                            break;
                            case 4:
                                System.out.println("Exiting...");
                                return;
                                default:
                                    System.out.println("Invalid choice, try again");
            }
    }
}}

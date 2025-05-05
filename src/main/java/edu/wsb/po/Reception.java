package edu.wsb.po;

import java.util.Scanner;

public class Reception {
    private final Scanner scanner;
    private final PatientManager patientManager;
    private final DoctorManager doctorManager;
    private final ScheduleManager scheduleManager;


    public Reception(DoctorManager doctorManager, PatientManager patientManager, ScheduleManager scheduleManager) {
        this.scanner = new Scanner(System.in);
        this.patientManager = patientManager;
        this.doctorManager = doctorManager;
        this.scheduleManager = scheduleManager;
        
    }
    public void start(){
        while (true) {
            // Menu options
            System.out.println("-----Reception Management Interface-----");
            System.out.println("1. Add Patient");
            System.out.println("2. Search for a patient by PESEL");
            System.out.println("3. Search for patients by surname");
            System.out.println("4. Search for a doctor by ID");
            System.out.println("5. Search for doctors by specialty");
            System.out.println("6. Display doctor's weekly schedule");
            System.out.println("7. Exit");
            System.out.println("Choose an option: ");

            //Doctor doc1 = doctorManager.addDoctor();
            int choice = scanner.nextInt(); //Read users choice
            scanner.nextLine();
            Doctor doctor = null;
            Patient patient = null;
            String id = null;

            switch (choice) {
                case 1:
                    patientManager.interactiveAddPatient();
                    break;
                    case 2:
                        System.out.println("Enter PESEL number: ");
                        String pesel = scanner.nextLine();
                        patient = patientManager.findPatientByPesel(pesel);
                        System.out.println(patient);
                        break;
                        case 3:
                            System.out.println("Enter surname: ");
                            String surname = scanner.nextLine();
                            patientManager.findPatientsBySurname(surname);
                            break;
                            case 4:
                                System.out.println("Enter Doctor's ID: ");
                                id = scanner.nextLine();
                                doctor = doctorManager.findDoctorById(id);
                                System.out.println(doctor);
                                break;
                                case 5:
                                    doctorManager.findDoctorsBySpecialty();
                                    break;
                                    case 6:
                                        System.out.println("Enter Doctor's ID: ");
                                        id = scanner.nextLine();
                                        doctor = doctorManager.findDoctorById(id);
                                        if(doctor==null){
                                            System.out.println("Returning to the previous menu...");
                                            return;
                                        }
                                        String doctorId = doctor.getId();

                                        scheduleManager.displayWeeklyScheduleForDoctor(doctorId);
                                        break;
                                        case 7:
                                System.out.println("Exiting...");
                                return;
                                default:
                                    System.out.println("Invalid choice, try again");


            }
        }
    }
}

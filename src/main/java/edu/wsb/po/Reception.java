package edu.wsb.po;

import java.util.Scanner;

public class Reception {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientManager patientManager = new PatientManager();
    private static final DoctorManager doctorManager = new DoctorManager();
    private static final ScheduleManager scheduleManager = new ScheduleManager();

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
                                doctor = doctorManager.findDoctorById();
                                System.out.println(doctor);
                                break;
                                case 5:
                                    doctorManager.findDoctorsBySpecialty();
                                    break;
                                    case 6:
                                        doctor = doctorManager.findDoctorById();
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
